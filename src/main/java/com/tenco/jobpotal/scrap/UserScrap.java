package com.tenco.jobpotal.scrap;

import com.tenco.jobpotal._core.utils.MyDateUtil;
import com.tenco.jobpotal.job_post.JobPost;
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
@Table(name = "user_scrap")
@Entity
public class UserScrap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userScrapId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruit_id")
    private JobPost jobPost;

    @CreationTimestamp
    private Timestamp userScrapDate;

    public boolean isOwner(Long checkUserId){
        return this.user.getUserId().equals(checkUserId);
    }

    public String getTime() {
        return MyDateUtil.timestampFormat(userScrapDate);
    }

}
