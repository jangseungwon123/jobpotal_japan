package com.tenco.jobpotal.resume;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ResumeJpaRepository extends JpaRepository<Resume,Long> {

    @Query("SELECT r FROM Resume r JOIN FETCH r.user u WHERE u.userId = :id")
    List<Resume> findByUserId(@Param("id") Long id);

    @Query("SELECT r FROM Resume r JOIN FETCH r.user u JOIN FETCH r.userSkillList usl WHERE r.id = :id")
    Optional<Resume> findByIdJoinUser(@Param("id") Long id);

    @Query("SELECT r FROM Resume r JOIN FETCH r.user u ORDER BY r.id DESC")
    Page<Resume> findAllJoinUser(Pageable pageable);

    @Query("SELECT r FROM Resume r JOIN FETCH r.userSkillList WHERE r.id = :id")
    Optional<Resume> findByIdWithSkillList(@Param("id") Long id);

}
