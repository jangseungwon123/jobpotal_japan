package com.tenco.jobpotal.company;


import com.tenco.jobpotal._core.utils.MyDateUtil;
import com.tenco.jobpotal.user.comp.CompUser;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;


@Entity
@Data
@NoArgsConstructor
@Table(name = "comp_info")
public class CompInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long compId;

    @OneToOne
    @JoinColumn(name = "comp_user_id", nullable = false)
    private CompUser compUser;

    @Column(nullable = false)
    private String companyName;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String companyDesc;
    @Column(nullable = false)
    private String companyCeoName;
    private String homepageUrl;
    private String phoneNumber;
    private String companyEmail;
    private String companyAddress;
    // imgInfo 테이블의 key값을 가져오기 위함
    @Lob
    private String companyImageData;

   // @OneToMany(fetch = FetchType.LAZY, mappedBy = "companyInfo", cascade = CascadeType.REMOVE)
    // List<CompanyReview> reviews = new ArrayList<>(); // List 선언과 동시에 초기화

    private String instId;
    @CreationTimestamp
    private Timestamp instDate;

    @Transient
    private boolean isBoardOwner;

    // 게시글에 소유자를 직접 확인하는 기능을 만들자
    public boolean isOwner(Long checkUserId) {
        return this.compUser.getCompUserId().equals(checkUserId);
    }

    @Builder
    public CompInfo(Long compId, CompUser compUser, String companyName, String companyDesc, String companyCeoName, String homepageUrl, String phoneNumber, String companyEmail, String companyAddress, String companyImageData, String instId, Timestamp instDate, boolean isBoardOwner) {
        this.compId = compId;
        this.compUser = compUser;
        this.companyName = companyName;
        this.companyDesc = companyDesc;
        this.companyCeoName = companyCeoName;
        this.homepageUrl = homepageUrl;
        this.phoneNumber = phoneNumber;
        this.companyEmail = companyEmail;
        this.companyAddress = companyAddress;
        this.companyImageData = companyImageData;
        this.instId = instId;
        this.instDate = instDate;
        this.isBoardOwner = isBoardOwner;
    }

    public void update(CompInfoRequest.UpdateDTO updateDTO) {
        this.companyName = updateDTO.getCompanyName();
        this.companyDesc = updateDTO.getCompanyDesc();
        this.companyCeoName = updateDTO.getCompanyCeoName();
        this.homepageUrl = updateDTO.getHomepageUrl();
        this.phoneNumber = updateDTO.getPhoneNumber();
        this.companyEmail = updateDTO.getCompanyEmail();
        this.companyAddress = updateDTO.getCompanyAddress();
        this.companyImageData = updateDTO.getCompanyImageData();
    }


    public String getTime() {
        return MyDateUtil.timestampFormat(instDate);
    }
}
