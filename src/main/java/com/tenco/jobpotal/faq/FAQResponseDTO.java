package com.tenco.jobpotal.faq;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class FAQResponseDTO {
    private Long faqId;
    private String title;
    private String content;
    private String adminName; // 작성자 이름

    @Builder
    public FAQResponseDTO(Long faqId, String title, String content, String adminName) {
        this.faqId = faqId;
        this.title = title;
        this.content = content;
        this.adminName = adminName;
    }

    public FAQResponseDTO toDTO(FAQInfo faqInfo) {
        return  FAQResponseDTO.builder()
                .faqId(faqInfo.getFaqId())
                .title(faqInfo.getTitle())
                .content(faqInfo.getContent())
                .adminName(faqInfo.getAdminInfo().getAdminName())
                .build();
    }
}
