package com.tenco.jobpotal.subscribe;

import com.tenco.jobpotal._core.common.ApiUtil;
import com.tenco.jobpotal._core.utils.Define;
import com.tenco.jobpotal.user.LoginUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Tag(name = "UserSub", description = "ユーザー購読管理API")
public class UserSubRestController {

    private final UserSubService userSubService;


    @PostMapping("/user_sub")
    @Operation(summary = "購読する")
    public ResponseEntity<?> save(@Valid @RequestBody UserSubRequest.SaveDTO saveDTO, Errors errors,
                                  @RequestAttribute(Define.LOGIN_USER) LoginUser loginUser) {
        UserSubResponse.SaveDTO responseDTO = userSubService.save(saveDTO, loginUser);
        return ResponseEntity.ok(new ApiUtil<>(responseDTO));
    }

    @Operation(summary = "購読リスト")
    @GetMapping("/user_sub/list")
    public ResponseEntity<?> list(@RequestAttribute(Define.LOGIN_USER) LoginUser loginUser) {
      List<UserSubResponse.SubListDTO> userSubList = userSubService.findAllByUserAndCompanyId(loginUser.getId());
      return ResponseEntity.ok(new ApiUtil<>(userSubList));
    }


    @Operation(summary = "購読解除")
    @PostMapping("/user_sub/{id}/delete")
    public ResponseEntity<ApiUtil<String>> delete(@PathVariable(name = "id") Long id,
                                                  @RequestAttribute(Define.LOGIN_USER) LoginUser loginUser) {
        userSubService.deleteById(id, loginUser);
        return ResponseEntity.ok(new ApiUtil<>("구독 삭제 완료"));
    }

}
