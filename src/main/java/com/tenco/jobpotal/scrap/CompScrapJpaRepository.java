package com.tenco.jobpotal.scrap;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompScrapJpaRepository extends JpaRepository<CompScrap, Long> {
//
//    @Query("SELECT cs FROM CompScrap cs JOIN FETCH cs.compInfo c JOIN FETCH cs.resume r WHERE c.compId = :compId")
//    List<CompScrapResponse.ScrapListDTO> findAllByResumeAndCompId(@Param("compId") Long compId);

    @Query("SELECT count(*) > 0 FROM CompScrap cs WHERE cs.resume.resumeId = :resumeId AND cs.compInfo.compId = :compId")
    boolean existsByResumeIdAndCompId(@Param("resumeId") Long resumeId, @Param("compId") Long compId);

    @Query("SELECT cs FROM CompScrap cs JOIN FETCH cs.resume r JOIN FETCH cs.compInfo ci WHERE ci.compUser.compUserId = :compUserId")
    List<CompScrap> findAllByCompUserId(@Param("compUserId") Long compUserId);

}
