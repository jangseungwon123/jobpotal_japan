package com.tenco.jobpotal.subscribe;

import com.tenco.jobpotal._core.errors.exception.Exception403;
import com.tenco.jobpotal._core.errors.exception.Exception404;
import com.tenco.jobpotal.company.CompInfo;
import com.tenco.jobpotal.company.CompInfoJpaRepository;
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
public class UserSubService {
    private final UserSubJpaRepository userSubJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final CompInfoJpaRepository compInfoJpaRepository;

    // 구독
    @Transactional
    public UserSubResponse.SaveDTO save(UserSubRequest.SaveDTO saveDTO, LoginUser loginUser) {
        if (userSubJpaRepository.existsByCompIdAndUserId(saveDTO.getCompId(), loginUser.getId())) {
            throw new Exception403("이미 구독했습니다");
        }
        User user = userJpaRepository.findById(loginUser.getId()).orElseThrow(() ->
                new Exception404("존재하지 않는 사용자입니다"));
        CompInfo compInfo = compInfoJpaRepository.findById(saveDTO.getCompId()).orElseThrow(() ->
                new Exception404("존재하지 않는 기업입니다."));

        UserSub userSub = UserSub.builder()
                .user(user)
                .compInfo(compInfo)
                .build();


        UserSub savedUserSub = userSubJpaRepository.save(userSub);
        return new UserSubResponse.SaveDTO(savedUserSub);
    }

    // 구독목록 조회 서비스
    public List<UserSubResponse.SubListDTO> findAllByUserAndCompanyId(Long userId) {
        List<UserSubResponse.SubListDTO> userSubList = userSubJpaRepository.findAllByUserAndCompId(userId);
        return userSubList;
    }

    // 구독 삭제
    @Transactional
    public void deleteById(Long userSubId, LoginUser loginUser) {
        UserSub userSub = userSubJpaRepository.findById(userSubId).orElseThrow(() ->
                new Exception404("삭제하려는 구독이 없습니다"));
        if (!userSub.isOwner(loginUser.getId())) {
            throw new Exception403("본인의 구독만 취소할 수 있습니다");
        }
        userSubJpaRepository.deleteById(userSubId);
    }
}
