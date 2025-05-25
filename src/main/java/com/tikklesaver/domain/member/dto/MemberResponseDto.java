package com.tikklesaver.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class MemberResponseDto {

        @Builder
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class MemberInfoDTO {
            Long id;
            String nickname;
            String profileUrl;
        }

    }
