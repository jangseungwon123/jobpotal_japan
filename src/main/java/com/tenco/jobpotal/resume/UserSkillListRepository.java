package com.tenco.jobpotal.resume;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserSkillListRepository extends JpaRepository<UserSkillList, Long> {
    @Query("SELECT u FROM UserSkillList u WHERE u.resume = :resume")
    Optional<UserSkillList> findByResume(@Param("resume") Resume resume);
}
