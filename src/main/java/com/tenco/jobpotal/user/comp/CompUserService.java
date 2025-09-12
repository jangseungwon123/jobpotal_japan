package com.tenco.jobpotal.user.comp;

import com.tenco.jobpotal._core.errors.exception.Exception400;
import com.tenco.jobpotal._core.errors.exception.Exception401;
import com.tenco.jobpotal._core.errors.exception.Exception403;
import com.tenco.jobpotal._core.errors.exception.Exception404;
import com.tenco.jobpotal._core.utils.JwtUtil;
import com.tenco.jobpotal.user.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CompUserService {
	private final CompUserJpaRepository compUserJpaRepository;

	// 회원가입
	@Transactional
	public CompUserResponse.JoinDTO join(CompUserRequest.JoinDTO joinDTO) {
		compUserJpaRepository.findByCompUserExists(joinDTO.getCompUserLoginId(), joinDTO.getCompUserEmail())
				.ifPresent(compUser -> {
					throw new Exception400("이미 존재하는 아이디 또는 이메일입니다");
				});
		CompUser savedUser = compUserJpaRepository.save(joinDTO.toEntity());
		return new CompUserResponse.JoinDTO(savedUser);
	}

	// 로그인
	public String login(CompUserRequest.LoginDTO loginDTO) {
		CompUser user = compUserJpaRepository
				.findByCompUserLoginIdAndCompUserPassword(
						loginDTO.getCompUserLoginId(), loginDTO.getCompUserPassword())
				.orElseThrow(() -> new Exception401("아이디 또는 비밀번호가 일치하지 않습니다."));
		LoginUser loginUser = LoginUser.builder()
				.id(user.getCompUserId())
				.name(user.getCompUserName())
				.loginId(user.getCompUserLoginId())
				.userNickName(user.getCompUserNickname())
				.isCompany(true)
				.isAdmin(false)
				.build();

		String jwt = JwtUtil.create(loginUser);
		return jwt;
	}

	// 회원정보조회 //Long requestCompUserId,
	public CompUserResponse.DetailDTO findCompUserByCompUserId(
			 Long sessionCompUserId) {

		CompUser selectedCompUser = compUserJpaRepository.findById(sessionCompUserId)
				.orElseThrow(() -> new Exception404("존재하지 않는 회원입니다"));
		return new CompUserResponse.DetailDTO(selectedCompUser);
	}

	// 회원정보수정
	@Transactional
	public CompUserResponse.UpdateDTO updateById(
			Long requestCompUserId, Long sessionCompUserId, CompUserRequest.UpdateDTO updateDTO) {
		if (!requestCompUserId.equals(sessionCompUserId)) {
			throw new Exception403("접근권한이 없습니다");
		}
		CompUser selectedCompUser = compUserJpaRepository.findById(requestCompUserId)
				.orElseThrow(() -> new Exception404("존재하지 않는 회원입니다"));
		selectedCompUser.update(updateDTO);

		return new CompUserResponse.UpdateDTO(selectedCompUser);
	}
}
