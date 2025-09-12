package com.tenco.jobpotal.user.comp;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CompUserRequest {

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class JoinDTO {

		@NotEmpty(message = "아이디를 입력해주세요.")
		@Pattern(regexp = "^[a-zA-Z0-9]{2,20}$", message = "아이디는 영문/숫자로 2자 이상 20자 이하로 입력해주세요")
		private String compUserLoginId; // 로그인 아이디

		@NotEmpty(message = "비밀번호를 입력해주세요.")
		@Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
		private String compUserPassword; // 로그인 비밀번호

		@NotEmpty(message = "이름을 입력해주세요.")
		private String compUserName;

		@NotEmpty(message = "전화번호를 입력해주세요.")
		private String compUserPhone;

		@NotEmpty(message = "이메일을 입력해주세요.")
		@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "올바른 이메일 형식이 아닙니다.")
		private String compUserEmail;

		@NotEmpty(message = "닉네임을 입력해주세요.")
		private String compUserNickname;

		@NotEmpty(message = "사업자등록번호를 입력해주세요.")
		private String compRegNumber; // 사업자등록번호

		public CompUser toEntity() {
			return CompUser.builder()
					.compUserLoginId(this.compUserLoginId)
					.compUserPassword(this.compUserPassword)
					.compUserName(this.compUserName)
					.compUserPhone(this.compUserPhone)
					.compUserEmail(this.compUserEmail)
					.compUserNickname(this.compUserNickname)
					.compRegNumber(this.compRegNumber)
					.build();
		}
	}

	// 로그인 DTO
	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class LoginDTO {

		@NotEmpty(message = "아이디를 입력해주세요.")
		@Pattern(regexp = "^[a-zA-Z0-9]{2,20}$", message = "아이디는 영문/숫자로 2자 이상 20자 이하로 입력해주세요")
		private String compUserLoginId; // 로그인 아이디

		@NotEmpty(message = "비밀번호를 입력해주세요.")
		@Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
		private String compUserPassword; // 로그인 비밀번호
	}

	// 회원정보수정 DTO
	@Data
	public static class UpdateDTO {

		@NotEmpty(message = "비밀번호를 입력해주세요.")
		@Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
		private String compUserPassword; // 로그인 비밀번호

		@NotEmpty(message = "이름을 입력해주세요.")
		private String compUserName;

		@NotEmpty(message = "전화번호를 입력해주세요.")
		private String compUserPhone;

		@NotEmpty(message = "이메일을 입력해주세요.")
		@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "올바른 이메일 형식이 아닙니다.")
		private String compUserEmail;

		@NotEmpty(message = "닉네임을 입력해주세요.")
		private String compUserNickname;

		@NotEmpty(message = "사업자등록번호를 입력해주세요.")
		private String compRegNumber; // 사업자등록번호
	}
}
