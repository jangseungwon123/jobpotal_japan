package com.tenco.jobpotal.reply.comp_reply;

import com.tenco.jobpotal.community.compCommunity.CompCommunity;
import com.tenco.jobpotal.user.comp.CompUser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class CompCommCmtRequest {

    @Data
    public static class SaveDTO {
        @NotNull(message = "게시물 ID가 필요합니다.")
        private Long postId;

        @NotBlank(message = "내용을 입력 해주세요.")
        @Size(min = 20,max = 100, message = "내용을 최소 20자 최대 100자로 입력 해주세요.")
        private String content;

        public CompCommCmt toEntity(CompUser compUser, CompCommunity compCommunity){
            return CompCommCmt.builder()
                    .content(content.trim())
                    .compUser(compUser)
                    .compCommunity(compCommunity)
                    .build();
        }
    }

    @Data
    public static class UpdateDTO {

        @NotBlank(message = "내용을 입력 해주세요.")
        @Size(min = 20,max = 100,message = "내용을 최소 20자 최대 100자로 입력 해주세요.")
        private String content;

    }
}
