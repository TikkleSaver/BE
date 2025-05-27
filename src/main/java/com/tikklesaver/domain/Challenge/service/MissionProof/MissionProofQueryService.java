package com.tikklesaver.domain.Challenge.service.MissionProof;

import com.tikklesaver.domain.Challenge.dto.missionProof.MissionProofResponseDTO;

import java.util.List;

public interface MissionProofQueryService {

   List<MissionProofResponseDTO.missionProofResultDTO> getMissionProofsByMonth(Long memberId, Long challengeId, int year, int month);
}
