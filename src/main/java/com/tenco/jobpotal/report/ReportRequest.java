package com.tenco.jobpotal.report;

import com.tenco.jobpotal.community.userCommunity.UserCommunity;
import com.tenco.jobpotal.user.normal.User;
import lombok.Data;

public class ReportRequest {

	@Data
	public static class CreateDTO {
		private Long reportId;
		private Long postId;
		private Long userId;

		public Report toEntity(Report report, UserCommunity userCommunity, User user) {
			return Report.builder()
					.reportId(reportId)
					.userCommunity(UserCommunity.builder().postId(postId).build())
					.user(User.builder().userId(userId).build())
					.build();

		}
	}
}
