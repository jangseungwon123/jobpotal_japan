package com.tenco.jobpotal.scrap;

import com.tenco.jobpotal.resume.Resume;
import lombok.Data;

import java.sql.Timestamp;

public class CompScrapResponse {

    @Data
    public static class ScrapListDTO {
        private Long compScrapId;
        private Long compId;
        private Long resumeId;
        private String name;
        private String title;
        private String content;
        private String phone;
        private String address;
        private String birth;
        private String email;
        private String gender;
        private char isExperienced;
        private char isShow;
        private Timestamp instDate;
        private String compScrapDate;

        public ScrapListDTO(CompScrap compScrap) {
            Resume resume = compScrap.getResume();
            this.compScrapId = compScrap.getCompScrapId();
            this.compId = compScrap.getCompInfo().getCompId();
            this.resumeId = resume.getResumeId();
            this.name = resume.getName();
            this.title = resume.getTitle();
            this.content = resume.getContent();
            this.phone = resume.getPhone();
            this.address = resume.getAddress();
            this.birth = resume.getBirth();
            this.email = resume.getEmail();
            this.gender = resume.getGender();
            this.isExperienced = resume.getIsExperienced();
            this.isShow = resume.getIsShow();
            this.instDate = resume.getInstDate();
            this.compScrapDate = compScrap.getTime();
        }
    }

    @Data
    public static class SaveDTO {
        private Long compScrapId;
        private Long compId;
        private Long compUserId;
        private Long resumeId;

        public SaveDTO(CompScrap compScrap) {
            this.compScrapId = compScrap.getCompScrapId();
            this.compId = compScrap.getCompInfo().getCompId();
            this.compUserId = compScrap.getCompInfo().getCompUser().getCompUserId();
            this.resumeId = compScrap.getResume().getResumeId();
        }
    }
}
