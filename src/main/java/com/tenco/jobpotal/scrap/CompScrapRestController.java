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
@Tag(name = "CompScrap", description = "企業スクラップAPI")
public class CompScrapRestController {

    private final CompScrapService compScrapService;

    @Operation(summary = "スクラップする")
    @PostMapping("/comp_scrap")
    public ResponseEntity<?> save(@Valid @RequestBody CompScrapRequest.SaveDTO saveDTO, Errors errors,
                                  @RequestAttribute(Define.LOGIN_USER) LoginUser loginUser) {
        CompScrapResponse.SaveDTO responseDTO = compScrapService.save(saveDTO, loginUser);
        return ResponseEntity.ok(new ApiUtil<>(responseDTO));
    }

    @Operation(summary = "スクラップリスト")
    @GetMapping("/comp_scrap/list")
    public ResponseEntity<?> list(@RequestAttribute(Define.LOGIN_USER) LoginUser loginUser) {
      List<CompScrapResponse.ScrapListDTO> compScrapList = compScrapService.findAllByCompUserId(loginUser.getId());
      return ResponseEntity.ok(new ApiUtil<>(compScrapList));
    }


    @Operation(summary = "スクラップ解除")
    @PostMapping("/comp_scrap/{id}/delete")
    public ResponseEntity<ApiUtil<String>> delete(@PathVariable(name = "id") Long id,
                                                  @RequestAttribute(Define.LOGIN_USER) LoginUser loginUser) {
        compScrapService.deleteById(id, loginUser);
        return ResponseEntity.ok(new ApiUtil<>("스크랩 삭제 완료"));
    }

}
