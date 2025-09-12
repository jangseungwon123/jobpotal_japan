package com.tenco.jobpotal.reply.comp_reply;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CompCommCmtJPARepository extends JpaRepository<CompCommCmt,Long> {

    @Query("SELECT c FROM CompCommCmt c JOIN FETCH c.compUser JOIN FETCH c.compCommunity ORDER BY c.compCommCmtId DESC")
    Page<CompCommCmt> findAllJoinCompCommunity(Pageable pageable);

    @Query("SELECT c FROM CompCommCmt c JOIN FETCH c.compUser JOIN FETCH c.compCommunity WHERE c.compCommCmtId = :id")
    Optional<CompCommCmt> findByIdJoinCompCommunity(@Param("id") Long id);


}
