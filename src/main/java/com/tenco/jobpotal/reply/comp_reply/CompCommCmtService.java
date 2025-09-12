package com.tenco.jobpotal.reply.comp_reply;

import com.tenco.jobpotal._core.errors.exception.Exception403;
import com.tenco.jobpotal._core.errors.exception.Exception404;
import com.tenco.jobpotal.community.compCommunity.CompCommunity;
import com.tenco.jobpotal.community.compCommunity.CompCommunityRepository;
import com.tenco.jobpotal.user.LoginUser;
import com.tenco.jobpotal.user.comp.CompUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service

public class CompCommCmtService {

    private final CompCommCmtJPARepository compCommCmtJPARepository;
    private final CompCommunityRepository compCommunityRepository;


    public List<CompCommCmtResponse.CompCommCmtListDTO> list(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<CompCommCmt> compCommCmtPage = compCommCmtJPARepository.findAllJoinCompCommunity(pageable);
        List<CompCommCmtResponse.CompCommCmtListDTO> compCommCmtList = new ArrayList<>();

        for (CompCommCmt compCommCmt : compCommCmtPage.getContent()) {
            CompCommCmtResponse.CompCommCmtListDTO compCommCmtListDTO = new CompCommCmtResponse.CompCommCmtListDTO(compCommCmt);
            compCommCmtList.add(compCommCmtListDTO);
        }
        return compCommCmtList;
    }

    public CompCommCmtResponse.DetailDTO detail(Long id, LoginUser loginUser) {

        CompCommCmt compCommCmt = compCommCmtJPARepository.findByIdJoinCompCommunity(id).orElseThrow(
                () -> new Exception404("댓글을 찾을 수 없습니다"));

        return new CompCommCmtResponse.DetailDTO(compCommCmt,loginUser);
    }

    @Transactional
    public CompCommCmtResponse.SaveDTO save(CompCommCmtRequest.SaveDTO saveDTO,LoginUser loginUser) {

        CompCommunity compCommunity = compCommunityRepository.findById(saveDTO.getPostId())
                .orElseThrow(() -> new Exception404("존재하지 않는 게시글에는 댓글을 작성할 수 없습니다."));

        CompUser compUser = CompUser.builder()
                .compUserId(loginUser.getId())
                .compUserName(loginUser.getName())
                .compUserNickname(loginUser.getUserNickName())
                .build();
    CompCommCmt compCommCmt = saveDTO.toEntity(compUser,compCommunity);
    compCommCmtJPARepository.save(compCommCmt);
    return new CompCommCmtResponse.SaveDTO(compCommCmt);
    }

    @Transactional
    public void deleteById(Long compCommCmtId,LoginUser loginUser) {

        CompCommCmt compCommCmt = compCommCmtJPARepository.findById(compCommCmtId)
                .orElseThrow(() -> new Exception404("삭제할 댓글이 없어요"));

        if (!compCommCmt.isOwner(loginUser.getId())) {
            throw new Exception403("본인이 작성한 댓글만 삭제 할 수 있음");
        }
       compCommCmtJPARepository.deleteById(compCommCmtId);
    }

    @Transactional
    public CompCommCmtResponse.UpdateDTO update(Long id,CompCommCmtRequest.UpdateDTO updateDTO,LoginUser loginUser) {

        CompCommCmt compCommCmt = compCommCmtJPARepository.findByIdJoinCompCommunity(id).orElseThrow(() ->
                new  Exception404("해당 댓글이 존재하지 않습니다"));

        if (!compCommCmt.isOwner(loginUser.getId())) {
            throw new Exception403("본인이 작성하신 댓글만 수정 가능");
        }
        compCommCmt.update(updateDTO);

       return new CompCommCmtResponse.UpdateDTO(compCommCmt);
    }

}
