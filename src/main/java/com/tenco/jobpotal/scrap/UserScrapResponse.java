package com.tenco.jobpotal.scrap;

import com.tenco.jobpotal.job_post.JobPost;
import lombok.Data;

import java.time.LocalDateTime;

public class UserScrapResponse {

    @Data
    public static class ScrapListDTO {
        private Long userScrapId;
        private Long compInfoId;
        private String title;
        private String content;
        private String requireCareerYears;
        private String employmentType;
        private LocalDateTime postedAt;
        private LocalDateTime deadline;
        private String instId;
        private LocalDateTime instDate;
        private String userScrapDate;

        public ScrapListDTO(UserScrap userScrap) {
            JobPost jobPost = userScrap.getJobPost();
            this.userScrapId = userScrap.getUserScrapId();
            this.compInfoId = jobPost.getCompInfo().getCompId();
            this.title = jobPost.getTitle();
            this.content = jobPost.getContent();
            this.requireCareerYears = jobPost.getRequireCareerYears();
            this.employmentType = jobPost.getEmploymentType();
            this.postedAt = jobPost.getPostedAt();
            this.deadline = jobPost.getDeadline();
            this.instId = jobPost.getInstId();
            this.instDate = jobPost.getInstDate();
            this.userScrapDate = userScrap.getTime();
        }
    }

    @Data
    public static class SaveDTO {
        private Long userScrabId;
        private Long userId;
        private Long compInfoId;

        public SaveDTO(UserScrap userScrap) {
            this.userScrabId = userScrap.getUserScrapId();
            this.userId = userScrap.getUser().getUserId();
            this.compInfoId = userScrap.getJobPost().getCompInfo().getCompId();
        }
    }
}
