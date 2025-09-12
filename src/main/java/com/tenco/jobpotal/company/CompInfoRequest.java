package com.tenco.jobpotal.company;



import com.tenco.jobpotal.user.LoginUser;
import com.tenco.jobpotal.user.comp.CompUser;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

public class CompInfoRequest {

    @Data
    public static class SaveDTO {
        @NotEmpty(message = "회사명을 입력 해주세요")
        private String companyName;
        @NotEmpty(message = "회사 설명을 입력 해주세요")
        private String companyDesc;
        @NotEmpty(message = "CEO 이름을 입력 해주세요")
        private String companyCeoName;
        private String homepageUrl;
        @NotEmpty(message = "회사 전화번호를 입력 해주세요")
        @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식으로 작성해주세요")
        private String phoneNumber;
        @NotEmpty(message = "회사 이메일을 입력 해주세요")
        @Pattern(regexp = "^[a-zA-Z0-9]{2,10}@[a-zA-Z0-9]{2,6}\\.[a-zA-Z]{2,3}$",
                message = "이메일 형식으로 작성해주세요")
        private String companyEmail;
        @NotEmpty(message = "회사 주소를 입력해주세요")
        private String companyAddress;
        @Lob
        private String companyImageData;

        public CompInfo toEntity(LoginUser loginUser, CompUser compUser) {
            return CompInfo.builder()
                    .compUser(compUser)
                    .companyName(companyName)
                    .companyDesc(companyDesc)
                    .companyCeoName(companyCeoName)
                    .homepageUrl(homepageUrl)
                    .phoneNumber(phoneNumber)
                    .companyEmail(companyEmail)
                    .companyAddress(companyAddress)
                    .companyImageData(companyImageData)
                    .instId(loginUser.getLoginId())
                    .build();
        }
    }

    @Data
    public static class UpdateDTO {

        @NotEmpty(message = "회사명을 입력 해주세요")
        private String companyName;
        @NotEmpty(message = "회사 설명을 입력 해주세요")
        private String companyDesc;
        @NotEmpty(message = "CEO 이름을 입력 해주세요")
        private String companyCeoName;
        private String homepageUrl;
        @NotEmpty(message = "회사 전화번호를 입력 해주세요")
        @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식으로 작성해주세요")
        private String phoneNumber;
        @NotEmpty(message = "회사 이메일을 입력 해주세요")
        @Pattern(regexp = "^[a-zA-Z0-9]{2,10}@[a-zA-Z0-9]{2,6}\\.[a-zA-Z]{2,3}$",
                message = "이메일 형식으로 작성해주세요")
        private String companyEmail;
        @NotEmpty(message = "회사 주소를 입력해주세요")
        private String companyAddress;
        private String companyImageData;

        public CompInfo toEntity(LoginUser loginUser, CompUser compUser) {
            return CompInfo.builder()
                    .compUser(compUser)
                    .companyName(companyName)
                    .companyDesc(companyDesc)
                    .companyCeoName(companyCeoName)
                    .homepageUrl(homepageUrl)
                    .phoneNumber(phoneNumber)
                    .companyEmail(companyEmail)
                    .companyAddress(companyAddress)
                    .companyImageData(companyImageData)
                    .instId(loginUser.getLoginId())
                    .build();
        }
    }
}
