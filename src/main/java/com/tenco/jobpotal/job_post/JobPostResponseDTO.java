package com.tenco.jobpotal.job_post;

import com.tenco.jobpotal.skill.SkillList;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class JobPostResponseDTO {
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


    public JobPostResponseDTO(JobPost jobPost) {
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
