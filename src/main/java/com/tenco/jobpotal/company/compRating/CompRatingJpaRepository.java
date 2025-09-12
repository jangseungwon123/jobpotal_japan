package com.tenco.jobpotal.company.compRating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CompRatingJpaRepository extends JpaRepository<CompRating , Long> {


    Optional<CompRating> findByApplInfo_ApplInfoId(Long applInfoId);

    @Query("SELECT AVG(cr.score) FROM CompRating cr JOIN cr.applInfo.jobPost.compInfo.compUser cu WHERE cu.compUserId = :compUserId")
    Double findAverageScoreBycompUserId(@Param("compUserId")Long compUserId);




}
