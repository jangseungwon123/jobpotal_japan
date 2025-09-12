package com.tenco.jobpotal.company.compRating;

import com.tenco.jobpotal.user.applInfo.ApplInfo;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

public class CompRatingResponse {

    public static class SaveDTO{
        private Long ratingId;
        private Long applInfo;
        private int score;
        public SaveDTO(CompRating compRating) {
            this.ratingId = compRating.getRatingId();
            this.applInfo = compRating.getApplInfo().getApplInfoId();
            this.score = compRating.getScore();
        }
    }

    public static class UpdateDTO{
        private int score;
        public UpdateDTO(CompRating compRating) {
            this.score = compRating.getScore();
        }
    }

    @Data
    public static class AvgScoreDTO{
        private Long companyId;
        private Double averageScore;
        private String message;

        public AvgScoreDTO(Long companyId, Double averageScore, String message) {
            this.companyId = companyId;
            this.averageScore = (averageScore == null) ? 0.0 : Math.round(averageScore * 10.0)/ 10.0;
            this.message = message;

        }
    }




}
