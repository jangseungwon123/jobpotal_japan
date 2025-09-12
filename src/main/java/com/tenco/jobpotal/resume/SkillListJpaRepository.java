package com.tenco.jobpotal.resume;


import com.tenco.jobpotal.company.CompInfo;
import com.tenco.jobpotal.skill.SkillList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SkillListJpaRepository extends JpaRepository<SkillList, Long> {

    @Query("SELECT sl FROM SkillList sl WHERE sl.skillId = :skillId")
    Optional<SkillList> findBySkillId(@Param("skillId") Long skillId);

}
