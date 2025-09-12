package com.tenco.jobpotal.reply.job_reply;

import com.tenco.jobpotal._core.errors.exception.Exception403;
import com.tenco.jobpotal._core.errors.exception.Exception404;
import com.tenco.jobpotal.community.userCommunity.UserCommunity;
import com.tenco.jobpotal.community.userCommunity.UserCommunityJpaRepository;
import com.tenco.jobpotal.user.LoginUser;
import com.tenco.jobpotal.user.normal.User;
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

public class JobCommCmtService {

    private final JobCommCmtJPARepository jobCommCmtJPARepository;
    private final UserCommunityJpaRepository userCommunityJpaRepository;


    public List<JobCommCmtResponse.JobCommCmtListDTO> list(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<JobCommCmt> jobCommCmtPage = jobCommCmtJPARepository.findAllJoinUserCommunity(pageable);
        List<JobCommCmtResponse.JobCommCmtListDTO> jobCommCmtList = new ArrayList<>();

        for (JobCommCmt jobCommCmt : jobCommCmtPage.getContent()) {
            JobCommCmtResponse.JobCommCmtListDTO jobCommCmtListDTO = new JobCommCmtResponse.JobCommCmtListDTO(jobCommCmt);
            jobCommCmtList.add(jobCommCmtListDTO);
        }
        return jobCommCmtList;
    }

    public JobCommCmtResponse.DetailDTO detail(Long id, LoginUser loginUser) {

        JobCommCmt jobCommCmt = jobCommCmtJPARepository.findByIdJoinUserCommunity(id).orElseThrow(
                () -> new Exception404("댓글을 찾을 수 없습니다"));

        return new JobCommCmtResponse.DetailDTO(jobCommCmt, loginUser);
    }

    @Transactional
    public JobCommCmtResponse.SaveDTO save(JobCommCmtRequest.SaveDTO saveDTO, LoginUser loginUser) {

        UserCommunity userCommunity = userCommunityJpaRepository.findById(saveDTO.getPostId())
                .orElseThrow(() -> new Exception404("존재하지 않는 게시글에는 댓글을 작성할 수 없습니다."));

        User user = User.builder()
                .userId(loginUser.getId())
                .userName(loginUser.getName())
                .userNickname(loginUser.getUserNickName())
                .build();

        JobCommCmt jobCommCmt = saveDTO.toEntity(user, userCommunity);
        jobCommCmtJPARepository.save(jobCommCmt);

        return new JobCommCmtResponse.SaveDTO(jobCommCmt);
    }


    @Transactional
    public void deleteById(Long jobCommCmtId, LoginUser loginUser) {

        JobCommCmt jobCommCmt = jobCommCmtJPARepository.findById(jobCommCmtId)
                .orElseThrow(() -> new Exception404("삭제할 댓글이 없어요"));

        if (!jobCommCmt.isOwner(loginUser.getId())) {
            throw new Exception403("본인이 작성한 댓글만 삭제 할 수 있음");
        }

        jobCommCmtJPARepository.deleteById(jobCommCmtId);
    }

    @Transactional
    public JobCommCmtResponse.UpdateDTO update(Long id, JobCommCmtRequest.UpdateDTO updateDTO, LoginUser loginUser) {

        JobCommCmt jobCommCmt = jobCommCmtJPARepository.findByIdJoinUserCommunity(id).orElseThrow(() ->
                new Exception404("해당 댓글이 존재하지 않습니다"));

        if (!jobCommCmt.isOwner(loginUser.getId())) {
            throw new Exception403("본인이 작성하신 댓글만 수정 가능");
        }
        jobCommCmt.update(updateDTO);

        return new JobCommCmtResponse.UpdateDTO(jobCommCmt);
    }
}
