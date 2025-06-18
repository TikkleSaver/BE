package com.tikklesaver.domain.Challenge.dto.challenge;

import com.tikklesaver.domain.Challenge.entity.JoinChallenge;
import com.tikklesaver.domain.Challenge.entity.enums.Status;
import com.tikklesaver.domain.member.entity.Member;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        private Long memberId;
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


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailChallengerTabListResponseDTO {

        List<DetailChallengerTabResponseDTO> memberList;
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
    public static class DetailChallengerTabResponseDTO {
        private Long id;
        private Long challengeId;
        private Long memberId;
        private String memberImgUrl;
        private String memberName;

    }




}
