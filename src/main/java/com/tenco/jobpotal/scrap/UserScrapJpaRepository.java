package com.tenco.jobpotal.scrap;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserScrapJpaRepository extends JpaRepository<UserScrap, Long> {

    @Query("SELECT us FROM UserScrap us JOIN FETCH us.user u JOIN FETCH us.jobPost c WHERE u.userId = :userId")
    List<UserScrapResponse.ScrapListDTO> findAllByUserAndJobPostId(@Param("userId") Long userId);

    @Query("select count(*) > 0 from UserScrap us where us.jobPost.id = :recruitId and us.user.userId = :userId")
    boolean existsByJobPostIdAndUserId(@Param("recruitId") Long recruitId, @Param("userId")Long userId);

}
