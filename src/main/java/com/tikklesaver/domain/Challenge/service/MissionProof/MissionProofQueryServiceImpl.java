package com.tikklesaver.domain.Challenge.service.MissionProof;

import com.tikklesaver.domain.Challenge.converter.ChallengeConverter;
import com.tikklesaver.domain.Challenge.dto.missionProof.MissionProofResponseDTO;
import com.tikklesaver.domain.Challenge.entity.MissionProof;
import com.tikklesaver.domain.Challenge.repository.missionProof.MissionProofRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MissionProofQueryServiceImpl implements MissionProofQueryService {

    private final MissionProofRepository missionProofRepository;

    public List<MissionProofResponseDTO.missionProofResultDTO> getMissionProofsByMonth(Long memberId, Long challengeId, int year, int month) {
        List<MissionProof> proofs = missionProofRepository.findByMonth(memberId, challengeId, year, month);
        return proofs.stream()
                .map(ChallengeConverter::toMissionProofResultDTO)
                .collect(Collectors.toList());
    }
}
