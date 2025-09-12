package com.tenco.jobpotal.report;

import com.tenco.jobpotal.community.userCommunity.UserCommunity;
import com.tenco.jobpotal.user.normal.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "report_info")
@NoArgsConstructor
public class Report {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reportId;

	// 신고자(누가신고했는지)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	// 신고된 게시글(어떤 게시글을 신고했는지)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id", nullable = false)
	private UserCommunity userCommunity;

	@CreationTimestamp
	private Timestamp createdAt;

	@Builder
	public Report(Long reportId, User user, UserCommunity userCommunity, Timestamp reportCreatedAt) {
		this.reportId = reportId;
		this.user = user;
		this.userCommunity = userCommunity;
	}

}
