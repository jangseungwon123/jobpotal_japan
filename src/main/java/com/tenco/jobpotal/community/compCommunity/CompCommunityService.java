package com.tenco.jobpotal.community.compCommunity;

import com.tenco.jobpotal._core.errors.exception.Exception404;
import com.tenco.jobpotal.user.LoginUser;

import com.tenco.jobpotal.user.comp.CompUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompCommunityService {

    private final CompCommunityRepository compCommunityRepository;

    // 전체 게시글 조회
    @Transactional(readOnly = true)
    public Page<CompCommunityResponse.ListDTO> findAllPosts(Pageable pageable, LoginUser loginUser) {
        log.info("기업 커뮤니티 전체 게시글 조회 서비스 시작");
        Page<CompCommunity> compCommunities = compCommunityRepository.findAllWithCompany(pageable);
        return compCommunities.map(community -> CompCommunityResponse.ListDTO.fromEntity(community, loginUser));
    }

    // 단일 게시글 조회
    public CompCommunityResponse.DetailDTO findById(Long postId, LoginUser loginUser) {
        CompCommunity compCommunity = compCommunityRepository.findById(postId).orElseThrow(() ->
                new Exception404("해당 게시글이 존재하지 않습니다.")
        );

        return new CompCommunityResponse.DetailDTO(compCommunity, loginUser);
    }

    // 게시글 생성
    @Transactional
    public CompCommunity savePost(CompUser compUser, CompCommunityRequest.SaveDTO saveDTO) {
        return compCommunityRepository.save(saveDTO.toEntity(compUser));
    }

    // 게시글 수정
    @Transactional
    public CompCommunity communityUpdate(Long postId, CompCommunityRequest.UpdateDTO updateDTO, LoginUser loginUser) {
        // 게시글 조회
        CompCommunity compCommunity = compCommunityRepository.findById(postId).orElseThrow(() ->
                new Exception404("해당 게시글이 존재하지 않습니다.")
        );

        // 게시글 작성자와 로그인한 기업이 동일한지 확인
        if (!compCommunity.isOwner(loginUser.getId())) {
            throw new Exception404("게시글 작성자만 수정할 수 있습니다.");
        }

        // 수정된 내용으로 게시글 업데이트
        compCommunity.update(updateDTO);
        return compCommunity;
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Long postId) {
        compCommunityRepository.deleteById(postId);
    }
}
