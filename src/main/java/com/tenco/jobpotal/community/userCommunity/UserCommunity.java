package com.tenco.jobpotal.community.userCommunity;

import com.tenco.jobpotal.user.normal.User;
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
@Table(name = "comm_post_info")
public class UserCommunity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = REMOVE)
    private User user; // 게시글 작성자 정보

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
    public UserCommunity(Long postId,String title, String content, String instId, String postPassword,User user) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.instId = instId;
        this.postPassword = postPassword;
        this.user = user;
    }
    public void update(UserCommunityRequest.UserCommunityUpdateDTO userCommunityUpdateDTO){
        this.title = userCommunityUpdateDTO.getTitle();
        this.content = userCommunityUpdateDTO.getContent();
        this.instId = userCommunityUpdateDTO.getInstId();
        this.postPassword = userCommunityUpdateDTO.getPostPassword();

    }

    // 게시글 소유자 확인 기능./
    public boolean isOwner(Long checkUserId) {
        return this.user.getUserId().equals(checkUserId);


}}