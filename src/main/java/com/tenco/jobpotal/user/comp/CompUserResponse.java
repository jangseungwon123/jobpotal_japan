package com.tenco.jobpotal.user.comp;

import lombok.Builder;
import lombok.Data;

public class CompUserResponse {

	// 회원가입 후 응답 DTO
	@Data
	public static class JoinDTO {
		private Long compUserId;
		private String compUserLoginId; // 로그인 아이디
		private String compUserPassword; // 로그인 비밀번호
		private String compUserName;
		private String compUserPhone;
		private String compUserEmail;
		private String compUserNickname;
		private String compRegNumber; // 사업자등록번호
		private String compUserCreatedTime;

		@Builder
		public JoinDTO(CompUser compUser) {
			this.compUserId = compUser.getCompUserId();
			this.compUserLoginId = compUser.getCompUserLoginId();
			this.compUserPassword = compUser.getCompUserPassword();
			this.compUserName = compUser.getCompUserName();
			this.compUserPhone = compUser.getCompUserPhone();
			this.compUserEmail = compUser.getCompUserEmail();
			this.compUserNickname = compUser.getCompUserNickname();
			this.compRegNumber = compUser.getCompRegNumber();
		}
	}

	// 로그인 후 응답 DTO
	@Data
	public static class LoginDTO {
		private Long compUserId;
		private String compUserLoginId; // 로그인 아이디
		private String compUserPassword; // 로그인 비밀번호
		private String compUserName;
		private String compUserPhone;
		private String compUserEmail;
		private String compUserNickname;
		private String compRegNumber; // 사업자등록번호

		@Builder
		public LoginDTO(CompUser compUser) {
			this.compUserId = compUser.getCompUserId();
			this.compUserLoginId = compUser.getCompUserLoginId();
			this.compUserPassword = compUser.getCompUserPassword();
			this.compUserName = compUser.getCompUserName();
			this.compUserPhone = compUser.getCompUserPhone();
			this.compUserEmail = compUser.getCompUserEmail();
			this.compUserNickname = compUser.getCompUserNickname();
			this.compRegNumber = compUser.getCompRegNumber();
		}
	}

	// 회원정보수정 후 응답 DTO
	@Data
	public static class UpdateDTO {
		private Long compUserId;
		private String compUserLoginId; // 로그인 아이디
		private String compUserPassword; // 로그인 비밀번호
		private String compUserName;
		private String compUserPhone;
		private String compUserEmail;
		private String compUserNickname;
		private String compRegNumber; // 사업자등록번호

		@Builder
		public UpdateDTO(CompUser compUser) {
			this.compUserId = compUser.getCompUserId();
			this.compUserLoginId = compUser.getCompUserLoginId();
			this.compUserPassword = compUser.getCompUserPassword();
			this.compUserName = compUser.getCompUserName();
			this.compUserPhone = compUser.getCompUserPhone();
			this.compUserEmail = compUser.getCompUserEmail();
			this.compUserNickname = compUser.getCompUserNickname();
			this.compRegNumber = compUser.getCompRegNumber();
		}
	}

	// 회원정보조회 후 응답 DTO
	@Data
	public static class DetailDTO {
		private Long compUserId;
		private String compUserLoginId; // 로그인 아이디
		private String compUserName;
		private String compUserPhone;
		private String compUserEmail;
		private String compUserNickname;
		private String compRegNumber; // 사업자등록번호

		@Builder
		public DetailDTO(CompUser compUser) {
			this.compUserId = compUser.getCompUserId();
			this.compUserLoginId = compUser.getCompUserLoginId();
			this.compUserName = compUser.getCompUserName();
			this.compUserPhone = compUser.getCompUserPhone();
			this.compUserEmail = compUser.getCompUserEmail();
			this.compUserNickname = compUser.getCompUserNickname();
			this.compRegNumber = compUser.getCompRegNumber();
		}
		public CompUser toEntity() {
			return CompUser.builder()
					.compUserId(this.getCompUserId())
					.compUserLoginId(this.getCompUserLoginId())
					.compUserName(this.getCompUserName())
					.compUserPhone(this.getCompUserPhone())
					.compUserEmail(this.getCompUserEmail())
					.compUserNickname(this.getCompUserNickname())
					.compRegNumber(this.getCompRegNumber())
					.build();
		}
	}
}
