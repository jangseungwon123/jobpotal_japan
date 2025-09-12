package com.tenco.jobpotal.company;

import com.tenco.jobpotal.user.LoginUser;
import jakarta.persistence.Lob;
import lombok.Builder;
import lombok.Data;

public class CompInfoResponse {
    // 기업 목록 응답 DTO
    @Data
    public static class MainDTO {
        private Long compId;
        private String companyName;
        private String companyDesc;
        private String companyCeoName;
        private String homepageUrl;
        private String phoneNumber;
        private String companyEmail;
        private String companyAddress;
        private String companyImageData;

        @Builder
        public MainDTO(CompInfo compInfo) {
            this.compId = compInfo.getCompId();
            this.companyName = compInfo.getCompanyName();
            this.companyDesc = compInfo.getCompanyDesc();
            this.companyCeoName = compInfo.getCompanyCeoName();
            this.homepageUrl = compInfo.getHomepageUrl();
            this.phoneNumber = compInfo.getPhoneNumber();
            this.companyEmail = compInfo.getCompanyEmail();
            this.companyAddress = compInfo.getCompanyAddress();
            this.companyImageData = compInfo.getCompanyImageData();
        }
    } // END OF INNER CLASS

    // 기업정보 상세보기 응답 DTO 설계
    @Data
    public static class DetailDTO {
        private Long compId;
        private String companyName;
        private String companyDesc;
        private String companyCeoName;
        private String homepageUrl;
        private String phoneNumber;
        private String companyEmail;
        private String companyAddress;
        private String companyImageId;
        private String companyImageData;

        //화면단에서 해당 유저의 게시물인지 확인 여부를 체크 해주기 위한 컬럼
        private boolean isCompInfoOwner;

        @Builder
        public DetailDTO(CompInfo compInfo, LoginUser loginUser) {
            this.compId = compInfo.getCompId();
            this.companyName = compInfo.getCompanyName();
            this.companyDesc = compInfo.getCompanyDesc();
            this.companyCeoName = compInfo.getCompanyCeoName();
            this.homepageUrl = compInfo.getHomepageUrl();
            this.phoneNumber = compInfo.getPhoneNumber();
            this.companyEmail = compInfo.getCompanyEmail();
            this.companyAddress = compInfo.getCompanyAddress();
            this.companyImageData = compInfo.getCompanyImageData();
            this.isCompInfoOwner = loginUser != null && compInfo.isOwner(loginUser.getId());
        }

        public CompInfo toEntity() {
            return CompInfo.builder()
                    .compId(this.compId)
                    .companyName(this.companyName)
                    .companyDesc(this.companyDesc)
                    .homepageUrl(this.homepageUrl)
                    .phoneNumber(this.phoneNumber)
                    .companyEmail(this.companyEmail)
                    .companyAddress(this.companyEmail)
                    .companyImageData(this.companyImageData)
                    .build();
        }
    } // END OF INNER CLASS
}
