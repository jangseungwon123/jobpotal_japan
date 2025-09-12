package com.tenco.jobpotal.community.compCommunity;

import com.tenco.jobpotal.user.comp.CompUser;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

import static jakarta.persistence.CascadeType.REMOVE;

@Data
@Entity
@NoArgsConstructor
@Table(name = "comp_comm_post_info")
public class CompCommunity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @JoinColumn(name = "compUser_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = REMOVE)
    private CompUser compUser; // 게시글 작성자 정보

    @Column(length = 8)
    private String postPassword;

    @Column(length = 100, nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(name = "inst_id", length = 50, nullable = false)
    private String instId;

    @CreationTimestamp
    @Column(name = "inst_date", nullable = false, updatable = false)
    private Timestamp instDate;

    @Builder
    public CompCommunity(Long postId, String title, String content, String instId, String postPassword, CompUser compUser) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.instId = instId;
        this.postPassword = postPassword;
        this.compUser = compUser;
    }
    
    public void update(CompCommunityRequest.UpdateDTO updateDTO){
        this.title = updateDTO.getTitle();
        this.content = updateDTO.getContent();
        this.instId = updateDTO.getInstId();
        this.postPassword = updateDTO.getPostPassword();
    }

    // 게시글 소유자 확인 기능
    public boolean isOwner(Long checkCompId) {
        return this.compUser.getCompUserId().equals(checkCompId);
    }
}
