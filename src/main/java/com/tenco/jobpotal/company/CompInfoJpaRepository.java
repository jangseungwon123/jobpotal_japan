package com.tenco.jobpotal.company;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CompInfoJpaRepository extends JpaRepository<CompInfo, Long> {

    //@Query("SELECT c FROM CompInfo c JOIN FETCH b.user u ORDER BY b.id DESC")
    @Query("SELECT c FROM CompInfo c ORDER BY c.id DESC")
    Page<CompInfo> findAllCompInfo(Pageable pageable);

    @Query("SELECT c FROM CompInfo c WHERE c.companyName like %:keyword% ORDER BY c.id DESC")
    Page<CompInfo> findAllCompInfoByKeyword(Pageable pageable, String keyword);

    @Query("SELECT ci FROM CompInfo ci WHERE ci.compUser.compUserId = :compUserId")
    Optional<CompInfo> findByCompInfo(Long compUserId);

    @Query("SELECT c FROM CompInfo c join fetch c.compUser WHERE c.compId = :compId")
    Optional<CompInfo> findByCompId(@Param("compId") Long compId);

}