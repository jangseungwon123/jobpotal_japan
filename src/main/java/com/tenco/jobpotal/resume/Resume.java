package com.tenco.jobpotal.resume;

import com.tenco.jobpotal.user.normal.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Table(name = "resume")
@Entity
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resumeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_skill_list_no")
    private UserSkillList userSkillList;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false, length = 100)
    private String title;
    @Column(nullable = false, length = 1000)
    private String content;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String birth;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false, columnDefinition = "CHAR(1)")
    private String gender;

    @Column(nullable = false, length = 1)
    private char isExperienced;
    @Column(nullable = false, length = 1)
    private char isShow;

    @CreationTimestamp
    private Timestamp instDate;


    @Builder
    public Resume(Long resumeId, String name, User user, UserSkillList userSkillList, String title, String content, String phone, String address, String birth, String email, String gender, char isExperienced, char isShow, Timestamp instDate) {
        this.resumeId = resumeId;
        this.name = name;
        this.user = user;
        this.userSkillList = userSkillList;
        this.title = title;
        this.content = content;
        this.phone = phone;
        this.address = address;
        this.birth = birth;
        this.email = email;
        this.gender = gender;
        this.isExperienced = isExperienced;
        this.isShow = isShow;
        this.instDate = instDate;
    }

    public void update(ResumeRequest.UpdateDTO updateDTO) {
        this.name = updateDTO.getName();
        this.title = updateDTO.getTitle();
        this.content = updateDTO.getContent();
        this.phone = updateDTO.getPhone();
        this.address = updateDTO.getAddress();
        this.birth = updateDTO.getBirth();
        this.email = updateDTO.getEmail();
        this.gender = updateDTO.getGender();
        this.isExperienced = updateDTO.getIsExperienced();
        this.isShow = updateDTO.getIsShow();
    }

}
