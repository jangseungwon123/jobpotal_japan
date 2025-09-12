package com.tenco.jobpotal.user.normal;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserRequest {

    @Data
    public static class JoinDTO {
        @NotEmpty(message = "이름을 입력해주세요")
        @Pattern(regexp = "^[가-힣a-zA-Z]{2,20}$", message = "한글/영문 2~20자 이내로 작성해주세요")
        private String userName;
        @NotEmpty(message = "아이디를 입력해주세요")
        @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$", message = "영문/숫자 조합 4~20자 이내로 작성해주세요")
        private String userLoginId;
        @NotEmpty(message = "비밀번호를 입력해주세요")
        @Size(min = 4, max = 20, message = "비밀번호는 4~20자 이내로 작성해주세요")
        private String userPassword;
        @NotEmpty(message = "이메일은 필수입니다")
        @Pattern(regexp = "^[a-zA-Z0-9]{2,10}@[a-zA-Z0-9]{2,6}\\.[a-zA-Z]{2,3}$",
                message = "이메일 형식으로 작성해주세요")
        private String userEmail;
        @NotEmpty(message = "주소를 입력해주세요")
        @Size(min = 2, max = 100, message = "주소는 2~100자 이내로 작성해주세요")
        private String userAddress;
        @NotEmpty(message = "전화번호를 입력해주세요")
        @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식으로 작성해주세요")
        private String userPhone;
        @NotEmpty(message = "생년월일을 입력해주세요")
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "생년월일 형식으로 작성해주세요")
        private String userBirth;
        @NotEmpty(message = "성별을 선택해주세요")
        private String userGender;
        @NotEmpty(message = "닉네임을 입력해주세요")
        @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,20}$", message = "한글/영문/숫자 조합 2~20자 이내로 작성해주세요")
        private String userNickname;
        @NotEmpty(message = "주민번호를 입력해주세요")
        @Pattern(regexp = "^\\d{6}-\\d{7}$", message = "주민번호 형식으로 작성해주세요")
        private String userCivilSerial;
        @Lob
        private String userImageData;

        public User toEntity() {
            return User.builder()
                    .userName(userName)
                    .userLoginId(userLoginId)
                    .userPassword(userPassword)
                    .userEmail(userEmail)
                    .userAddress(userAddress)
                    .userPhone(userPhone)
                    .userBirth(userBirth)
                    .userGender(userGender)
                    .userNickname(userNickname)
                    .userCivilSerial(userCivilSerial)
                    .userImageData(userImageData)
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
        private String userLoginId;
        @NotEmpty(message = "비밀번호를 입력해주세요") // null, 빈 문자열("") 허용 x
        @Size(min = 4, max = 20, message = "비밀번호는 4~20자 이내로 작성해주세요")
        private String userPassword;
    }

    // 회원 정보 수정용 DTO
    @Data
    public static class UpdateProfileRequestDTO {
        @NotEmpty(message = "이름을 입력해주세요")
        @Pattern(regexp = "^[가-힣a-zA-Z]{2,20}$", message = "한글/영문 2~20자 이내로 작성해주세요")
        private String userName;
        @NotEmpty(message = "비밀번호를 입력해주세요") // null, 빈 문자열("") 허용 x
        @Size(min = 4, max = 20, message = "비밀번호는 4~20자 이내로 작성해주세요")
        private String userPassword;
        @NotEmpty(message = "이메일은 필수입니다")
        @Pattern(regexp = "^[a-zA-Z0-9]{2,10}@[a-zA-Z0-9]{2,6}\\.[a-zA-Z]{2,3}$",
                message = "이메일 형식으로 작성해주세요")
        private String userEmail;
        @NotEmpty(message = "주소를 입력해주세요")
        @Size(min = 2, max = 100, message = "주소는 2~100자 이내로 작성해주세요")
        private String userAddress;
        @NotEmpty(message = "전화번호를 입력해주세요")
        @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식으로 작성해주세요")
        private String userPhone;
        @NotEmpty(message = "닉네임을 입력해주세요")
        @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,20}$", message = "한글/영문/숫자 조합 2~20자 이내로 작성해주세요")
        private String userNickname;
        @Lob
        private String userImageData;
    }

    // 마이페이지- 프로필 조회용 DTO
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyProfileDTO {
        private String userName;
        private String userLoginId;
        private String userEmail;
        private String userAddress;
        private String userPhone;
        private String userBirth;
        private String userNickname;

    }
}
