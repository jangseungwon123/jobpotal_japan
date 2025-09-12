package com.tenco.jobpotal.report;

import com.tenco.jobpotal._core.errors.exception.Exception403;
import com.tenco.jobpotal._core.errors.exception.Exception404;
import com.tenco.jobpotal._core.errors.exception.Exception500;
import com.tenco.jobpotal.community.userCommunity.UserCommunity;
import com.tenco.jobpotal.community.userCommunity.UserCommunityJpaRepository;
import com.tenco.jobpotal.user.LoginUser;
import com.tenco.jobpotal.user.normal.User;
import com.tenco.jobpotal.user.normal.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

	private final ReportJpaRepository reportJpaRepository;
	private final UserJpaRepository userJpaRepository;
	private final UserCommunityJpaRepository userCommunityJpaRepository;

	// 신고하기 기능
	@Transactional
	public ReportResponse.SaveDTO createReport(ReportRequest.CreateDTO createDTO, LoginUser loginUser) {
		reportJpaRepository.findByUser_UserIdAndUserCommunity_PostId(loginUser.getId(), createDTO.getPostId())
				.ifPresent(report -> {
					throw new Exception500("이미 신고한 게시글입니다.");
				});

		User user = userJpaRepository.findById(loginUser.getId())
				.orElseThrow(() -> new Exception404("존재하지 않는 유저입니다."));
		UserCommunity userCommunity = userCommunityJpaRepository.findById(createDTO.getPostId())
				.orElseThrow(() -> new Exception404("존재하지 않는 게시글입니다."));

		Report report = Report.builder()
				.user(user)
				.userCommunity(userCommunity)
				.build();

		Report savedReport = reportJpaRepository.save(report);

		return new ReportResponse.SaveDTO(savedReport);

	}

	// Admin Report List Find All
	@Transactional
	public List<ReportResponse.FindAllDTO> findAll(LoginUser loginUser) {
		if (!loginUser.isAdmin()) {
			throw new Exception403("관리자만 가능한 기능입니다.");
		}
		// DB 모든 신고 데이터 조회
		List<Report> reportList = reportJpaRepository.findAll();
		// 조회한 Report Entity List를 ReportResponse.FindAllDTO로 변환하여 리턴
		return reportList.stream()
				.map(ReportResponse.FindAllDTO::new)
				.collect(Collectors.toList());
	}

	@Transactional
	public ReportResponse.DetailDTO findById(Long reportId, LoginUser loginUser) {
		if (!loginUser.isAdmin()) {
			throw new Exception403("관리자만 가능한 기능입니다.");
		}
		// reportId 받아서 해당 신고 데이터 조회, 없으면 예외처리
		Report report = reportJpaRepository.findById(reportId)
				.orElseThrow(() -> new Exception404("해당 신고 내역을 찾을수 없습니다"));
		// 조회한 Report entity를 detailDTO로 변환 및 반환
		return new ReportResponse.DetailDTO(report);
	}

	@Transactional
	public void deleteReport(Long reportId, LoginUser loginUser) {
		// admin login 인지 확인
		if (!loginUser.isAdmin()) {
			throw new Exception403("관리자만 가능한 기능입니다.");
		}
		Report report = reportJpaRepository.findById(reportId)
				.orElseThrow(() -> new Exception404("해당 신고 내역을 찾을수 없습니다"));
		reportJpaRepository.delete(report);
	}

	@Transactional
	public List<ReportResponse.MyReportListDTO> findByUserId(Long userId) {
		// 사용자 ID로 해당 유저의 신고 내역을 모두 조회
		List<Report> myReportList = reportJpaRepository.findByUser_UserId(userId);
		return myReportList.stream()
				.map(ReportResponse.MyReportListDTO::new)
				.collect(Collectors.toList());
	}
}
