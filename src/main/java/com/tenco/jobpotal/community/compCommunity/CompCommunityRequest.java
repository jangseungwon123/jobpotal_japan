package com.tenco.jobpotal.community.compCommunity;

import com.tenco.jobpotal.user.comp.CompUser;
import lombok.Data;

public class CompCommunityRequest {

    @Data
    public static class SaveDTO {
        private String title;
        private String content;
        private String instId;
        private String postPassword;


        public CompCommunity toEntity(CompUser compUser) {
            return CompCommunity.builder()
                    .compUser(compUser)
                    .title(title)
                    .content(content)
                    .instId(compUser.getCompUserLoginId())
                    .postPassword(postPassword)
                    .build();
        }
    }

    @Data
    public static class UpdateDTO {
        private String title;
        private String content;
        private String instId;
        private String postPassword;

        public CompCommunity toEntity() {
            return CompCommunity.builder()
                    .title(title)
                    .content(content)
                    .instId(instId)
                    .postPassword(postPassword)
                    .build();
        }
    }
}
