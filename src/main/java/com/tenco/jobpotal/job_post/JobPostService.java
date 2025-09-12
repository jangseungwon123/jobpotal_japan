package com.tenco.jobpotal.job_post;

import com.tenco.jobpotal._core.errors.exception.Exception403;
import com.tenco.jobpotal._core.errors.exception.Exception404;
import com.tenco.jobpotal.alarm.event.JobPostCreatedEvent;
import com.tenco.jobpotal.company.CompInfo;
import com.tenco.jobpotal.company.CompInfoJpaRepository;
import com.tenco.jobpotal.resume.SkillListJpaRepository;
import com.tenco.jobpotal.skill.SkillList;
import com.tenco.jobpotal.user.LoginUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class JobPostService {
    private final JobPostRepository jobPostRepository;
    private final CompInfoJpaRepository compInfoJpaRepository;
    private final SkillListJpaRepository skillListJpaRepository;
    private final ApplicationEventPublisher eventPublisher;

    // 전체 목록 조회
    public List<JobPostResponseDTO> getAllJobPosts() {
        List<JobPost> jobPosts = jobPostRepository.findAllWithUser();
        log.info("jobPost 확인 : {}", jobPosts.toString());

        return jobPosts.stream()
                .map(JobPostResponseDTO::new)
                .collect(Collectors.toList());
    }

    // 상세 조회 (예외 처리 포함)
    public JobPost getJobPostById(Long recruitId) {
        return jobPostRepository.findById(recruitId)
                .orElseThrow(() -> new Exception404("해당 채용공고가 존재하지 않습니다."));
    }

    // 게시글 등록
    @Transactional
    public JobPostResponseDTO createJobPost(JobPostRequestDTO requestDTO, LoginUser loginUser) {
        CompInfo companyInfo = compInfoJpaRepository.findById(requestDTO.getCompId()).orElseThrow(() ->
                new Exception404("해당 게시물이 존재하지 않습니다.")
        );
        SkillList skillList = skillListJpaRepository.findBySkillId(requestDTO.getSkillId()).orElseThrow(() ->
                new Exception404("해당 스킬정보가 존재하지 않습니다.")
        );

        JobPost savedJobPost = jobPostRepository.save(requestDTO.toEntity(companyInfo, skillList, loginUser));

        log.info("savedJobPost 값 확인 : {}",savedJobPost);
        
        // 채용공고 생성 이벤트 발행
        JobPostCreatedEvent event = new JobPostCreatedEvent(
                savedJobPost.getRecruitId(),
                companyInfo.getCompId(),
                companyInfo.getCompanyName(),
                savedJobPost.getTitle()
        );
        eventPublisher.publishEvent(event);
        
        log.info("JobPostCreatedEvent 발행 완료 - 채용공고ID: {}, 회사: {}", 
                savedJobPost.getRecruitId(), companyInfo.getCompanyName());
        
        return new JobPostResponseDTO(savedJobPost);
    }

    // 게시글 수정 (예외 처리 포함)
    @Transactional
    public JobPostResponseDTO updateJobPost(Long recruitId, JobPostRequestDTO requestDTO, LoginUser loginUser) {
        JobPost jobPost = jobPostRepository.findById(recruitId)
                .orElseThrow(() -> new Exception404("존재하지 않는 게시글입니다."));

        SkillList skillList = skillListJpaRepository.findBySkillId(requestDTO.getSkillId()).orElseThrow(() ->
                new Exception404("해당 스킬정보가 존재하지 않습니다.")
        );

        // [보안 강화] 공고 작성자와 로그인한 사용자가 동일한지 확인
        if (!jobPost.getInstId().equals(loginUser.getLoginId())) {
            throw new Exception403("게시글을 수정할 권한이 없습니다.");
        }

        // DTO의 데이터로 엔티티를 업데이트합니다. (더티 체킹)
        jobPost.setTitle(requestDTO.getTitle());
        jobPost.setContent(requestDTO.getContent());
        jobPost.setSkillList(skillList);
        jobPost.setRequireCareerYears(requestDTO.getRequireCareerYears());
        jobPost.setEmploymentType(requestDTO.getEmploymentType());
        jobPost.setPostedAt(requestDTO.getPostedAt());
        jobPost.setDeadline(requestDTO.getDeadline());

        return new JobPostResponseDTO(jobPost);
    }

    // 게시글 삭제 (예외 처리 포함)
    @Transactional
    public void deleteJobPost(Long recruitId, LoginUser loginUser) {
        JobPost jobPost = jobPostRepository.findById(recruitId)
                .orElseThrow(() -> new Exception404("해당 게시글이 존재하지 않습니다."));

        //  공고 작성자와 로그인한 사용자가 동일한지 확인
        if (!jobPost.getInstId().equals(loginUser.getLoginId())) {
            throw new Exception403("게시글을 삭제할 권한이 없습니다.");
        }
        jobPostRepository.delete(jobPost);
    }

    // 페이징 처리
    public Page<JobPostResponseDTO> getJobPostsPaged(Pageable pageable) {
        Page<JobPost> jobPostsPage = jobPostRepository.findAll(pageable);
        return jobPostsPage.map(JobPostResponseDTO::new);
    }

    // 검색 기능
    public List<JobPostResponseDTO> searchJobPosts(String keyword) {
        List<JobPost> jobPosts = jobPostRepository.searchByKeyword(keyword);
        return jobPosts.stream()
                .map(JobPostResponseDTO::new)
                .toList();
    }
}
