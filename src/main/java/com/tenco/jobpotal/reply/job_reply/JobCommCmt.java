package com.tenco.jobpotal.reply.job_reply;

import com.tenco.jobpotal.community.userCommunity.UserCommunity;
import com.tenco.jobpotal.user.normal.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Table(name = "job_comm_cmt")
@Entity
public class JobCommCmt {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobCommCmtId;

     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "user_id",nullable = false)
     private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private UserCommunity userCommunity;


    @Column(nullable = false,length = 500)
    private String content;

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public JobCommCmt(Long jobCommCmtId, User user, UserCommunity userCommunity, String content, Timestamp createdAt) {
        this.jobCommCmtId = jobCommCmtId;
        this.user = user;
        this.userCommunity = userCommunity;
        this.content = content;
        this.createdAt = createdAt;
    }

    @Transient
    private boolean isReplyOwner;

    public boolean isOwner(Long loginUserId) {
        return this.user.getUserId().equals(loginUserId);
    }

    public String getWriterName() {
        return this.user.getUserName();
    }

    public void update(JobCommCmtRequest.UpdateDTO updateDTO) {
        this.content = updateDTO.getContent();
    }

}
