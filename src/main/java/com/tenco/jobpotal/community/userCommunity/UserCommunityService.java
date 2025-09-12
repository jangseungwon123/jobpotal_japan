package com.tenco.jobpotal.community.userCommunity;

import com.tenco.jobpotal._core.errors.exception.Exception404;
import com.tenco.jobpotal.user.LoginUser;
import com.tenco.jobpotal.user.normal.User;
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
public class UserCommunityService {

    private final UserCommunityJpaRepository userCommunityJpaRepository;

    // 전체 게시글 조회

    @Transactional(readOnly = true)
    public Page<UserCommunityResponse.ListDTO> findAllPosts(Pageable pageable, LoginUser loginUser) {
        log.info("전체 게시글 조회 서비스 시작");
        Page<UserCommunity> userCommunities = userCommunityJpaRepository.findAllWithUser(pageable);
        return userCommunities.map(community -> UserCommunityResponse.ListDTO.fromEntity(community, loginUser));
    }

    // 단일 게시글 조회

    public UserCommunityResponse.DetailDTO findById(Long postId, LoginUser loginUser) {
        UserCommunity userCommunity = userCommunityJpaRepository.findById(postId).orElseThrow(() ->
                new Exception404("해당 게시글이 존재하지 않습니다.")
        );

        return new UserCommunityResponse.DetailDTO(userCommunity, loginUser);
    }

    // 게시글 생성
    @Transactional
    public UserCommunity savePost(User user, UserCommunityRequest.SaveDTO saveDTO) {
        return userCommunityJpaRepository.save(saveDTO.toEntity(user));
    }

    // 게시글 수정
    @Transactional
    public UserCommunity communityUpdate(Long postId, UserCommunityRequest.UserCommunityUpdateDTO userCommunityUpdateDTO, LoginUser loginUser) {
        // 게시글 조회
        UserCommunity userCommunity = userCommunityJpaRepository.findById(postId).orElseThrow(() ->
                new Exception404("해당 게시글이 존재하지 않습니다.")
        );

        // 게시글 작성자와 로그인한 사용자가 동일한지 확인
        if (!userCommunity.isOwner(loginUser.getId())) {
            throw new Exception404("게시글 작성자만 수정할 수 있습니다.");
        }

        // 수정된 내용으로 게시글 업데이트
        userCommunity.update(userCommunityUpdateDTO);
        return userCommunity;
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Long postId) {
        userCommunityJpaRepository.deleteById(postId);
    }

}
