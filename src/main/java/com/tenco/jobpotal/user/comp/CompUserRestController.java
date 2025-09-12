package com.tenco.jobpotal.user.comp;

import com.tenco.jobpotal._core.common.ApiUtil;
import com.tenco.jobpotal._core.errors.exception.Exception401;
import com.tenco.jobpotal._core.errors.exception.Exception403;
import com.tenco.jobpotal._core.utils.Define;
import com.tenco.jobpotal.user.LoginUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "CompUser", description = "企業会員管理API")
public class CompUserRestController {
	private final CompUserService compUserService;

	@Operation(summary = "会員登録", operationId = "CompUserJoin")
	@PostMapping("/api/compUsers/join")
	public ResponseEntity<?> join(@Valid @RequestBody CompUserRequest.JoinDTO joinDTO, Errors errors) {
		CompUserResponse.JoinDTO joinedCompUser = compUserService.join(joinDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiUtil<>("회원 가입 완료"));
	}

	@Operation(summary = "ログイン", operationId = "CompUserLogin")
	@PostMapping("/api/compUsers/login")
	public ResponseEntity<?> login(@Valid @RequestBody CompUserRequest.LoginDTO loginDTO, Errors errors) {
		String jwtToken = compUserService.login(loginDTO);
		System.out.println("1111111111111111111111111111");
		return ResponseEntity.ok()
				.header(Define.AUTH, Define.BEARER + jwtToken)
				.body(new ApiUtil<>(jwtToken));
	}

	@Operation(summary = "会員情報取得", operationId = "CompUserDetail")
	@GetMapping("/api/compUsers/{compUserId}")
	public ResponseEntity<?> getCompUserInfo(
			@PathVariable(name = "compUserId") Long compUserId,
			@RequestAttribute(Define.LOGIN_USER) LoginUser loginUser) {
		if (loginUser == null) {
			throw new Exception401("로그인이 필요합니다");
		}
		if (!compUserId.equals(loginUser.getId())) {
			throw new Exception403("본인 정보만 조회가능합니다.");
		}
		CompUserResponse.DetailDTO compUserDetail =
				compUserService.findCompUserByCompUserId(loginUser.getId());
		return ResponseEntity.ok(new ApiUtil<>(compUserDetail));
	}

	@Operation(summary = "会員情報修正", operationId = "CompUserUpdate")
	@PutMapping("/api/compUsers/{compUserId}")
	public ResponseEntity<?> updateCompUser(
			@PathVariable(name = "compUserId") Long compUserId,
			@RequestAttribute(Define.LOGIN_USER) LoginUser loginUser,
			@Valid @RequestBody CompUserRequest.UpdateDTO updateDTO, Errors errors) {
		if (loginUser == null) {
			throw new Exception401("로그인이 필요합니다");
		}
		CompUserResponse.UpdateDTO updatedCompUser =
				compUserService.updateById(compUserId, loginUser.getId(), updateDTO);
		return ResponseEntity.ok().body(new ApiUtil<>(updatedCompUser));
	}

	@Operation(summary = "ログアウト", description = "로그아웃", operationId = "CompUserLogout")
	@PostMapping("/api/compUsers/logout")
	public ResponseEntity<?> logout() {
		return ResponseEntity.ok(new ApiUtil<>("로그아웃 성공"));
	}

}
