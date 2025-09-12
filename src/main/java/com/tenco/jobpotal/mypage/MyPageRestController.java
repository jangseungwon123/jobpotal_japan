package com.tenco.jobpotal.mypage;


import com.tenco.jobpotal._core.common.ApiUtil;
import com.tenco.jobpotal._core.utils.Define;
import com.tenco.jobpotal.community.userCommunity.UserCommunityResponse;
import com.tenco.jobpotal.user.LoginUser;
import com.tenco.jobpotal.user.normal.UserJpaRepository;
import com.tenco.jobpotal.user.normal.UserRequest;
import com.tenco.jobpotal.user.normal.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/my-page")
@RequiredArgsConstructor
@Validated
public class MyPageRestController {
    private final UserJpaRepository userJpaRepository;
    private final MyPageService myPageService;

    // 마이페이지 - 내가 쓴 글 조회하기
    @GetMapping("/posts")
    public ResponseEntity<Page<UserCommunityResponse.MyPostResponse>> myPosts(
            @RequestAttribute(value = Define.LOGIN_USER, required = false) LoginUser loginUser, Pageable pageable) {
        Page<UserCommunityResponse.MyPostResponse> response = myPageService.myPosts(loginUser, pageable);
        return ResponseEntity.ok(response); //
    }

    // 마이페이지 - 내 정보 조회
    @GetMapping("/my-profile")
    public ResponseEntity<UserResponse.MyProfileDTO> myProfiles(@RequestAttribute(value = Define.LOGIN_USER, required = false) LoginUser loginUser) {

        UserResponse.MyProfileDTO myProfile = myPageService.myProfile(loginUser);
        return ResponseEntity.ok(myProfile);
    }

    //마이페이지 - 내 정보 수정
    @PutMapping("/my-profile")
    public ResponseEntity<?> myProfileUpdate(
            @RequestAttribute(value = Define.LOGIN_USER, required = false) LoginUser loginUser,
            @RequestBody UserRequest.UpdateProfileRequestDTO updateDTO) {
        myPageService.myProfileUpdate(loginUser, updateDTO);
        return ResponseEntity.ok(new ApiUtil<>("내 정보 수정이 완료 되었습니다."));
    }

    // 마이페이지 - 내 정보 삭제
    @DeleteMapping("/my-profile")
    public ResponseEntity<?> deleteUser(@RequestAttribute(value = Define.LOGIN_USER, required =
            false) LoginUser loginUser) {
        myPageService.deleteUser(loginUser);
        return ResponseEntity.ok(new ApiUtil<>("회원 탈퇴가 완료 되었습니다."));
    }
}