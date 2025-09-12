package com.tenco.jobpotal.skill;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tenco.jobpotal.job_post.JobPost;
import com.tenco.jobpotal.resume.UserSkillList;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@Data
@Table(name = "skill_list")
@Entity
public class SkillList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long skillListNo;

    @Column(nullable = false)
    private Long skillGrpId;

    @Column(unique = true, nullable = false)
    private Long skillId;

    @Column(nullable = false)
    private String skillName;

    private String skillDesc;
    private String instId;

    //@OneToMany(mappedBy = "skillList")
    //@JsonIgnore
    //private List<UserSkillList> userSkills;

    @CreationTimestamp
    private Timestamp instDate;

    @Builder
    public SkillList(Long skillListNo, Long skillGrpId, Long skillId, String skillName, String skillDesc, String instId, Timestamp instDate) {
        this.skillListNo = skillListNo;
        this.skillGrpId = skillGrpId;
        this.skillId = skillId;
        this.skillName = skillName;
        this.skillDesc = skillDesc;
        this.instId = instId;
        this.instDate = instDate;
    }

    public SkillList toEntity(JobPost jobPost) {
        return SkillList.builder()
                .skillListNo(jobPost.getSkillList().getSkillListNo())
                .skillGrpId(jobPost.getSkillList().getSkillGrpId())
                .skillId(jobPost.getSkillList().getSkillId())
                .skillName(jobPost.getSkillList().getSkillName())
                .skillDesc(jobPost.getSkillList().getSkillDesc())
                .instId(jobPost.getSkillList().getInstId())
                .instDate(jobPost.getSkillList().getInstDate())
                .build();
    }
}
