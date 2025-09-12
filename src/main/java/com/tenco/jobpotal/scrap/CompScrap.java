package com.tenco.jobpotal.scrap;

import com.tenco.jobpotal._core.utils.MyDateUtil;
import com.tenco.jobpotal.company.CompInfo;
import com.tenco.jobpotal.resume.Resume;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "comp_Scrap")
@Entity
public class CompScrap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long compScrapId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comp_id")
    private CompInfo compInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @CreationTimestamp
    private Timestamp compScrapDate;

    public boolean isOwner(Long checkUserId){
        return this.compInfo.getCompUser().getCompUserId().equals(checkUserId);
    }

    public String getTime() {
        return MyDateUtil.timestampFormat(compScrapDate);
    }

}
