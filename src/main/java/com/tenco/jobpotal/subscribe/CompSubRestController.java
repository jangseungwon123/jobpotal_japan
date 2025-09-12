package com.tenco.jobpotal.subscribe;

import com.tenco.jobpotal._core.common.ApiUtil;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "CompSub", description = "企業購読管理API")
public class CompSubRestController {

    private final CompSubService compSubService;

    @Operation(summary = "購読する", operationId = "CompSubSave")
    @PostMapping("/comp_sub")
    public ResponseEntity<?> save(@Valid @RequestBody CompSubRequest.SaveDTO saveDTO, Errors error,
                                  @RequestAttribute(Define.LOGIN_USER)LoginUser loginUser) {
        CompSubResponse.SaveDTO responseDTO = compSubService.save(saveDTO, loginUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiUtil<>(responseDTO));
    }

    @Operation(summary = "購読リスト")
    @GetMapping("/comp_sub/list")
    public ResponseEntity<?> list(@RequestAttribute(Define.LOGIN_USER) LoginUser loginUser) {
        List<CompSubResponse.SubListDTO> userSubList = compSubService.findAllByUserAndCompanyId(loginUser);
        return ResponseEntity.ok(new ApiUtil<>(userSubList));
    }

    @Operation(summary = "購読解除")
    @DeleteMapping("/comp_sub/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id,
                                                  @RequestAttribute(Define.LOGIN_USER) LoginUser loginUser) {
        compSubService.deleteById(id, loginUser);
        return ResponseEntity.ok(new ApiUtil<>("구독 삭제 완료"));
    }

}
