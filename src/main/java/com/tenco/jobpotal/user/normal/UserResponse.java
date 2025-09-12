package com.tenco.jobpotal.user.normal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tenco.jobpotal.company.CompInfo;
import com.tenco.jobpotal.job_post.JobPost;
import com.tenco.jobpotal.skill.SkillList;
import jakarta.persistence.Lob;
import lombok.*;

import java.time.LocalDateTime;

@Builder
public class UserResponse {

    // 회원가입 후 응답 DTO
    @Data
    public static class JoinDTO {
        private String userName;
        private String userLoginId;
        private String userEmail;
        private String userAddress;
        private String userPhone;
        private String userBirth;
        private String userGender;
        private String userNickname;
        private String userImageData;

        @Builder
        public JoinDTO(User user) {
            this.userName = user.getUserName();
            this.userLoginId = user.getUserLoginId();
            this.userEmail = user.getUserEmail();
            this.userAddress = user.getUserAddress();
            this.userPhone = user.getUserPhone();
            this.userBirth = user.getUserBirth();
            this.userGender = user.getUserGender();
            this.userNickname = user.getUserNickname();
            this.userImageData = user.getUserImageData();
        }
    }

    // 로그인 후 응답 DTO
    @Data
    public static class LoginDTO {
        private String userLoginId;

        @Builder
        public LoginDTO(User user) {
            this.userLoginId = user.getUserLoginId();
        }
    }

    // 회원 정보 수정 후 응답 DTO
    @Data
    @NoArgsConstructor
    public static class  UpdateDTO {
        private String userName;
        private String userPassword;
        private String userEmail;
        private String userAddress;
        private String userPhone;
        private String userNickname;
        private String userImageData;

        @Builder

        public UpdateDTO(User user) {
            this.userName = user.getUserName();
            this.userPassword = user.getUserPassword();
            this.userEmail = user.getUserEmail();
            this.userAddress = user.getUserAddress();
            this.userPhone = user.getUserPhone();
            this.userNickname = user.getUserNickname();
            this.userImageData = user.getUserImageData();
        }
    }

    // 회원정보 조회 응답 DTO
    @Data
    public static class DetailDTO {
        private String userName;
        private String userLoginId;
        private String userEmail;
        private String userAddress;
        private String userPhone;
        private String userBirth;
        private String userGender;
        private String userNickname;
        private String userImageData;

        @Builder
        public DetailDTO(User user) {
            this.userName = user.getUserName();
            this.userLoginId = user.getUserLoginId();
            this.userEmail = user.getUserEmail();
            this.userAddress = user.getUserAddress();
            this.userPhone = user.getUserPhone();
            this.userBirth = user.getUserBirth();
            this.userGender = user.getUserGender();
            this.userNickname = user.getUserNickname();
            this.userImageData = user.getUserImageData();
        }
    }

    // 회원과 관심기업 매칭 공고 조회 응답 DTO
    @Data
    public static class JobPostMatchListDTO {
        private Long recruitId;
        private Long compId;
        private SkillList skillList;
        private String title;
        private String content;
        private String requireCareerYears;
        private String employmentType;
        private String instId;
        private LocalDateTime instDate;
        private LocalDateTime postedAt;
        private LocalDateTime deadline;

        @Builder
        public JobPostMatchListDTO(JobPost jobPost) {
            this.recruitId = jobPost.getRecruitId();
            this.compId = jobPost.getCompInfo().getCompId();
            this.skillList = jobPost.getSkillList();
            this.title = jobPost.getTitle();
            this.content = jobPost.getContent();
            this.requireCareerYears = jobPost.getRequireCareerYears();
            this.employmentType = jobPost.getEmploymentType();
            this.instId = jobPost.getInstId();
            this.instDate = jobPost.getInstDate();
            this.postedAt = jobPost.getPostedAt();
            this.deadline = jobPost.getDeadline();
        }
    }

    // 응답 DTO (읽기 전용)
    @Getter
    @AllArgsConstructor
    @Builder
    public static class MyProfileDTO {
        private String userName;
        private String userLoginId;
        private String userEmail;
        private String userAddress;
        private String userPhone;
        private String userBirth;
        private String userNickname;
        private String userGender;
        private Boolean companyUser;

        // 엔티티 -> DTO 변환
        public static MyProfileDTO fromEntity(User user) {
            return MyProfileDTO.builder()
                    .userName(user.getUserName())
                    .userLoginId(user.getUserLoginId())
                    .userEmail(user.getUserEmail())
                    .userAddress(user.getUserAddress())
                    .userPhone(user.getUserPhone())
                    .userBirth(user.getUserBirth())
                    .userNickname(user.getUserNickname())
                    .userGender(user.getUserGender())
                    .companyUser(Boolean.TRUE.equals(user.getIsCompanyUserYn()))
                    .build();
        }
    }
}
