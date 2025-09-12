package com.tenco.jobpotal.report;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Report", description = "通報管理API")
public class ReportRestController {
	private final ReportService reportService;

	@Operation(summary = "通報する", description = "유저가 게시글에서 신고하는 기능")
	@PostMapping("/reports")
	public ResponseEntity<?> report(
			@Valid @RequestBody ReportRequest.CreateDTO createDTO, Errors errors,
			@RequestAttribute(Define.LOGIN_USER) LoginUser loginUser) {
		ReportResponse.SaveDTO responseDTO = reportService.createReport(createDTO, loginUser);
		return ResponseEntity.ok(new ApiUtil<>(responseDTO));
	}
	// 관리자용 신고 관리 기능
	// 1. 전체 신고 목록 조회
	// 2. 신고 상세 조회
	// 3. 신고 내역 삭제
	// 사용자용 신고 관리 기능
	// 1. 내 신고 내역 조회

	@Operation(summary = "管理者が通報一覧取得", description = "관리자가 전체 신고 목록을 조회하는 기능")
	@GetMapping("/admin/reports")
	public ResponseEntity<?> reportList(@RequestAttribute(Define.LOGIN_USER) LoginUser loginUser) {
		List<ReportResponse.FindAllDTO> responseDTO = reportService.findAll(loginUser);
		return ResponseEntity.ok(new ApiUtil<>(responseDTO));
	}

	@Operation(summary = "管理者が通報詳細取得", description = "관리자가 신고 내역을 상세 조회하는 기능")
	@GetMapping("/admin/reports/{reportId}")
	public ResponseEntity<?> reportDetail(
			@PathVariable Long reportId, @RequestAttribute(Define.LOGIN_USER) LoginUser loginUser) {
		ReportResponse.DetailDTO responseDTO = reportService.findById(reportId, loginUser);
		return ResponseEntity.ok(new ApiUtil<>(responseDTO));
	}

	@Operation(summary = "管理者が通報内訳削除")
	@DeleteMapping("/admin/reports/{reportId}")
	public ResponseEntity<?> reportDelete(@PathVariable Long reportId, @RequestAttribute(Define.LOGIN_USER) LoginUser loginUser) {
		reportService.deleteReport(reportId, loginUser);
		return ResponseEntity.ok(new ApiUtil<>(null));
	}

	@Operation(summary = "自分の通報履歴一覧取得")
	@GetMapping("/my-reports")
	public ResponseEntity<?> myReportList(@RequestAttribute(Define.LOGIN_USER) LoginUser loginUser) {
		List<ReportResponse.MyReportListDTO> responseDTO = reportService.findByUserId(loginUser.getId());
		return ResponseEntity.ok(new ApiUtil<>(responseDTO));
	}
}
