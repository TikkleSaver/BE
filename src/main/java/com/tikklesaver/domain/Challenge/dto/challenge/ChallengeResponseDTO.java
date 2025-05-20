package com.tikklesaver.domain.Challenge.dto.challenge;

import com.tikklesaver.domain.Challenge.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class ChallengeResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class challengeResultDTO {
        Long challengeId;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChallengePreViewListDTO {

        List<ChallengePreViewDTO> challengeList;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChallengePreViewDTO {
        Long challengeId;
        String title;
        Long categoryId;
        String imgUrl;
        LocalDateTime createdAt;


    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChallengePreviewWithStatusResponseDTO {
        private Long challengeId;
        private String title;
        private String description;
        private String category;
        private String imgUrl;
        private List<String> missionMethods;
        private Status status;
        private Long leaderId;
        private String isPublic;
        private boolean isScrapped;
        private List<String> challengerImages;
        private Integer challengerCount;


    }

}
