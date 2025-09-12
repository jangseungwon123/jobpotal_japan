package com.tenco.jobpotal.resume;


import com.tenco.jobpotal.skill.SkillList;
import com.tenco.jobpotal.user.normal.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Table(name = "user_skill_list")
@Entity
public class UserSkillList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSkillListNo;

    @ManyToOne
    @JoinColumn(name = "resume_no")
    private Resume resume;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // 외래키 컬럼명 명시
    private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id") // 외래키 컬럼명 명시
    private SkillList skillList;

    private String instId;

    @CreationTimestamp
    private Timestamp instDate;

    @Builder
    public UserSkillList(Long userSkillListNo, Resume resume, User user, SkillList skillList, String instId, Timestamp instDate) {
        this.userSkillListNo = userSkillListNo;
        this.resume = resume;
        this.user = user;
        this.skillList = skillList;
        this.instId = instId;
        this.instDate = instDate;
    }
}
