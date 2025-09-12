package com.tenco.jobpotal.report;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

public class ReportResponse {


	// 신고하기(저장)
	@Data
	public static class SaveDTO{
		private Long reportId;
		private Long postId;
		private Long userId;
		private Timestamp createdAt;

		@Builder
		public SaveDTO(Report report) {
			this.reportId = report.getReportId();
			this.postId = report.getUserCommunity().getPostId();
			this.userId = report.getUser().getUserId();
			this.createdAt = report.getCreatedAt();
		}
	}

	@Data
	public static class FindAllDTO {
		private Long reportId;            // 신고 ID
		private String reporterNickname;  // 신고자 닉네임
		private Long reportedPostId;      // 신고된 게시글 ID
		private String reportedPostTitle; // 신고된 게시글 제목
		private Timestamp createdAt;      // 신고 생성일

		@Builder
		public FindAllDTO(Report report) {
			this.reportId = report.getReportId();
			this.reporterNickname = report.getUser().getUserNickname();
			this.reportedPostId = report.getUserCommunity().getPostId();
			this.reportedPostTitle = report.getUserCommunity().getTitle();
			this.createdAt = report.getCreatedAt();
		}
	}

	@Data
	public static class DetailDTO {
		private Long reportId;
		private String reporterNickname;
		private Long reportedPostId;
		private String reportedPostTitle;
		private String reportedPostContent;
		private Timestamp createdAt;

		@Builder
		public DetailDTO(Report report) {
			this.reportId = report.getReportId();
			this.reporterNickname = report.getUser().getUserNickname();
			this.reportedPostId = report.getUserCommunity().getPostId();
			this.reportedPostTitle = report.getUserCommunity().getTitle();
			this.reportedPostContent = report.getUserCommunity().getContent();
			this.createdAt = report.getCreatedAt();
		}
	}

	@Data
	public static class MyReportListDTO {
		private Long reportId;
		private Long reportPostId;
		private String reportedPostTitle;
		private Timestamp createdAt;

		@Builder
		public MyReportListDTO(Report report) {
			this.reportId = report.getReportId();
			this.reportPostId = report.getUserCommunity().getPostId();
			this.reportedPostTitle = report.getUserCommunity().getTitle();
			this.createdAt = report.getCreatedAt();
		}
	}
}
