package com.tenco.jobpotal.resume;

import com.tenco.jobpotal.user.LoginUser;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

public class ResumeResponse {

    @Data
    public static class SaveDTO{
        private Long resumeId;
        private String name;
        private String title;
        private String content;
        private String phone;
        private String address;
        private String birth;
        private String email;
        private String gender;
        private Long skillId;
        private char isExperienced;
        private char isShow;

        public SaveDTO(Resume resume) {
            this.resumeId = resume.getResumeId();
            this.name = resume.getName();
            this.title = resume.getTitle();
            this.content = resume.getContent();
            this.phone = resume.getPhone();
            this.address = resume.getAddress();
            this.birth = resume.getBirth();
            this.email = resume.getEmail();
            this.gender = resume.getGender();
            this.skillId = resume.getUserSkillList().getSkillList().getSkillId();
            this.isExperienced = resume.getIsExperienced();
            this.isShow = resume.getIsShow();
        }
    }

    @Data
    public static class UpdateDTO{
        private Long resumeId;
        private String name;
        private String title;
        private String content;
        private String phone;
        private String address;
        private String birth;
        private String email;
        private String gender;
        private Long skillId;
        private char isExperienced;
        private char isShow;

        public UpdateDTO(Resume resume) {
            this.resumeId = resume.getResumeId();
            this.name = resume.getName();
            this.title = resume.getTitle();
            this.content = resume.getContent();
            this.phone = resume.getPhone();
            this.address = resume.getAddress();
            this.birth = resume.getBirth();
            this.email = resume.getEmail();
            this.gender = resume.getGender();
            this.skillId = resume.getUserSkillList().getSkillList().getSkillId();
            this.isExperienced = resume.getIsExperienced();
            this.isShow = resume.getIsShow();
        }
    }

    @Data
    public static class DetailDTO {
        private Long resumeId;
        private String name;
        private String title;
        private String content;
        private String phone;
        private String address;
        private String birth;
        private String email;
        private String gender;
        private Long skillId;
        private char isExperienced;


        public DetailDTO(Resume resume, LoginUser loginUser) {
            this.resumeId = resume.getResumeId();
            this.name = resume.getName();
            this.title = resume.getTitle();
            this.content = resume.getContent();
            this.phone = resume.getPhone();
            this.address = resume.getAddress();
            this.birth = resume.getBirth();
            this.email = resume.getEmail();
            this.gender = resume.getGender();
            this.skillId = resume.getUserSkillList().getSkillList().getSkillId();
            this.isExperienced = resume.getIsExperienced();
        }
    }

    @Data
    public static class ResumeListResponseDTO {
        private Long resumeId;
        private String name;
        private String title;
        private String content;
        private Timestamp instDate;

        public ResumeListResponseDTO(Resume resume) {
            this.resumeId = resume.getResumeId();
            this.name = resume.getName();
            this.title = resume.getTitle();
            this.content = resume.getContent();
            this.instDate = resume.getInstDate();
        }
    }


}
