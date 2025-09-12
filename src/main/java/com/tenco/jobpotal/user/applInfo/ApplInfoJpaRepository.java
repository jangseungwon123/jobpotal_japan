package com.tenco.jobpotal.user.applInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplInfoJpaRepository extends JpaRepository<ApplInfo, Long> {

    @Query("select a from ApplInfo a where a.resume.user.userId = :userId and a.jobPost.recruitId = :jobPostId")
    ApplInfo findByUserIdAndJobPostId(@Param("userId") Long userId, @Param("jobPostId") Long jobPostId);

    @Query("select a from ApplInfo a where a.resume.user.userId = :userId")
    List<ApplInfo> findByUserId(@Param("userId") Long userId);

    @Query("select a from ApplInfo a where a.jobPost.compInfo.compUser.compUserId = :comUserId")
    List<ApplInfo> findByCompId(@Param("comUserId") Long comUserId);

}
