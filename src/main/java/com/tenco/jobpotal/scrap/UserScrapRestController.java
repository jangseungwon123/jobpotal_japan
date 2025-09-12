package com.tenco.jobpotal.scrap;

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
@Tag(name = "UserScrap", description = "ユーザースクラップAPI")
public class UserScrapRestController {

    private final UserScrapService userScrapService;


    @PostMapping("/user_scrap")
    @Operation(summary = "スクラップする")
    public ResponseEntity<?> save(@Valid @RequestBody UserScrapRequest.SaveDTO saveDTO, Errors errors,
                                  @RequestAttribute(Define.LOGIN_USER) LoginUser loginUser) {
        UserScrapResponse.SaveDTO responseDTO = userScrapService.save(saveDTO, loginUser);
        return ResponseEntity.ok(new ApiUtil<>(responseDTO));
    }

    @Operation(summary = "スクラップリスト")
    @GetMapping("/user_scrap/list")
    public ResponseEntity<?> list(@RequestAttribute(Define.LOGIN_USER) LoginUser loginUser) {
      List<UserScrapResponse.ScrapListDTO> userScrapList = userScrapService.findAllByUserAndJobPostId(loginUser.getId());
      return ResponseEntity.ok(new ApiUtil<>(userScrapList));
    }

    @Operation(summary = "スクラップ解除")
    @PostMapping("/user_scrap/{id}/delete")
    public ResponseEntity<ApiUtil<String>> delete(@PathVariable(name = "id") Long id,
                                                  @RequestAttribute(Define.LOGIN_USER) LoginUser loginUser) {
        userScrapService.deleteById(id, loginUser);
        return ResponseEntity.ok(new ApiUtil<>("채용공고 삭제 완료"));
    }

}
