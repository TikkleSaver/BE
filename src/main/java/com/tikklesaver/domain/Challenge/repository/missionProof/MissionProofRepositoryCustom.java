package com.tikklesaver.domain.Challenge.repository.missionProof;

import com.tikklesaver.domain.Challenge.dto.missionProof.MissionProofResponseDTO;
import com.tikklesaver.domain.Challenge.entity.MissionProof;

import java.util.List;

public interface MissionProofRepositoryCustom {

   List<MissionProof> findByMonth(Long memberId, Long challengeId, int year, int month) ;
   List<MissionProofResponseDTO.top3RankingDTO> findTop3RankersByChallengeAndMonth(Long challengeId, int year, int month);
}
