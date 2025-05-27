package com.tikklesaver.domain.Challenge.dto.missionProof;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class MissionProofResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class missionProofResultDTO {
        Long missionProofId;
        String content;
        String imageUrl;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class top3RankingDTO {
        Long memberId;
        String nickname;
        String imageUrl;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class missionProofMainDTO{

        float successRate;
        List<missionProofResultDTO> missionProofs;
        List<top3RankingDTO> top3Rankings;
        int successCount;
        int failCount;
    }
}
