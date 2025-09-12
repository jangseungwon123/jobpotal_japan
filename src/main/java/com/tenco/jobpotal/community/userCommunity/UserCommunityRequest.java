package com.tenco.jobpotal.community.userCommunity;

import com.tenco.jobpotal.user.normal.User;
import lombok.Data;

import java.sql.Timestamp;

public class UserCommunityRequest {


    @Data
    public static class SaveDTO {
        private String title;
        private String content;
        private String instId;
        private String postPassword;
        private Timestamp instDate;

        public UserCommunity toEntity(User user) {
            return UserCommunity.builder()
                    .user(user)
                    .title(title)
                    .content(content)
                    .instId(user.getUserLoginId())
                    .postPassword(postPassword)
                    .build();
        }
    }

    @Data
    public static class UserCommunityUpdateDTO {
        private String title;
        private String content;
        private String instId;
        private String postPassword;

        public UserCommunity toEntity() {
            return UserCommunity.builder()
                    .title(title)
                    .content(content)
                    .instId(instId)
                    .postPassword(postPassword)
                    .build();
        }
    }
}
