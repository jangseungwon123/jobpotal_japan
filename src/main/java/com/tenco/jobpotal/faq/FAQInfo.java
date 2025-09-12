package com.tenco.jobpotal.faq;

import com.tenco.jobpotal.user.adminInfo.AdminInfo;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@Table(name = "faq_info")
public class FAQInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long faqId;

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private AdminInfo adminInfo; // 작성자 (AdminInfo 엔티티 참조)

    @CreationTimestamp
    private Timestamp createdAt; // 등록일

    @Builder
    public FAQInfo(Long faqId, String title, String content, AdminInfo adminInfo, Timestamp createdAt) {
        this.faqId = faqId;
        this.title = title;
        this.content = content;
        this.adminInfo = adminInfo;
        this.createdAt = createdAt;
    }
}
