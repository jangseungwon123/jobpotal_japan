package com.tenco.jobpotal.community.userCommunity;

import com.tenco.jobpotal._core.common.ApiUtil;
import com.tenco.jobpotal._core.errors.exception.Exception400;
import com.tenco.jobpotal._core.utils.Define;
import com.tenco.jobpotal.user.LoginUser;
import com.tenco.jobpotal.user.normal.User;
import com.tenco.jobpotal.user.normal.UserResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserCommunityRestController {

    private static final Logger log = LoggerFactory.getLogger(UserCommunityRestController.class);
    private final UserCommunityService userCommunityService;

    // 전체 게시글 조회 (페이징)
    @GetMapping("/community/list")
    public ResponseEntity<?> communityList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestAttribute(value = Define.LOGIN_USER, required = false) LoginUser loginUser) {
        
        Page<UserCommunityResponse.ListDTO> communityPage = userCommunityService.findAllPosts(
                PageRequest.of(page, size), 
                loginUser
        );
        return ResponseEntity.ok(new ApiUtil<>(communityPage));
    }

    // 단일 게시글 조회
    @GetMapping("/community/{id}")
    public ResponseEntity<?> getCommunity(@PathVariable(name = "id") Long id,
                                          @RequestAttribute(value = Define.LOGIN_USER, required = false) LoginUser loginUser) {

        if(loginUser.isCompany()){
            throw new Exception400("일반회원만 접근 가능합니다.");
        }
        log.info(">> 게시글 조회 시작 << id: {}", id);
        // 게시글 조회 서비스 호출
        // 게시글 없으면 예외처리

        UserCommunityResponse.DetailDTO communityDetail = userCommunityService.findById(id, loginUser);
        return ResponseEntity.ok(new ApiUtil<>(communityDetail));
    }

    // 게시글 생성
    @PostMapping("/community")
    public ResponseEntity<?> createCommunity(
            @RequestBody UserCommunityRequest.SaveDTO saveDTO,
            @RequestAttribute(value = Define.LOGIN_USER, required = false) LoginUser loginUser) {
        log.info(">> 게시글 생성 시작 <<");
        if (loginUser.isCompany()) {
            throw new Exception400("일반회원만 접근 가능합니다.");
        }
        User user = loginUser.toUser();
        UserCommunity userCommunity = userCommunityService.savePost(user, saveDTO);
        return ResponseEntity.ok(new ApiUtil<>(userCommunity));



    }

    // 게시글 수정
    @PutMapping("/community/{id}/update")
    public ResponseEntity<?> CommunityUpdate(@PathVariable(name = "id") Long postId,
                                             @RequestBody UserCommunityRequest.UserCommunityUpdateDTO userCommunityUpdateDTO,
                                             @RequestAttribute(value = Define.LOGIN_USER, required = false) LoginUser loginUser) {
        log.info(">> 게시글 수정 시작 << id: {}", postId);

        if (loginUser.isCompany()) {
            throw new Exception400("일반회원만 접근 가능합니다.");
        }
        userCommunityService.communityUpdate(postId, userCommunityUpdateDTO, loginUser);
        return ResponseEntity.ok(new ApiUtil<>("수정 완료"));
    }


    // 게시글 삭제
    @DeleteMapping("/community/{id}/delete")
    public ResponseEntity<?> deleteCommunity(@PathVariable(name = "id") Long postId,
                                             @RequestAttribute(value = Define.LOGIN_USER, required = false) LoginUser loginUser) {
        //삭제할 게시글 조회
        log.info(">> 게시글 삭제 시작 << id: {}", postId);
        if (loginUser.isCompany()) {
            throw new Exception400("일반회원만 접근 가능합니다.");
        }
        // 게시글이 존재하지 않으면 예외 처리
        UserCommunityResponse.DetailDTO detailDTO = userCommunityService.findById(postId, loginUser);
        userCommunityService.deletePost(postId);
        return ResponseEntity.ok(new ApiUtil<>("삭제 완료"));
    }
}
