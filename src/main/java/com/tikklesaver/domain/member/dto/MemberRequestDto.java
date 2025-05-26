package com.tikklesaver.domain.member.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class MemberRequestDto
{
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateProfileDTO {
        @NotNull(message = "닉네임은 필수입니다.")
        private String nickname;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryDTO {
        @NotNull(message = "아이디는 필수")
        private Long memberId;

        @NotNull
        private List<Long> categoryList;
    }

}
