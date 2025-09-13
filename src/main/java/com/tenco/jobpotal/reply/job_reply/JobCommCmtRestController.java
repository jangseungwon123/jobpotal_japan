package com.tenco.jobpotal.reply.job_reply;

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
@Tag(name = "JobCommCmt", description = "求職者コミュニティコメントAPI")
public class JobCommCmtRestController {

    private final JobCommCmtService jobCommCmtService;


    @Operation(summary = "求職者コミュニティコメント一覧取得")
    @GetMapping("/jobcommcmts/list")
    public ResponseEntity<ApiUtil<List<JobCommCmtResponse.JobCommCmtListDTO>>> jobCommCmtList(
            @RequestParam(name = "page",defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5")int size){

        List<JobCommCmtResponse.JobCommCmtListDTO> jobCommCmtList = jobCommCmtService.list(page,size);
        return ResponseEntity.ok(new ApiUtil<>(jobCommCmtList));
    }

    @Operation(summary = "求職者コミュニティコメント詳細取得")
    @GetMapping("/jobcommcmts/{id}/detail")
    public ResponseEntity<ApiUtil<JobCommCmtResponse.DetailDTO>> detail(
            @PathVariable(name = "id")Long id,@RequestAttribute(value = Define.LOGIN_USER,required = false)LoginUser loginUser) {
        JobCommCmtResponse.DetailDTO detailDTO = jobCommCmtService.detail(id,loginUser);
        return ResponseEntity.ok(new ApiUtil<>(detailDTO));
    }

    @Operation(summary = "求職者コミュニティコメント登録機能")
    @PostMapping("/jobcommcmts")
    public ResponseEntity<?> save(@Valid @RequestBody JobCommCmtRequest.SaveDTO saveDTO,
                                  @RequestAttribute(Define.LOGIN_USER) LoginUser loginUser) {

        JobCommCmtResponse.SaveDTO saveJobCommCmt = jobCommCmtService.save(saveDTO, loginUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiUtil<>(saveJobCommCmt));
    }

    @Operation(summary = "求職者コミュニティコメント修正")
    @PutMapping("/jobcommcmts/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id")Long id,
                                    @Valid @RequestBody JobCommCmtRequest.UpdateDTO updateDTO,
                                    @RequestAttribute(Define.LOGIN_USER)LoginUser loginUser){
        JobCommCmtResponse.UpdateDTO updateJobCommCmt = jobCommCmtService.update(id,updateDTO,loginUser);
        return ResponseEntity.ok(new ApiUtil<>(updateJobCommCmt));
    }

    @Operation(summary = "求職者コミュニティコメント削除")
    @PostMapping("/jobcommcmts/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long jobCommCmtId,
                                    @RequestParam(name = "postId") Long postId,
                                    @RequestAttribute(Define.LOGIN_USER) LoginUser loginUser) {

        jobCommCmtService.deleteById(jobCommCmtId, loginUser);

        return ResponseEntity.ok(new ApiUtil<>("댓글 삭제 성공"));
    }
}
