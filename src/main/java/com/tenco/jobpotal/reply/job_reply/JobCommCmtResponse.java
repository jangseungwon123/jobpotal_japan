package com.tenco.jobpotal.reply.job_reply;

import com.tenco.jobpotal.user.LoginUser;
import lombok.Builder;
import lombok.Data;

public class JobCommCmtResponse {

    @Data
    public static class JobCommCmtListDTO {
        private Long jobCommCmtId;
        private String content;
        private String author;
        private String createdAt;

        @Builder
        public JobCommCmtListDTO(JobCommCmt jobCommCmt) {
            this.jobCommCmtId = jobCommCmt.getJobCommCmtId();
            this.content = jobCommCmt.getContent();
            this.author = jobCommCmt.getWriterName();
            this.createdAt = jobCommCmt.getCreatedAt().toString();
        }
    }


    @Data
    public static class SaveDTO {
        private Long jobCommCmtId;
        private Long postId;
        private String content;
        private String author;
        private String createdAt;

        @Builder
        public SaveDTO(JobCommCmt jobCommCmt) {
            this.jobCommCmtId = jobCommCmt.getJobCommCmtId();
            this.postId = jobCommCmt.getUserCommunity().getPostId();
            this.content = jobCommCmt.getContent();
            this.author = jobCommCmt.getUser().getUserNickname();
            this.createdAt = jobCommCmt.getCreatedAt().toString();
        }
    }

    @Data
    public static class DetailDTO {
        private Long jobCommCmtId;
        private Long postId;
        private String content;
        private String author;
        private String createdAt;

        public DetailDTO(JobCommCmt jobCommCmt, LoginUser loginUser) {
            this.jobCommCmtId = jobCommCmt.getJobCommCmtId();
            this.postId = jobCommCmt.getUserCommunity().getPostId();
            this.content = jobCommCmt.getContent();
            this.author = jobCommCmt.getUser().getUserNickname();
            this.createdAt = jobCommCmt.getCreatedAt().toString();
        }
    }

    @Data
    public static class UpdateDTO {
        private Long jobCommCmtId;
        private Long postId;
        private String content;
        private String author;
        private String createdAt;

        @Builder
        public UpdateDTO(JobCommCmt jobCommCmt) {
            this.jobCommCmtId = jobCommCmt.getJobCommCmtId();
            this.postId = jobCommCmt.getUserCommunity().getPostId();
            this.content = jobCommCmt.getContent();
            this.author = jobCommCmt.getUser().getUserNickname();
            this.createdAt = jobCommCmt.getCreatedAt().toString();
        }
    }
}
