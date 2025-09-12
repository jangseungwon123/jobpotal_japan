package com.tenco.jobpotal.user.adminInfo;

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
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminInfoService {

    private final AdminInfoJpaRepository adminInfoJpaRepository;

    // 관리자 회원가입
    @Transactional
    public AdminInfoResponse.JoinDTO join(AdminInfoRequest.AdminJoinDTO joinDTO, LoginUser loginUser) {
        // 동일한 아이디 있는지 확인(중복체크)
        if (loginUser == null) {
            throw new Exception403("로그인 정보가 없습니다.");
        }
        adminInfoJpaRepository.findById(loginUser.getId()).orElseThrow(() ->
                new Exception403("관리자 생성 권한이 없습니다.")
        );
        adminInfoJpaRepository.findByAdminJoinId(joinDTO.getAdminLoginId())
                .ifPresent(adminInfo1 -> {
                    throw new Exception400("이미 존재하는 관리자ID 입니다.");
                });
        AdminInfo savedAdmin = adminInfoJpaRepository.save(joinDTO.toEntity());
        return new AdminInfoResponse.JoinDTO(savedAdmin);
    }

    // 로그인
    public String login(AdminInfoRequest.LoginDTO loginDTO) {
        AdminInfo adminInfo = adminInfoJpaRepository
                .findByAdminLoginId(loginDTO.getAdminLoginId(), loginDTO.getAdminPassword()).orElseThrow(() -> {
                    throw new Exception401("아이디 또는 비밀번호가 틀렸어요");
                });
        LoginUser loginUser = LoginUser.builder()
                .id(adminInfo.getAdminId())
                .name(adminInfo.getAdminName())
                .loginId(adminInfo.getAdminLoginId())
                .isAdmin(true) // TODO 임시값 추후 role 부여 할 예정임
                .isCompany(false)
                .build();

        String jwt = JwtUtil.create(loginUser);
        return jwt;
    }

    //관리자 정보 상세 조회
    public AdminInfoResponse.DetailDTO findByTargetId(Long targetAdminId, LoginUser loginUser) {
        if (loginUser == null) {
            throw new Exception403("로그인 정보가 없습니다.");
        }
        adminInfoJpaRepository.findById(loginUser.getId()).orElseThrow(() ->
                new Exception403("다른 관리자를 정보를 조회할 권한이 없습니다.")
        );
        AdminInfo selectedAdminInfoUser = adminInfoJpaRepository.findById(targetAdminId).orElseThrow(() -> {
            throw new Exception404("해당 관리자를 찾을 수 없습니다.");
        });
        return new AdminInfoResponse.DetailDTO(selectedAdminInfoUser);
    }

    // 관리자 정보 수정
    @Transactional
    public void updateByAdmin(Long adminId, AdminInfoRequest.UpdateDTO updateDTO, LoginUser loginUser) {
        if (!loginUser.getId().equals(adminId)) {
            throw new Exception403("자신의 비밀번호만 수정할 수 있습니다");
        }
        AdminInfo adminInfo = adminInfoJpaRepository.findById(adminId).orElseThrow(() -> {
            throw new Exception403("해당 관리자를 찾을 수 없습니다.");
        });
        if (!adminInfo.getAdminPassword().equals(updateDTO.getCurrentPassword())) {
            throw new Exception400("비밀번호가 일치하지 않습니다. 다시 입력해주세요");
        }
        if (adminInfo.getAdminPassword().equals(updateDTO.getNewPassword())) {
            throw new Exception400("같은 비밀번호로 바꿀 수 없습니다.");
        }
        adminInfo.setAdminPassword(updateDTO.getNewPassword());
    }

    @Transactional
    public void deleteByAdmin(LoginUser loginUser, Long adminId) {
        AdminInfo adminInfo = adminInfoJpaRepository.findById(adminId)
                .orElseThrow(() -> new Exception404("존재하지 않는 관리자입니다."));
        if (!adminInfo.getAdminId().equals(loginUser.getId())) {
            throw new Exception403("본인 계정만 삭제할 수 있습니다.");
        }
        adminInfoJpaRepository.delete(adminInfo);
    }
}
