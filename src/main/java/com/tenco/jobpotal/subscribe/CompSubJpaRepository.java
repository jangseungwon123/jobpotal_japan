package com.tenco.jobpotal.subscribe;

import com.tenco.jobpotal.scrap.CompScrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompSubJpaRepository extends JpaRepository<CompSub, Long> {

    @Query("SELECT cs FROM CompSub cs JOIN FETCH cs.user u JOIN FETCH cs.compInfo c WHERE c.compId = :compId")
    List<CompSub> findAllByUserAndCompanyId(@Param("compId") Long compId);

    @Query("SELECT cs FROM CompScrap cs JOIN FETCH cs.resume r JOIN FETCH cs.compInfo ci WHERE ci.compUser.compUserId = :compUserId")
    List<CompScrap> findAllByCompUserId(@Param("compUserId") Long compUserId);

    @Query("select count(*) > 0 from CompSub cs where cs.compInfo.compId = :compId and cs.user.userId = :userId")
    boolean existsByCompanyIdAndUserId(@Param("compId") Long compId, @Param("userId") Long userId);

//    @Query("SELECT us FROM UserSub us JOIN FETCH us.user u JOIN FETCH us.compInfo c WHERE u.userId = :userId")
//    List<UserSubResponse.SubListDTO> findAllByUserAndCompanyId(@Param("userId") Long userId);


}
