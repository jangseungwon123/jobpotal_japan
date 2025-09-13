package com.tenco.jobpotal.reply.comp_reply;

import com.tenco.jobpotal._core.common.ApiUtil;
import com.tenco.jobpotal._core.utils.Define;
import com.tenco.jobpotal.user.LoginUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Tag(name = "CompCommCmt", description = "企業コミュニティコメントAPI")
public class CompCommCmtRestController {

    private final CompCommCmtService compCommCmtService;

    @Operation(summary = "企業コミュニティコメント一覧取得")
    @GetMapping("/compcommcmts/list")
    public ResponseEntity<ApiUtil<List<CompCommCmtResponse.CompCommCmtListDTO>>> compCommCmtList(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) {

        List<CompCommCmtResponse.CompCommCmtListDTO> commCmtList = compCommCmtService.list(page, size);
        return ResponseEntity.ok(new ApiUtil<>(commCmtList));
    } //

    @Operation(summary = "企業コミュニティコメント一覧取得")
    @GetMapping("/compcommcmts/{id}/detail")
    public ResponseEntity<ApiUtil<CompCommCmtResponse.DetailDTO>> detail(
            @PathVariable(name = "id") Long id, @RequestAttribute(value = Define.LOGIN_USER, required = false) LoginUser loginUser) {
        CompCommCmtResponse.DetailDTO detailDTO = compCommCmtService.detail(id, loginUser);
        return ResponseEntity.ok(new ApiUtil<>(detailDTO));
    }

    @Operation(summary = "企業コミュニティコメント登録")
    @PostMapping("/compcommcmts")
    public ResponseEntity<?> save(@Valid @RequestBody CompCommCmtRequest.SaveDTO saveDTO,
                                  @RequestAttribute(Define.LOGIN_USER) LoginUser loginUser) {

        CompCommCmtResponse.SaveDTO saveCompCommCmt = compCommCmtService.save(saveDTO, loginUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiUtil<>(saveCompCommCmt));
    }

    @Operation(summary = "企業コミュニティコメント修正")
    @PutMapping("/compcommcmts/{id}")
    public ResponseEntity<?> update(@Valid @PathVariable(name = "id") Long id,
                                    @RequestBody CompCommCmtRequest.UpdateDTO updateDTO,
                                    @RequestAttribute(Define.LOGIN_USER) LoginUser loginUser) {
        CompCommCmtResponse.UpdateDTO updateCompCommCmt = compCommCmtService.update(id, updateDTO, loginUser);
        return ResponseEntity.ok(new ApiUtil<>(updateCompCommCmt));
    }

    @Operation(summary = "業コミュニティコメント削除")
    @PostMapping("/compcommcmts/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long compCommCmtId,
                                    @RequestParam(name = "postId") Long postId,
                                    @RequestAttribute(Define.LOGIN_USER) LoginUser loginUser) {

        compCommCmtService.deleteById(compCommCmtId, loginUser);

        return ResponseEntity.ok(new ApiUtil<>("댓글 삭제 성공"));
    }

}