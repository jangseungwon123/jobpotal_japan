package com.tenco.jobpotal.community.compCommunity;

import com.tenco.jobpotal._core.common.ApiUtil;
import com.tenco.jobpotal._core.errors.exception.Exception400;
import com.tenco.jobpotal._core.utils.Define;
import com.tenco.jobpotal.user.LoginUser;
import com.tenco.jobpotal.user.comp.CompUser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "CompCommunity", description = "企業コミュニティAPI")
public class CompCommunityRestController {

    private static final Logger log = LoggerFactory.getLogger(CompCommunityRestController.class);
    private final CompCommunityService compCommunityService;

    // 전체 게시글 조회 (페이징)
    @Operation(summary = "ページング機能")
    @GetMapping("/comp-community/list")
    public ResponseEntity<?> compCommunityList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestAttribute(value = Define.LOGIN_USER, required = false) LoginUser loginUser) {
        
        Page<CompCommunityResponse.ListDTO> communityPage = compCommunityService.findAllPosts(
                PageRequest.of(page, size), 
                loginUser
        );
        return ResponseEntity.ok(new ApiUtil<>(communityPage));
    }

    @Operation(summary = "企業コミュニティ投稿取得")
    @GetMapping("/comp-community/{id}")
    public ResponseEntity<?> getCompCommunity(@PathVariable(name = "id") Long id,
                                          @RequestAttribute(value = Define.LOGIN_USER, required = false) LoginUser loginUser) {

        if(!loginUser.isCompany()){
            throw new Exception400("기업회원만 접근 가능합니다.");
        }
        log.info(">> 기업 커뮤니티 게시글 조회 시작 << id: {}", id);
        // 게시글 조회 서비스 호출
        // 게시글 없으면 예외처리

        CompCommunityResponse.DetailDTO communityDetail = compCommunityService.findById(id, loginUser);
        return ResponseEntity.ok(new ApiUtil<>(communityDetail));
    }

    // 게시글 생성
    @Operation(summary = "企業コミュニティ投稿作成")
    @PostMapping("/comp-community")
    public ResponseEntity<?> createCompCommunity(
            @RequestBody CompCommunityRequest.SaveDTO saveDTO,
            @RequestAttribute(value = Define.LOGIN_USER, required = false) LoginUser loginUser) {
        log.info(">> 기업 커뮤니티 게시글 생성 시작 <<");
        if (!loginUser.isCompany()) {
            throw new Exception400("기업회원만 접근 가능합니다.");
        }
        CompUser compUser = loginUser.toCompUser();
        CompCommunity compCommunity = compCommunityService.savePost(compUser, saveDTO);
        return ResponseEntity.ok(new ApiUtil<>(CompCommunityResponse.SaveDTO.fromEntity(compCommunity)));
    }

    // 게시글 수정
    @Operation(summary = "企業コミュニティ投稿修正")
    @PutMapping("/comp-community/{id}/update")
    public ResponseEntity<?> compCommunityUpdate(@PathVariable(name = "id") Long postId,
                                             @RequestBody CompCommunityRequest.UpdateDTO updateDTO,
                                             @RequestAttribute(value = Define.LOGIN_USER, required = false) LoginUser loginUser) {
        log.info(">> 기업 커뮤니티 게시글 수정 시작 << id: {}", postId);

        if (!loginUser.isCompany()) {
            throw new Exception400("기업회원만 접근 가능합니다.");
        }
        compCommunityService.communityUpdate(postId, updateDTO, loginUser);
        return ResponseEntity.ok(new ApiUtil<>("수정 완료"));
    }

    // 게시글 삭제
    @Operation(summary = "企業コミュニティ投稿削除")
    @DeleteMapping("/comp-community/{id}/delete")
    public ResponseEntity<?> deleteCompCommunity(@PathVariable(name = "id") Long postId,
                                             @RequestAttribute(value = Define.LOGIN_USER, required = false) LoginUser loginUser) {
        compCommunityService.deletePost(postId);
        return ResponseEntity.ok(new ApiUtil<>("삭제 완료"));
    }
}
