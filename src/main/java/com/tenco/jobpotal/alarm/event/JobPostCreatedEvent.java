package com.tenco.jobpotal.alarm.event;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JobPostCreatedEvent {
    private Long recruitId;   // 채용공고 ID
    private Long compId;      //
    private String companyName;// 회사
    private String jobPostTitle; // 채용공고 제목 // ex [SK 하이닉스] 에서 채용공고가 올라왔어요 확인하세요.
}
