package com.tenco.jobpotal.community.compCommunity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompCommunityRepository extends JpaRepository<CompCommunity, Long> {
    
    Page<CompCommunity> findAll(Pageable pageable);
    
    // 기업회원 정보를 조인 페치로
    @Query("SELECT c FROM CompCommunity c LEFT JOIN FETCH c.compUser u")
    Page<CompCommunity> findAllWithCompany(Pageable pageable);
    //CompCommunity - c . comp
}
