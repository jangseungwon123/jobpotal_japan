package com.tenco.jobpotal.job_post;


import com.tenco.jobpotal.company.CompInfo;
import com.tenco.jobpotal.skill.SkillList;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "job_post")
@Getter
@Setter
@NoArgsConstructor
public class JobPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recruitId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comp_id")
    private CompInfo compInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id", referencedColumnName = "skillId")
    private SkillList skillList;

    private String title;

    private String content;

    private String requireCareerYears;

    private String employmentType;

    private String instId;

    @CreationTimestamp
    private LocalDateTime instDate;

    private LocalDateTime postedAt;

    private LocalDateTime deadline;

    @Builder

    public JobPost(Long recruitId, CompInfo compInfo, SkillList skillList, String title, String content, String requireCareerYears, String employmentType, String instId, LocalDateTime instDate, LocalDateTime postedAt, LocalDateTime deadline) {
        this.recruitId = recruitId;
        this.compInfo = compInfo;
        this.skillList = skillList;
        this.title = title;
        this.content = content;
        this.requireCareerYears = requireCareerYears;
        this.employmentType = employmentType;
        this.instId = instId;
        this.instDate = instDate;
        this.postedAt = postedAt;
        this.deadline = deadline;
    }
}


