package com.tenco.jobpotal.user.applInfo;


import com.tenco.jobpotal._core.errors.exception.Exception400;
import com.tenco.jobpotal._core.errors.exception.Exception403;
import com.tenco.jobpotal._core.errors.exception.Exception404;
import com.tenco.jobpotal._core.errors.exception.Exception500;
import com.tenco.jobpotal.company.CompInfoJpaRepository;
import com.tenco.jobpotal.job_post.JobPost;
import com.tenco.jobpotal.job_post.JobPostRepository;
import com.tenco.jobpotal.resume.Resume;
import com.tenco.jobpotal.resume.ResumeJpaRepository;
import com.tenco.jobpotal.user.LoginUser;
import com.tenco.jobpotal.user.comp.CompUser;
import com.tenco.jobpotal.user.comp.CompUserJpaRepository;
import com.tenco.jobpotal.user.normal.User;
import com.tenco.jobpotal.user.normal.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ApplInfoService {

    private final ApplInfoJpaRepository applInfoJpaRepository;
    private final JobPostRepository jobPostRepository;
    private final ResumeJpaRepository resumeJpaRepository;
    private final CompInfoJpaRepository compInfoJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final CompUserJpaRepository compUserJpaRepository;

    @Transactional
    public ApplInfoResponse.SaveDTO save(ApplInfoRequest.SaveDTO saveDTO, LoginUser loginUser) {
        Resume resume = resumeJpaRepository.findById(saveDTO.getResumeId())
                .orElseThrow(() -> new Exception404("선택한 이력서를 찾을 수 없습니다."));

        if (!resume.getUser().getUserId().equals(loginUser.getId())) {
            throw new Exception403("자신의 이력서로만 지원할 수 있습니다.");
        }
        JobPost jobPost = jobPostRepository.findById(saveDTO.getJopPostId())
                .orElseThrow(() -> new Exception500("존재하지 않는 채용 공고입니다."));
        ApplInfo existApplInfo = applInfoJpaRepository.findByUserIdAndJobPostId(loginUser.getId(), saveDTO.getJopPostId());
        if (existApplInfo != null) {
            throw new Exception500("이미 지원한 공고입니다.");
        }
        ApplInfo applInfo = ApplInfo.builder()
                .resume(resume)
                .jobPost(jobPost)
                .status("지원완료")
                .build();
        ApplInfo savedApplInfo = applInfoJpaRepository.save(applInfo);
        return new ApplInfoResponse.SaveDTO(savedApplInfo);
    } // end of save


    @Transactional
    public void updateStatus(Long applyId, String status, LoginUser loginUser) {
        ApplInfo applInfo = applInfoJpaRepository.findById(applyId)
                .orElseThrow(() -> new Exception404("존재하지 않는 지원 정보입니다."));
        if (!applInfo.getJobPost().getCompInfo().getCompUser().getCompUserId().equals(loginUser.getId())) {
            throw new Exception403("해당 지원서의 상태를 변경할 권한이 없습니다.");
        }
        if (!"지원완료".equals(applInfo.getStatus())) {
            throw new Exception400("이미 합격 또는 불합격 처리된 지원서는 상태를 변경할 수 없습니다.");
        }
        if (!"합격".equals(status) && !"불합격".equals(status)) {
            throw new Exception400("잘못된 상태 값입니다. '합격' 또는 '불합격'만 가능합니다.");
        }
        applInfo.setStatus(status);

    }

    // [사용자]가 자신의 지원 목록을 조회할 때 사용하는 DTO
    public List<ApplInfoResponse.UserApplInfoListDTO> userApplInfoListDTO(LoginUser loginUser, Long userId) {
        User user = userJpaRepository.findById(loginUser.getId())
                .orElseThrow(() -> new Exception404("존재하지 않는 유저입니다."));

        if (!user.getUserId().equals(userId)) {
            throw new Exception403("본인의 지원 목록만 볼 수 있습니다.");
        }

        return applInfoJpaRepository.findByUserId(userId)
                .stream()
                .map(ApplInfoResponse.UserApplInfoListDTO::new)
                .toList();
    }

    // [기업]지원한 지원자 목록을 조회할 때 사용하는 DTO
    public List<ApplInfoResponse.CompApplInfoListDTO> compApplInfoListDTOList(LoginUser loginUser, Long comUserId) {
        CompUser compUser = compUserJpaRepository.findById(loginUser.getId())
                .orElseThrow(() -> new Exception404("존재하지 않는 유저입니다."));

        if (!compUser.getCompUserId().equals(comUserId)) {
            throw new Exception403("귀사의 채용 지원자만 볼 수 있습니다.");
        }

        return applInfoJpaRepository.findByCompId(comUserId)
                .stream()
                .map(ApplInfoResponse.CompApplInfoListDTO::new)
                .toList();
    }

    //"[공고]에서 삭제" ,description = "[채용 공고] 안에 삭제하는 기능"
    @Transactional
    public void deleteByapply(LoginUser loginUser, Long applyId) {
        ApplInfo applInfo = applInfoJpaRepository.findById(applyId)
                .orElseThrow(() -> new Exception404("존재하지 않는 지원입니다"));
        if (!applInfo.getResume().getUser().getUserId().equals(loginUser.getId())) {
            throw new Exception403("자신의 지원만 삭제할 수 있습니다.");
        }
        applInfoJpaRepository.delete(applInfo);
    }

} // end of ApplInfoService
