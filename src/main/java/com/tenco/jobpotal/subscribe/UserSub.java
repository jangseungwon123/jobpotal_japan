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

import static jakarta.persistence.CascadeType.REMOVE;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "user_sub")
@Entity
public class UserSub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSubId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = REMOVE)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comp_id")
    private CompInfo compInfo;

    @CreationTimestamp
    private Timestamp userSubDate;

    public boolean isOwner(Long checkUserId){
        return this.user.getUserId().equals(checkUserId);
    }

    public String getTime() {
        return MyDateUtil.timestampFormat(userSubDate);
    }

}
