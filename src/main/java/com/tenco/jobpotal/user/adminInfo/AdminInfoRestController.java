package com.tenco.jobpotal.user.adminInfo;

import com.tenco.jobpotal._core.common.ApiUtil;
import com.tenco.jobpotal._core.errors.exception.Exception401;
import com.tenco.jobpotal._core.utils.Define;
import com.tenco.jobpotal.user.LoginUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Tag(name = "Admin", description = "管理者API")
public class AdminInfoRestController {

    private final AdminInfoService adminInfoService;

    @Operation(summary = "管理者会員登録", description = "로그인한 관리자가 새로운 관리자를 생성합니다.")
    @PostMapping("/admins/join")
    public ResponseEntity<?> adminJoin(@RequestAttribute(Define.LOGIN_USER) LoginUser loginUser,
                                       @Valid @RequestBody AdminInfoRequest.AdminJoinDTO joinDTO,
                                       Errors errors) {
        if (loginUser == null) {
            throw new Exception401("인증 정보가 없습니다");
        }
        AdminInfoResponse.JoinDTO joinAdmin = adminInfoService.join(joinDTO, loginUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiUtil<>(joinAdmin));
    }

    @Operation(summary = "ログイン", description = "관리자 로그인")
    @PostMapping("/admins/login")
    public ResponseEntity<?> adminLogin(@Valid @RequestBody AdminInfoRequest.LoginDTO loginDTO, Errors errors) {
        String jwtToken = adminInfoService.login(loginDTO);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + jwtToken)
                .body(new ApiUtil<>(null));
    }

    @Operation(summary = "管理者情報取得", description = "자신의 정보 또는 다른 관리자의 정보를 조회한다.")
    @GetMapping("/admins/{id}")
    public ResponseEntity<?> getAdminInfo(@PathVariable(name = "id") Long id,
                                          @RequestAttribute(Define.LOGIN_USER) LoginUser loginUser) {
        if (loginUser == null) {
            throw new Exception401("인증 정보가 없습니다");
        }
        AdminInfoResponse.DetailDTO adminDetail = adminInfoService.findByTargetId(id, loginUser);
        return ResponseEntity.ok(new ApiUtil<>(adminDetail));
    }


    @Operation(summary = "管理者情報修正", description = "관리자 정보를 수정한다.")
    @PutMapping("/admins/{id}")
    public ResponseEntity<?> updateAdminInfo(@PathVariable(name = "id") Long id,
                                             @RequestAttribute(Define.LOGIN_USER) LoginUser loginUser,
                                             @Valid @RequestBody AdminInfoRequest.UpdateDTO updateDTO, Errors errors) {
        adminInfoService.updateByAdmin(id, updateDTO, loginUser);
        return ResponseEntity.ok().body(new ApiUtil<>("수정되었습니다."));
    }

    @Operation(summary = "ログアウト", description = "관리자 로그아웃")
    @PostMapping("/admins/logout")
    public ResponseEntity<?> adminLogout() {
        return ResponseEntity.ok(new ApiUtil<>("로그아웃 성공"));
    }

    @Operation(summary = "アカウント削除", description = "관리자 계정 삭제")
    @DeleteMapping("/admins/{id}/delect")
    public ResponseEntity<?> adminDelete(@PathVariable(name = "id") Long id,
                                         @RequestAttribute(Define.LOGIN_USER) LoginUser loginUser) {
        adminInfoService.deleteByAdmin(loginUser, id);
        return ResponseEntity.ok(new ApiUtil<>("삭제 완료"));
    }


}
