package com.tenco.jobpotal.reply.comp_reply;

import com.tenco.jobpotal.user.LoginUser;
import lombok.Builder;
import lombok.Data;

public class CompCommCmtResponse {

    @Data
    public static class CompCommCmtListDTO {
        private Long compCommCmtId;
        private String content;
        private String author;
        private String createdAt;

        @Builder
        public CompCommCmtListDTO(CompCommCmt compCommCmt) {
            this.compCommCmtId = compCommCmt.getCompCommCmtId();
            this.content = compCommCmt.getContent();
            this.author = compCommCmt.getWriterName();
            this.createdAt = compCommCmt.getCreatedAt().toString();
        }
    }

    @Data
    public static class SaveDTO {
        private Long compCommCmtId;
        private Long postId;
        private String content;
        private String author;
        private String createdAt;

        @Builder
        public SaveDTO(CompCommCmt compCommCmt) {
            this.compCommCmtId = compCommCmt.getCompCommCmtId();
            this.postId = compCommCmt.getCompCommunity().getPostId();
            this.content = compCommCmt.getContent();
            this.author = compCommCmt.getCompUser().getCompUserNickname();
            this.createdAt = compCommCmt.getCreatedAt().toString();
        }


    }

    @Data
    public static class DetailDTO {
        private Long compCommCmtId;
        private Long postId;
        private String content;
        private String author;
        private String createdAt;

        @Builder
        public DetailDTO(CompCommCmt compCommCmt, LoginUser loginUser) {
            this.compCommCmtId = compCommCmt.getCompCommCmtId();
            this.postId = compCommCmt.getCompCommunity().getPostId();
            this.content = compCommCmt.getContent();
            this.author = compCommCmt.getCompUser().getCompUserNickname();
            this.createdAt = compCommCmt.getCreatedAt().toString();
        }
    }

    @Data
    public static class UpdateDTO {
        private Long compCommCmtId;
        private Long postId;
        private String content;
        private String author;
        private String createdAt;

        @Builder
        public UpdateDTO(CompCommCmt compCommCmt) {
            this.compCommCmtId = compCommCmt.getCompCommCmtId();
            this.postId = compCommCmt.getCompCommunity().getPostId();
            this.content = compCommCmt.getContent();
            this.author = compCommCmt.getCompUser().getCompUserNickname();
            this.createdAt = compCommCmt.getCreatedAt().toString();
        }
    }
}

