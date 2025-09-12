package com.tenco.jobpotal.subscribe;

import com.tenco.jobpotal.company.CompInfo;
import lombok.Data;

public class UserSubResponse {

    @Data
    public static class SubListDTO {
        private Long userSubId;
        private Long compId;
        private String companyName;
        private String companyDesc;
        private String companyCeoName;
        private String homepageUrl;
        private String phoneNumber;
        private String companyEmail;
        private String companyAddress;
        private String companyImageData;
        private String userSubDate;

        public SubListDTO(UserSub userSub) {
            CompInfo compInfo = userSub.getCompInfo();
            this.userSubId = userSub.getUserSubId();
            this.compId = compInfo.getCompId();
            this.companyName = compInfo.getCompanyName();
            this.companyDesc = compInfo.getCompanyDesc();
            this.companyCeoName = compInfo.getCompanyCeoName();
            this.homepageUrl = compInfo.getHomepageUrl();
            this.phoneNumber = compInfo.getPhoneNumber();
            this.companyEmail = compInfo.getCompanyEmail();
            this.companyAddress = compInfo.getCompanyAddress();
            this.companyImageData = compInfo.getCompanyImageData();
            this.userSubDate = userSub.getTime();
        }
    }

    @Data
    public static class SaveDTO {
        private Long userSubId;
        private Long userId;
        private Long compInfoId;

        public SaveDTO(UserSub userSub) {
            this.userSubId = userSub.getUserSubId();
            this.userId = userSub.getUser().getUserId();
            this.compInfoId = userSub.getCompInfo().getCompId();
        }
    }
}
