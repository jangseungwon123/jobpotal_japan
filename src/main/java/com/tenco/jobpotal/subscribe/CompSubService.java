package com.tenco.jobpotal.subscribe;

import com.tenco.jobpotal._core.errors.exception.Exception403;
import com.tenco.jobpotal._core.errors.exception.Exception404;
import com.tenco.jobpotal.company.CompInfo;
import com.tenco.jobpotal.company.CompInfoJpaRepository;
import com.tenco.jobpotal.user.LoginUser;
import com.tenco.jobpotal.user.normal.User;
import com.tenco.jobpotal.user.normal.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CompSubService {

    private  final UserJpaRepository userJpaRepository;
    private final CompInfoJpaRepository compInfoJpaRepository;
    private final CompSubJpaRepository compSubJpaRepository;

    @Transactional
    public CompSubResponse.SaveDTO save(CompSubRequest.SaveDTO saveDTO, LoginUser loginUser) {
        CompInfo compInfo = compInfoJpaRepository.findByCompInfo(loginUser.getId()).orElseThrow(() ->
                new Exception404("존재하지 않는 기업입니다."));
        if (compSubJpaRepository.existsByCompanyIdAndUserId( compInfo.getCompId(),saveDTO.getUserId())){
            throw new Exception403("이미 구독했습니다");
        }
        User user = userJpaRepository.findById(saveDTO.getUserId()).orElseThrow(() ->
                new Exception404("존재하지 않는 사용자입니다"));

        CompSub compSub = CompSub.builder()
                .user(user)
                .compInfo(compInfo)
                .build();

        CompSub savedcompSub = compSubJpaRepository.save(compSub);
        return new CompSubResponse.SaveDTO(savedcompSub);
    }

    // 구독목록 조회 서비스
    public List<CompSubResponse.SubListDTO> findAllByUserAndCompanyId(LoginUser loginUser) {
        CompInfo compInfo = compInfoJpaRepository.findByCompInfo(loginUser.getId())
                .orElseThrow(() -> new Exception404("해당 기업 정보를 찾을 수 없습니다."));
        List<CompSub> compSubList = compSubJpaRepository.findAllByUserAndCompanyId(compInfo.getCompId());
        return compSubList.stream()
                .map(CompSubResponse.SubListDTO::new)
                .collect(Collectors.toList());
    }

    // 구독 삭제
    @Transactional
    public void deleteById(Long compSubId, LoginUser loginUser) {
        CompSub compSub = compSubJpaRepository.findById(compSubId).orElseThrow(() ->
                new Exception404("삭제하려는 구독이 없습니다"));
        if (!compSub.isOwner(loginUser.getId())) {
            throw new Exception403("본인의 구독만 취소할 수 있습니다");
        }
        compSubJpaRepository.deleteById(compSubId);
    }
}
