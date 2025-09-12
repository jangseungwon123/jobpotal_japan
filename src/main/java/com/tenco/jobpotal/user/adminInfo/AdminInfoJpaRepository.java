package com.tenco.jobpotal.user.adminInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdminInfoJpaRepository extends JpaRepository<AdminInfo, Long> {


    @Query("SELECT a FROM AdminInfo a WHERE a.adminLoginId = :adminLoginId")
    Optional<AdminInfo> findByAdminJoinId(@Param("adminLoginId") String adminLoginId);

    // 관리자 로그인 아이디로 사용자를 조회(로그인 중복체크 모두사용 가능)
    @Query("SELECT a FROM AdminInfo a WHERE a.adminLoginId = :adminLoginId and a.adminPassword = :adminPassword")
    Optional<AdminInfo> findByAdminLoginId(@Param("adminLoginId") String adminLoginId,
                                           @Param("adminPassword") String adminPassword);

}
