package com.tenco.jobpotal.company.compRating;


import com.tenco.jobpotal._core.errors.exception.Exception400;
import com.tenco.jobpotal._core.errors.exception.Exception403;
import com.tenco.jobpotal._core.errors.exception.Exception404;
import com.tenco.jobpotal.resume.Resume;
import com.tenco.jobpotal.user.LoginUser;
import com.tenco.jobpotal.user.applInfo.ApplInfo;
import com.tenco.jobpotal.user.applInfo.ApplInfoJpaRepository;
import com.tenco.jobpotal.user.comp.CompUserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CompRatingService {

    private final CompRatingJpaRepository compRatingJpaRepository;
    private final ApplInfoJpaRepository applInfoJpaRepository;
    private final CompUserJpaRepository compUserJpaRepository;


    @Transactional
    public CompRatingResponse.SaveDTO save(CompRatingRequest.SaveDTO saveDTO, LoginUser loginUser) {
        // 1 .지원 정보 조회
        ApplInfo applInfo = applInfoJpaRepository.findById(saveDTO.getApplInfoId())
                .orElseThrow(() -> new Exception404("해당 지원한 정보를 찾을 수 없습니다."));

        if(!applInfo.getResume().getUser().getUserId().equals(loginUser.getId())){
            throw new Exception403("자신이 지원한 회사만 평가만 가능합니다.");
        }
        if (!"합격".equals(applInfo.getStatus())){
            throw new Exception403("합격한 회사의 평가만 가능합니다.");
        }
        compRatingJpaRepository.findByApplInfo_ApplInfoId(applInfo.getApplInfoId())
                .ifPresent(compRating -> {throw new Exception400("이미 평가를 완료한 지원입니다.");});

        CompRating compRating = CompRating.builder()
                .applInfo(applInfo)
                .score(saveDTO.getScore())
                .build();
        CompRating savedCompRating = compRatingJpaRepository.save(compRating);
        return new CompRatingResponse.SaveDTO(savedCompRating);

    }

    @Transactional
    public void update(Long ratingId, int score, LoginUser loginUser){
       CompRating compRating = compRatingJpaRepository.findById(ratingId)
               .orElseThrow(() -> new Exception404("존재하지 않는 평가입니다."));
       if(!compRating.getApplInfo().getResume().getUser().getUserId().equals(loginUser.getId())){
           throw new Exception403("자신이 평가한 평점만 수정 가능합니다.");
       }
       compRating.setScore(score);
    }

    @Transactional
    public void delete(Long ratingId, LoginUser loginUser){
        CompRating compRating = compRatingJpaRepository.findById(ratingId)
                .orElseThrow(() -> new Exception404("존재하지 않는 평점입니다."));
        if (!compRating.getApplInfo().getResume().getUser().getUserId().equals(loginUser.getId())){
            throw new Exception403("자신이 평가한 평점만 삭제 가능합니다.");
        }
        compRatingJpaRepository.delete(compRating);
    }

    public CompRatingResponse.AvgScoreDTO getAverageScore(Long compUserId){
        compUserJpaRepository.findById(compUserId)
                .orElseThrow(() -> new Exception404("존재하지 않는 회사입니다."));
        Double avgScore = compRatingJpaRepository.findAverageScoreBycompUserId(compUserId);
        String message = (avgScore == null) ? "평점 등록되지 않은 회사입니다." : "기업의 평균 평점입니다.";
        return new CompRatingResponse.AvgScoreDTO(compUserId, avgScore ,message );
    }


}
