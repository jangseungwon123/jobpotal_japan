package com.tenco.jobpotal.alarm;

import com.tenco.jobpotal.user.normal.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;

import static jakarta.persistence.CascadeType.REMOVE;
// 중간 테이블..

@Table(name = "alarm_info")
@Entity
@NoArgsConstructor
@Data
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alarmId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = REMOVE)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Column(nullable = false,length = 200)
    private String content;

    @Column
    private boolean isRead;

    @CreationTimestamp
    private Timestamp createdAt;


}
