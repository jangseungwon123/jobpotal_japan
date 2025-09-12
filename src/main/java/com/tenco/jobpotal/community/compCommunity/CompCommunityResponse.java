package com.tenco.jobpotal.community.compCommunity;

import com.tenco.jobpotal.user.LoginUser;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

public class CompCommunityResponse {

    // 게시글 작성 응답 DTO 설계
    @Data
    @Builder
    public static class SaveDTO {
        private Long postId;
        private String title;
        private String content;
        private String instId;
        private Timestamp instDate; // 게시글 작성 시간
        private String postPassword; // 게시글 비밀번호

        public static SaveDTO fromEntity(CompCommunity compCommunity) {
            return SaveDTO.builder()
                    .postId(compCommunity.getPostId())
                    .title(compCommunity.getTitle())
                    .content(compCommunity.getContent())
                    .instId(compCommunity.getInstId())
                    .instDate(compCommunity.getInstDate())
                    .postPassword(compCommunity.getPostPassword())
                    .build();
        }
    }
    //전체 게시글 조회 응답 DTO 설계
    @Data
    @Builder
    public static class ListDTO {
        private Long postId;
        private String title;
        private String content;
        private String instId;
        private String writerCompanyName; // 작성자 회사명
        private boolean isPostOwner; // 게시글 작성자 여부

        public static ListDTO fromEntity(CompCommunity compCommunity, LoginUser loginUser) {
            return ListDTO.builder()
                    .postId(compCommunity.getPostId())
                    .title(compCommunity.getTitle())
                    .content(compCommunity.getContent())
                    .instId(compCommunity.getInstId())
                    .writerCompanyName(compCommunity.getCompUser() != null ? compCommunity.getCompUser().getCompUserName() : "탈퇴한 회사")
                    .isPostOwner(loginUser != null && compCommunity.getCompUser() != null &&
                               compCommunity.getCompUser().getCompUserId().equals(loginUser.getId()))
                    .build();
        }
    }

    // 기업 커뮤니티 상세보기 응답 DTO 설계
    @Data
    public static class DetailDTO {
        private Long postId;
        private String title;
        private String content;
        private String instId;
        private boolean isPostOwner; // 화면단에서 해당 기업의 게시물인지 확인 여부를 체크 해주기 위한 컬럼

        @Builder
        public DetailDTO(CompCommunity compCommunity, LoginUser loginUser) {
            this.postId = compCommunity.getPostId();
            this.title = compCommunity.getTitle();
            this.content = compCommunity.getContent();
            this.instId = compCommunity.getInstId();
            this.isPostOwner = loginUser != null && compCommunity.isOwner(loginUser.getId());
        }

        public CompCommunity toEntity() {
            return CompCommunity.builder()
                    .postId(this.postId)
                    .title(this.title)
                    .content(this.content)
                    .instId(this.instId)
                    .build();
        }
    }
}
