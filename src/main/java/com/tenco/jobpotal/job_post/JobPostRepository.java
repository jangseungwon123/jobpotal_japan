package com.tenco.jobpotal.job_post;

import com.tenco.jobpotal.community.userCommunity.UserCommunity;
import com.tenco.jobpotal.company.CompInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {

    // 스킬리스트 정보를 조인 페치로 가져옴
    @Query("SELECT j FROM JobPost j LEFT JOIN FETCH j.skillList s")
    List<JobPost> findAllWithUser();

    // 제목이나 내용에 키워드가 포함된 공고 검색
    @Query("SELECT j FROM JobPost j WHERE j.title LIKE %:keyword% OR j.content LIKE %:keyword%")
    List<JobPost> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT j FROM JobPost j join fetch j.compInfo WHERE j.recruitId = : jobPostId")
    Optional<JobPost> findByJobPostId(@Param("jobPostId")Long jobPostId);

    @Query("SELECT j FROM JobPost j join fetch j.compInfo ci join fetch j.skillList sl WHERE ci.compId IN (:compIds) AND sl.skillId IN (:skillIds)")
    List<JobPost> findByCompIdAndSkillId(@Param("compIds")List<Long> compIds, @Param("skillIds")List<Long> skillIds);

}


