package com.tenco.jobpotal.user.applInfo;

import com.tenco.jobpotal.job_post.JobPost;
import com.tenco.jobpotal.resume.Resume;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ApplInfoRequest {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SaveDTO {

        @NotNull(message = "이력서 ID는 필수입니다.")
        @Positive(message = "공고 ID는 양수여야 합니다.")
        private Long resumeId;
        @NotNull(message = "채용공고 ID는 필수입니다.")
        @Positive(message = "공고 ID는 양수여야 합니다.")
        private Long jopPostId;

        public ApplInfo toEntity() {
            return ApplInfo.builder()
                    .resume(Resume.builder().resumeId(resumeId).build())
                    .jobPost(JobPost.builder().recruitId(jopPostId).build())
                    .build();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateStatusDTO {
        private Long applInfoId;
        @NotNull(message = "이력서 ID는 필수입니다.")
        @Positive(message = "공고 ID는 양수여야 합니다.")
        private Long resumeId;
        @NotNull(message = "채용공고 ID는 필수입니다.")
        @Positive(message = "공고 ID는 양수여야 합니다.")
        private Long jobPostId;
        private String status;

        public ApplInfo toEntity(String status, ApplInfo applInfo) {
            return ApplInfo.builder()
                    .applInfoId(applInfo.getApplInfoId())
                    .resume(Resume.builder().resumeId(resumeId).build())
                    .jobPost(JobPost.builder().recruitId(jobPostId).build())
                    .status(status)
                    .build();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteDTO {
        private Long applInfoId;
        private Long resumeId;
        private Long jobPostId;


        public ApplInfo toEntity(ApplInfo applInfo) {
            return ApplInfo.builder()
                    .applInfoId(applInfo.getApplInfoId())
                    .resume(Resume.builder().resumeId(resumeId).build())
                    .jobPost(JobPost.builder().recruitId(resumeId).build())
                    .build();
        }
    }
}
