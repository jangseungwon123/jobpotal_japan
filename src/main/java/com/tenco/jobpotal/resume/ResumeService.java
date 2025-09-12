package com.tenco.jobpotal.resume;


import com.tenco.jobpotal._core.errors.exception.Exception404;
import com.tenco.jobpotal.skill.SkillList;
import com.tenco.jobpotal.user.LoginUser;
import com.tenco.jobpotal.user.normal.User;
import com.tenco.jobpotal.user.normal.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ResumeService {

    private final ResumeJpaRepository resumeJpaRepository;
    private final SkillListJpaRepository skillListJpaRepository;
    private final UserSkillListRepository userSkillListRepository;
    private final UserJpaRepository userJpaRepository;

    @Transactional
    public void save(ResumeRequest.SaveDTO saveDTO,LoginUser loginUser ) {
        User user = userJpaRepository.findById(loginUser.getId()).orElseThrow(
                () -> new Exception404("사용자를 찾을 수 없습니다."));
        Resume resume = resumeJpaRepository.save(saveDTO.toEntity(user));
        if (resume == null){ throw new Exception404("이력서를 찾을 수 없습니다");}
        SkillList SkillStack = skillListJpaRepository.findBySkillId(saveDTO.getSkillId()).orElseThrow(
                () -> new Exception404("스킬을 찾을 수 없습니다."));

        UserSkillList userSkillSetting = UserSkillList.builder()
                .user(user)
                .resume(resume)
                .skillList(SkillStack)
                .instId(saveDTO.getName())
                .build();

        userSkillListRepository.save(userSkillSetting);

        resume.setUserSkillList(userSkillSetting);
    }



    @Transactional
    public void update(Long id, ResumeRequest.UpdateDTO updateDTO,
                                           LoginUser loginUser) {
        User user = userJpaRepository.findById(loginUser.getId()).orElseThrow(() ->
                new Exception404("사용자를 찾지 못했습니다"));
        log.info("user 조회 및 확인 : {}", user );
        Resume resume = resumeJpaRepository.findByIdJoinUser(id).orElseThrow(() ->
                new Exception404("해당 이력서가 존재하지 않습니다"));

        SkillList skillStack = skillListJpaRepository.findBySkillId(updateDTO.getSkillId())
                .orElseThrow(() -> new Exception404("스킬을 찾을 수 없습니다."));

        // 이력서 수정 진행
        resume.update(updateDTO);

        log.info("if 체킹 전");
        if (!resume.getUserSkillList().getSkillList().getSkillId().equals(updateDTO.getSkillId())) {
            log.info("if 체크 시작");
            UserSkillList userSkillSetting = UserSkillList.builder()
                    .user(user)
                    .resume(resume)
                    .skillList(skillStack)
                    .instId(updateDTO.getName())
                    .build();
            log.info("빌더 객체 생성 완료");
            //더티체킹을 통해 SKILL 정보 업데이트
            resume.setUserSkillList(userSkillSetting);
            log.info("resume userSkillList 업데이트 완료");
        }
        log.info("전체 업데이트 완료");
    }

    @Transactional
    public void deleteById(Long id, LoginUser loginUser) {
        Resume resume = resumeJpaRepository.findById(id).orElseThrow(() ->
                new Exception404("삭제 하려는 게시글이 없습니다"));
        resumeJpaRepository.deleteById(id);
    }

    public ResumeResponse.DetailDTO detail(Long id, LoginUser loginUser) {

        Resume resume = resumeJpaRepository.findByIdJoinUser(id).orElseThrow(
                () -> new Exception404("이력서를 찾을 수 없습니다"));


        return new ResumeResponse.DetailDTO(resume, loginUser);
    }

    public List<ResumeResponse.ResumeListResponseDTO> list(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Resume> resumePage = resumeJpaRepository.findAllJoinUser(pageable);
        List<ResumeResponse.ResumeListResponseDTO> resumeList = new ArrayList<>();
        for (Resume resume : resumePage.getContent()) {
            ResumeResponse.ResumeListResponseDTO resumeListResponseDTO = new ResumeResponse.ResumeListResponseDTO(resume);
            resumeList.add(resumeListResponseDTO);
        }
        return resumeList;
    }
}
