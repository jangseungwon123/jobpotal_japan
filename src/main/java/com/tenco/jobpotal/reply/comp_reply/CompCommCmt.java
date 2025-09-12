package com.tenco.jobpotal.reply.comp_reply;

import com.tenco.jobpotal.community.compCommunity.CompCommunity;
import com.tenco.jobpotal.user.comp.CompUser;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Table(name = "comp_comm_cmt")
@Entity
public class CompCommCmt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long compCommCmtId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comp_user_id",nullable = false)
    private CompUser compUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private CompCommunity compCommunity;


    @Column(nullable = false,length = 500)
    private String content;

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder

    public CompCommCmt(Long compCommCmtId, CompUser compUser, CompCommunity compCommunity, String content, Timestamp createdAt) {
        this.compCommCmtId = compCommCmtId;
        this.compUser = compUser;
        this.compCommunity = compCommunity;
        this.content = content;
        this.createdAt = createdAt;
    }

    @Transient
    private boolean isReplyOwner;

    public boolean isOwner(Long loginUserId) {
        return this.compUser.getCompUserId().equals(loginUserId);
    }

    public String getWriterName(){
        return this.compUser.getCompUserName();
    }
public void update(CompCommCmtRequest.UpdateDTO updateDTO){
        this.content = updateDTO.getContent();
}


}


