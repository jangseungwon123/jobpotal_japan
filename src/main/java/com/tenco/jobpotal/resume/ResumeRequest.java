package com.tenco.jobpotal.resume;

import com.tenco.jobpotal.user.normal.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class ResumeRequest {

    // 이력서 저장 DTO
    @Data
    public static class SaveDTO {
        @NotEmpty(message = "이름은 필수입니다")
        private String name;
        @NotEmpty(message = "제목은 필수입니다")
        @Size(min = 1, max = 100, message = "제목은 1~100자 이내로 작성해주세요")
        private String title;

        private Long skillId;

        @NotEmpty(message = "내용은 필수입니다")
        @Size(min = 1, max = 1000, message = "내용은 1~5000자 이내로 작성해주세요")
        private String content;
        @NotEmpty(message = "전화번호는 필수입니다")
        @Size(min = 11, max = 11, message = "전화번호는 -제외 11자여야 입니다")
        private String phone;
        @NotEmpty(message = "주소는 필수입니다")
        @Size(min = 5, max = 255, message = "주소는 5~255자여야 합니다")
        private String address;
        @NotEmpty(message = "생년월일은 필수입니다")
        @Size(min = 10, max = 10, message = "생년월일은 yyyy-MM-dd 형식이어야 합니다")
        private String birth;
        @NotEmpty(message = "이메일은 필수입니다")
        @Pattern(regexp = "^[a-zA-Z0-9]{2,10}@[a-zA-Z0-9]{2,6}\\.[a-zA-Z]{2,3}$",
                message = "이메일 형식으로 작성해주세요"
        )
        private String email;
        @NotEmpty(message = "성별은 필수입니다")
        private String gender;
        private char isExperienced;
        private char isShow;

        public Resume toEntity(User user) {
            return Resume.builder()
                    .name(this.name)
                    .title(this.title)
                    .user(user)
                    .content(this.content)
                    .phone(this.phone)
                    .address(this.address)
                    .birth(this.birth)
                    .email(this.email)
                    .gender(this.gender)
                    .isExperienced(this.isExperienced)
                    .isShow(this.isShow)
                    .build();
        }
    }
    // 이력서 수정 DTO
    @Data
    public static class UpdateDTO {
        @NotEmpty(message = "이름은 필수입니다")
        private String name;
        @NotEmpty(message = "제목은 필수입니다")
        @Size(min = 1, max = 100, message = "제목은 1~100자 이내로 작성해주세요")
        private String title;

        @NotNull(message = "스킬 선택은 필수 입니다.")
        private Long skillId;

        @NotEmpty(message = "내용은 필수입니다")
        @Size(min = 1, max = 1000, message = "내용은 1~5000자 이내로 작성해주세요")
        private String content;
        @NotEmpty(message = "전화번호는 필수입니다")
        @Size(min = 11, max = 11, message = "전화번호는 -제외 11자여야 입니다")
        private String phone;
        @NotEmpty(message = "주소는 필수입니다")
        @Size(min = 5, max = 255, message = "주소는 5~255자여야 합니다")
        private String address;
        @NotEmpty(message = "생년월일은 필수입니다")
        @Size(min = 10, max = 10, message = "생년월일은 yyyy-MM-dd 형식이어야 합니다")
        private String birth;
        @NotEmpty(message = "이메일은 필수입니다")
        @Pattern(regexp = "^[a-zA-Z0-9]{2,10}@[a-zA-Z0-9]{2,6}\\.[a-zA-Z]{2,3}$",
                message = "이메일 형식으로 작성해주세요"
        )
        private String email;
        @NotEmpty(message = "성별은 필수입니다")
        private String gender;
        private char isExperienced;
        private char isShow;

    }

}