package com.tenco.jobpotal.company.compRating;

import com.tenco.jobpotal._core.errors.exception.Exception400;
import com.tenco.jobpotal.user.applInfo.ApplInfo;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

public class CompRatingRequest {

    @Data
    public static class SaveDTO {

        @NotNull(message = "지원 정보 ID는 필수입니다.")
        @Positive(message = "지원 정보 ID는 양수여야 합니다.")
        private Long applInfoId;
        @Min(1)
        @Max(5)
        @NotNull(message = "평점은 필수입니다.")
        private int score;

        public CompRating toEntity() {
            return CompRating.builder()
                    .applInfo(ApplInfo.builder().applInfoId(applInfoId).build())
                    .score(score)
                    .build();
        }
    }

    @Data
    public static class UpdateDTO {
        @NotNull(message = "지원 정보 ID는 필수입니다.")
        @Positive(message = "지원 정보 ID는 양수여야 합니다.")
        private Long ratingId;
        @Min(1)
        @Max(5)
        @NotNull(message = "평점은 필수입니다.")
        private int score;

        public CompRating toEntity() {
            return CompRating.builder()
                    .ratingId(ratingId)
                    .score(score)
                    .build();
        }
    }
}
