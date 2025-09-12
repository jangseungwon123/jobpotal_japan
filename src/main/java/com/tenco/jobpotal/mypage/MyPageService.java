package com.tenco.jobpotal.mypage;

import com.tenco.jobpotal._core.errors.exception.Exception404;
import com.tenco.jobpotal.alarm.AlarmJpaRepository;
import com.tenco.jobpotal.community.userCommunity.UserCommunity;
import com.tenco.jobpotal.community.userCommunity.UserCommunityJpaRepository;
import com.tenco.jobpotal.community.userCommunity.UserCommunityResponse;
import com.tenco.jobpotal.subscribe.UserSubJpaRepository;
import com.tenco.jobpotal.user.LoginUser;
import com.tenco.jobpotal.user.normal.User;
import com.tenco.jobpotal.user.normal.UserJpaRepository;
import com.tenco.jobpotal.user.normal.UserRequest;
import com.tenco.jobpotal.user.normal.UserResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

// Summary = 요약
@Service
@RequiredArgsConstructor
public class MyPageService {


    private final UserJpaRepository userJpaRepository;
    private final AlarmJpaRepository alarmJpaRepository;
    private final UserSubJpaRepository userSubJpaRepository;

    private final UserCommunityJpaRepository userCommunityJpaRepository;


    // 내 정보 ->
    public UserResponse.MyProfileDTO myProfile(LoginUser loginUser) {
        Optional<User> user = userJpaRepository.findById(loginUser.getId()); // --> 이것은 User 반환 --> user를 dto로 변환하기

        return user.map(UserResponse.MyProfileDTO::fromEntity)
                .orElseThrow(() -> new Exception404("사용자를 찾을 수 없습니다."));
    }

    // 내 정보 수정
    @Transactional
    public void myProfileUpdate(LoginUser loginUser, UserRequest.UpdateProfileRequestDTO updateDTO) {
        User user = userJpaRepository.findById(loginUser.getId())
                .orElseThrow(() -> new Exception404("사용자를 찾을 수 없습니다."));

        user.profileUpdate(updateDTO);
    }

    // 내가 쓴 글 조회-필요한것 : 나[세션]의 id , usercommunity에 따로 쿼리 만들기

    public Page<UserCommunityResponse.MyPostResponse> myPosts(LoginUser loginUser, Pageable pageable) {
        Page<UserCommunity> userCommunities =
                userCommunityJpaRepository.findAllWithUserByUserId(loginUser.getId(), pageable);

        return userCommunities.map(UserCommunityResponse.MyPostResponse::fromEntity);
    }



    // 회원 탈퇴 기능
    public ResponseEntity<String> deleteUser(LoginUser loginUser) {
        User user = userJpaRepository.findById(loginUser.getId())
                .orElseThrow(() -> new Exception404("사용자를 찾을 수 없습니다."));
        // 사용자 삭제
        userJpaRepository.delete(user);
        //alarmJpaRepository.delete
        //userSubJpaRepository.deleteAllById(user.getUserId());
        return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
    }
// 내가 스크랩한 공고



}
