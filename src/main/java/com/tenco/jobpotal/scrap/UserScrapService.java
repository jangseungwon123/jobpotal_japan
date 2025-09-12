package com.tenco.jobpotal.scrap;

import com.tenco.jobpotal._core.errors.exception.Exception403;
import com.tenco.jobpotal._core.errors.exception.Exception404;
import com.tenco.jobpotal.job_post.JobPost;
import com.tenco.jobpotal.job_post.JobPostRepository;
import com.tenco.jobpotal.user.LoginUser;
import com.tenco.jobpotal.user.normal.User;
import com.tenco.jobpotal.user.normal.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserScrapService {
    private final UserScrapJpaRepository userScrapJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final JobPostRepository jobPostRepository;

    // 채용공고 스크랩
    @Transactional
    public UserScrapResponse.SaveDTO save(UserScrapRequest.SaveDTO saveDTO, LoginUser loginUser) {
        if (userScrapJpaRepository.existsByJobPostIdAndUserId(saveDTO.getRecruitId(), loginUser.getId())) {
            throw new Exception403("이미 저장한 채용공고 입니다.");
        }
        User user = userJpaRepository.findById(loginUser.getId()).orElseThrow(() ->
                new Exception404("존재하지 않는 사용자입니다."));
        JobPost jobPost = jobPostRepository.findById(saveDTO.getRecruitId()).orElseThrow(() ->
                new Exception404("존재하지 않는 채용공고 입니다."));

        UserScrap userScrap = UserScrap.builder()
                .user(user)
                .jobPost(jobPost)
                .build();


        UserScrap savedUserScrap = userScrapJpaRepository.save(userScrap);
        return new UserScrapResponse.SaveDTO(savedUserScrap);
    }

    // 채용공고 스크랩 목록 조회 서비스
    public List<UserScrapResponse.ScrapListDTO> findAllByUserAndJobPostId(Long userId) {
        List<UserScrapResponse.ScrapListDTO> userScrapList = userScrapJpaRepository.findAllByUserAndJobPostId(userId);
        return userScrapList;
    }

    // 채용공고 삭제
    @Transactional
    public void deleteById(Long userScarpId, LoginUser loginUser) {
        UserScrap userScrap = userScrapJpaRepository.findById(userScarpId).orElseThrow(() ->
                new Exception404("삭제하려는 채용공고가 없습니다"));
        if (!userScrap.isOwner(loginUser.getId())) {
            throw new Exception403("본인이 저장한 채용공고만 삭제할 수 있습니다");
        }
        userScrapJpaRepository.deleteById(userScarpId);
    }
}
