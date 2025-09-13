package com.tenco.jobpotal.company;

import com.tenco.jobpotal._core.common.ApiUtil;
import com.tenco.jobpotal._core.errors.exception.Exception500;
import com.tenco.jobpotal._core.utils.Define;
import com.tenco.jobpotal.user.*;
import com.tenco.jobpotal.user.comp.CompUser;
import com.tenco.jobpotal.user.comp.CompUserService;
import com.tenco.jobpotal.user.normal.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Tag(name = "CompInfo", description = "企業API")
public class CompInfoRestController {


    private static final Logger log = LoggerFactory.getLogger(CompInfoRestController.class);
    private final CompInfoService companyService;
    private final CompUserService compUserService;
    

    //전체 게시글 조회 and 제목 검색한 게시글 조회
    @Operation(summary = "投稿一覧")
    @GetMapping("/company/list")
    public ResponseEntity<?> companyInfoList(
                                  @RequestParam(required = false) String type, // todo 검색 시 회사 종류도 선택해서 처리 추가할지 고민필요
                                  @RequestParam(required = false) String keyword,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size) {

        List<CompInfoResponse.MainDTO> compInfoPage;

        if (keyword != null && !keyword.trim().isEmpty()) {

            compInfoPage = companyService.findAllCompanyInfo(page, size, keyword);

            return ResponseEntity.ok(new ApiUtil<>(compInfoPage));
        } else {
            compInfoPage = companyService.findAllCompanyInfo(page, size, keyword);
            return ResponseEntity.ok(new ApiUtil<>(compInfoPage));
        }
    }

    // 기업정보 상세
    @Operation(summary = "企業情報詳細")
    @GetMapping("/company/{id}")
    public ResponseEntity<?> companyInfoDetail(@PathVariable(name = "id") Long id,
                                               @RequestAttribute(value = Define.LOGIN_USER, required = false) LoginUser loginUser) {

        log.info(">> 기업 상세정보 조회 시작 << ");
        CompInfoResponse.DetailDTO companyInfoDetail = companyService.findCompanyInfoById(id, loginUser);

        return ResponseEntity.ok(new ApiUtil<>(companyInfoDetail));
    }

    // 기업정보 등록
    @Operation(summary = "企業情報登録")
    @PostMapping("/company/form")
    public ResponseEntity<?> companyInfoInsert(@Valid @RequestBody CompInfoRequest.SaveDTO saveDTO,
                                               Errors errors,
                                               @RequestAttribute(value = Define.LOGIN_USER, required = false) LoginUser loginUser) {

        log.info(">> 기업정보 등록 시작 << ");

        // 존재하는 유저인지 확인
        CompUser compUser = compUserService.findCompUserByCompUserId(loginUser.getId()).toEntity();

        // 등록 처리
        CompInfo companyInfo = companyService.companyInfoInsert(saveDTO, loginUser, compUser);

        if (companyInfo == null) {
            throw new Exception500("등록 처리 중 에러가 발생했습니다. 관리자에게 문의 하세요.");
        }

        return ResponseEntity.ok(new ApiUtil<>("기업 등록이 완료 되었습니다."));
    }

    // 기업정보 수정
    @Operation(summary = "企業情報修正")
    @PutMapping("/company/{id}/update")
    public ResponseEntity<?> companyInfoUpdate(@PathVariable(name = "id") Long id, @Valid @RequestBody CompInfoRequest.UpdateDTO updateDTO,
                                               Errors errors,
                                    @RequestAttribute(value = Define.LOGIN_USER, required = false) LoginUser loginUser) {

        log.info(">> 기업정보 수정 시작 << ");

        CompInfo companyInfo = companyService.companyInfoUpdate(id, updateDTO, loginUser);

        if (companyInfo == null) {
            throw new Exception500("수정 처리 중 에러가 발생했습니다.");
        }

        return ResponseEntity.ok(new ApiUtil<>("기업정보 수정이 완료 되었습니다."));
    }

    @Operation(summary = "企業情報削除")
    @DeleteMapping("/company/{id}/delete")
    public ResponseEntity<?> companyInfoDelete(@PathVariable(name = "id") Long id, @RequestAttribute(value = Define.LOGIN_USER, required = false) LoginUser loginUser) {

        log.info(">> 기업정보 삭제 시작 << ");

        companyService.companyInfoDelete(loginUser, id);
        return ResponseEntity.ok(new ApiUtil<>("기업 정보 삭제를 완료 하었습니다."));
    }
}
