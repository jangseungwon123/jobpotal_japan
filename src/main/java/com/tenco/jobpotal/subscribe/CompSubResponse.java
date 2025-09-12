package com.tenco.jobpotal.subscribe;

import com.tenco.jobpotal.company.CompInfo;
import com.tenco.jobpotal.user.comp.CompUser;
import com.tenco.jobpotal.user.normal.User;
import lombok.Data;

public class CompSubResponse {

    @Data
    public static class SubListDTO{
        private Long compSubId;
        private Long compId;
        private Long userId;
        private String userEmail;
        private String userBirth;
        private String userGender;

        private String compSubDate;

        public SubListDTO(CompSub compSub) {
            this.compSubId = compSub.getCompSubId();
            this.compId = compSub.getCompInfo().getCompId();
            this.userId = compSub.getUser().getUserId();
            this.userEmail = compSub.getUser().getUserEmail();
            this.userBirth = compSub.getUser().getUserBirth();
            this.userGender = compSub.getUser().getUserGender();
            this.compSubDate = compSub.getTime();
        }
    }


    // 구독 DTO
    @Data
    public static class SaveDTO {
        private Long compSubId;
        private Long userId;
        private Long compInfoId;
        private Long compUserId;

        public SaveDTO(CompSub compSub) {
          this.compSubId = compSub.getCompSubId();
          this.userId = compSub.getUser().getUserId();
          this.compInfoId = compSub.getCompInfo().getCompId();
          this.compUserId = compSub.getCompInfo().getCompUser().getCompUserId();
        }
    }


}
