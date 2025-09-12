package com.tenco.jobpotal.user.comp;

import com.tenco.jobpotal.company.CompInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CompUserJpaRepository  extends JpaRepository<CompUser, Long> {

	// 기업회원 로그인 아이디와 비밀번호로 사용자 조회(로그인)
	@Query("select cu from CompUser cu where cu.compUserLoginId = :compUserLoginId and cu.compUserPassword = :compUserPassword")
	Optional<CompUser> findByCompUserLoginIdAndCompUserPassword(@Param("compUserLoginId") String compUserLoginId,
																@Param("compUserPassword") String compUserPassword);

	// 기업회원 로그인 아이디로 사용자 조회(중복체크)
	@Query("select cu from CompUser cu where cu.compUserLoginId = :compUserLoginId or cu.compUserEmail = :compUserEmail")
	Optional<CompUser> findByCompUserExists(@Param("compUserLoginId") String compUserLoginId,
											 @Param("compUserEmail") String compUserEmail);

}
