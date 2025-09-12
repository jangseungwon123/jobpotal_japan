package com.tenco.jobpotal.job_post;

import com.tenco.jobpotal.company.CompInfo;
import com.tenco.jobpotal.skill.SkillList;
import com.tenco.jobpotal.user.LoginUser;
import com.tenco.jobpotal.user.comp.CompUser;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Future;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class JobPostRequestDTO {

    @NotNull(message = "회사 ID는 필수입니다.")
    private Long compId;

    @NotBlank(message = "제목은 비어 있을 수 없습니다.")
    private String title;

    @NotBlank(message = "내용은 비어 있을 수 없습니다.")
    private String content;

    @NotBlank(message = "경력 요구사항은 비어 있을 수 없습니다.")
    private String requireCareerYears;

    @NotBlank(message = "고용 형태는 비어 있을 수 없습니다.")
    private String employmentType;

    @NotNull(message = "요구 스킬은 비어 있을 수 없습니다.")
    private Long skillId;

    @NotNull(message = "게시일은 필수입니다.")
    private LocalDateTime postedAt;

    @NotNull(message = "마감일은 필수입니다.")
    @Future(message = "마감일은 미래여야 합니다.")
    private LocalDateTime deadline;

    public JobPost toEntity(CompInfo compInfo, SkillList skillList, LoginUser loginUser) {
        return JobPost.builder()
                .compInfo(compInfo)
                .skillList(skillList)
                .title(this.title)
                .content(this.content)
                .requireCareerYears(this.requireCareerYears)
                .employmentType(this.employmentType)
                .instId(loginUser.getLoginId())
                .postedAt(this.postedAt)
                .deadline(this.deadline)
        .build();
    }
}
