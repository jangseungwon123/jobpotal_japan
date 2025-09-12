package com.tenco.jobpotal.resume;

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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Tag(name = "Resume", description = "ユーザー履歴書API")
public class ResumeRestController {

    private final ResumeService resumeService;

    @Operation(summary = "履歴書一覧取得")
    @GetMapping("/resumes")
    public ResponseEntity<ApiUtil<List<ResumeResponse.ResumeListResponseDTO>>> resumeList(
            @RequestParam(name = "page",defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5")int size){

        List<ResumeResponse.ResumeListResponseDTO> resumeList = resumeService.list(page,size);
        return ResponseEntity.ok(new ApiUtil<>(resumeList));
    }


    @Operation(summary = "履歴書詳細取得")
    @GetMapping("/resumes/{id}/detail")
    public ResponseEntity<ApiUtil<ResumeResponse.DetailDTO>> detail(
            @PathVariable(name = "id")Long id,@RequestAttribute(value = Define.LOGIN_USER,required = false)LoginUser loginUser) {

        ResumeResponse.DetailDTO detailDTO = resumeService.detail(id,loginUser);
        return ResponseEntity.ok(new ApiUtil<>(detailDTO));
    }


    @Operation(summary = "履歴書保存")
    @PostMapping("/resumes")
    public ResponseEntity<?> save(@Valid @RequestBody ResumeRequest.SaveDTO saveDTO,
                                  Errors errors,
                                  @RequestAttribute(Define.LOGIN_USER)LoginUser loginUser) {

        resumeService.save(saveDTO,loginUser);
        return ResponseEntity.ok(new ApiUtil<>("이력서 저장 완료"));
    }

    @Operation(summary = "履歴書修正")
    @PutMapping("/resumes/{id}")
    public ResponseEntity<?> update(@Valid @PathVariable(name = "id")Long id,
                                    @RequestBody ResumeRequest.UpdateDTO updateDTO,
                                    Errors errors,
                                    @RequestAttribute(Define.LOGIN_USER)LoginUser loginUser) {

        resumeService.update(id,updateDTO,loginUser);
        return ResponseEntity.ok(new ApiUtil<>("이력서 수정 완료"));
    }

    @Operation(summary = "履歴書削除")
    @DeleteMapping("/resumes/{id}")
    public ResponseEntity<ApiUtil<String>> delete(
            @PathVariable(name = "id") Long id,
            @RequestAttribute(Define.LOGIN_USER) LoginUser loginUser) {
        resumeService.deleteById(id, loginUser);
        return ResponseEntity.ok(new ApiUtil<>("이력서 삭제 성공"));
    }



}
