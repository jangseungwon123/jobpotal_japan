package com.tenco.jobpotal.community.userCommunity;

import com.tenco.jobpotal.user.LoginUser;
import lombok.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class UserCommunityResponse {


    //전체 게시글 조회 응답 DTO 설계
    @Data
    @Builder
    public static class ListDTO {
        private Long postId;
        private String title;
        private String content;
        private String instId;
        private Timestamp instDate;
        private String writerNickname; // 작성자 닉네임
        private boolean isPostOwner; // 게시글 작성자 여부

        public static ListDTO fromEntity(UserCommunity userCommunity, LoginUser loginUser) {
            return ListDTO.builder()
                    .postId(userCommunity.getPostId())
                    .title(userCommunity.getTitle())
                    .content(userCommunity.getContent())
                    .instId(userCommunity.getInstId())
                    .instDate(userCommunity.getInstDate())
                    .writerNickname(userCommunity.getUser() != null ? userCommunity.getUser().getUserNickname() : "탈퇴한 사용자")
                    .isPostOwner(loginUser != null && userCommunity.getUser() != null && 
                               userCommunity.getUser().getUserId().equals(loginUser.getId()))
                    .build();
        }
    }



    // 사용자 커뮤니티 상세보기 응답 DTO 설계

    @Data
    public static class DetailDTO {
        private Long postId;
        private String title;
        private String content;
        private String instId;
        private String instDate;
        private boolean isPostOwner; // 화면단에서 해당 유저의 게시물인지 확인 여부를 체크 해주기 위한 컬럼

        @Builder
        public DetailDTO(UserCommunity userCommunity, LoginUser loginUser) {
            this.postId = userCommunity.getPostId();
            this.title = userCommunity.getTitle();
            this.content = userCommunity.getContent();
            this.instId = userCommunity.getInstId();
            this.instDate = userCommunity.getInstDate().toString(); //
            this.isPostOwner = loginUser != null && userCommunity.isOwner(loginUser.getId());
        }

        public UserCommunity toEntity() {
            return UserCommunity.builder()
                    .postId(this.postId)
                    .title(this.title)
                    .content(this.content)
                    .instId(this.instId)
                    .build();
        }
    }



    // 마이 페이지용 값 전달 DTO
    @AllArgsConstructor
    @Getter
    public static class MyPostResponse {
        private Long postId;
        private String title;
        private Timestamp instDate;

        public static MyPostResponse fromEntity(UserCommunity userCommunity) {
            return new MyPostResponse(
                    userCommunity.getPostId(),
                    userCommunity.getTitle(),
                    userCommunity.getInstDate()
            );
        }
    }

}
