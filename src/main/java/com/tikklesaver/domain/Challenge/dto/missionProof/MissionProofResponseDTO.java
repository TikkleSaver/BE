package com.tikklesaver.domain.Challenge.dto.missionProof;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
}
