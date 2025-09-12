package com.tenco.jobpotal.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReportJpaRepository extends JpaRepository<Report, Long> {

	// 1. Find list of all reports by user id
	@Query("select r from Report r where r.user.userId = :userId order by r.createdAt desc")
	List<Report> findAllByUserId(@Param("userId") Long userId);

	// 2. Find list of all reports by user community id
	@Query("select r from Report r where r.userCommunity.postId = :postId order by r.createdAt desc")
	List<Report> findAllByUserCommunityId(@Param("postId") Long postId);

	// 사용자 ID(userId)와 게시글 ID(postId)를 동시에 만족하는 신고 내역이 있는지 조회합니다.
	Optional<Report> findByUser_UserIdAndUserCommunity_PostId(Long userId, Long postId);

	// 사용자의 ID 로 신고 내역을 조회
	List<Report> findByUser_UserId(Long userId);
}
