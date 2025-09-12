package com.tenco.jobpotal.alarm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlarmJpaRepository extends JpaRepository <Alarm, Long>{
    // 필요한기능
    // 1. 알람 전체 목록 보기
    // 2. 알람 수정(읽음 처리 하기)

    @Query("SELECT a FROM Alarm a WHERE a.user.userId = :userId ORDER BY a.createdAt DESC")
    List<Alarm> findAllByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(a) FROM Alarm a WHERE a.user.userId = :userId AND a.isRead = false")
    Long countUnreadByUserId(@Param("userId") Long userId);

}
