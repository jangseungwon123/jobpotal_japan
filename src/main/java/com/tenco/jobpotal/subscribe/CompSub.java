package com.tenco.jobpotal.subscribe;

import com.tenco.jobpotal._core.utils.MyDateUtil;
import com.tenco.jobpotal.company.CompInfo;
import com.tenco.jobpotal.user.normal.User;
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
@Table(name = "comp_sub")
@Entity
public class CompSub {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long compSubId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comp_id")
    private CompInfo compInfo;

    @CreationTimestamp
    private Timestamp compSubDate;

    public boolean isOwner(Long checkUserId){return this.compInfo.getCompUser().getCompUserId().equals(checkUserId);}

    public String getTime() {return MyDateUtil.timestampFormat(compSubDate);}
}
