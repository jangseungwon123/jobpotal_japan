package com.tenco.jobpotal.user.adminInfo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AdminInfoRequest {

    @Data
    // 회원가입
    public static class AdminJoinDTO {
        @NotEmpty(message = "아이디를 입력해주세요")
        @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$", message = "영문/숫자 조합 4~20자 이내로 작성해주세요")
        private String adminLoginId;
        @NotEmpty(message = "비밀번호를 입력해주세요")
        @Size(min = 4, max = 20, message = "비밀번호는 4~20자 이내로 작성해주세요")
        private String adminPassword;
        @NotEmpty(message = "이름을 입력해주세요")
        @Pattern(regexp = "^[가-힣a-zA-Z]{2,20}$", message = "한글/영문 2~20자 이내로 작성해주세요")
        private String adminName;
        @NotEmpty(message = "이메일은 필수입니다")
        @Pattern(regexp = "^[a-zA-Z0-9]{2,10}@[a-zA-Z0-9]{2,6}\\.[a-zA-Z]{2,3}$",
                message = "이메일 형식으로 작성해주세요")
        private String adminEmail;
        @NotEmpty(message = "전화번호를 입력해주세요")
        @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식으로 작성해주세요")
        private String adminPhone;

        public AdminInfo toEntity() {
            return AdminInfo.builder()
                    .adminLoginId(adminLoginId)
                    .adminPassword(adminPassword)
                    .adminName(adminName)
                    .adminEmail(adminEmail)
                    .adminPhone(adminPhone)
                    .build();
        }
    }

    // 로그인 용 DTO
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginDTO {
        @NotEmpty(message = "아이디를 입력해주세요")
        @Pattern(regexp = "^[a-zA-Z0-9]{2,20}$", message = "영문/숫자/2~20자 이내로 작성해주세요")
        private String adminLoginId;
        @NotEmpty(message = "비밀번호를 입력해주세요") // null, 빈 문자열("") 허용 x
        @Size(min = 4, max = 20, message = "비밀번호는 4~20자 이내로 작성해주세요")
        private String adminPassword;
    }

    // 회원 정보 수정용 DTO
    @Data
    public static class UpdateDTO {
        @NotEmpty(message = "현재 비밀번호를 입력해주세요")
        @Size(min = 4, max = 20, message = "비밀번호는 4~20자 이내로 작성해주세요")
        private String currentPassword;
        @NotEmpty(message = "새로운 비밀번호를 입력해주세요")
        @Size(min = 4, max = 20, message = "비밀번호는 4~20자 이내로 작성해주세요")
        private String newPassword;
    }
}
