package com.tikklesaver.domain.Challenge.service.MissionProof;

import com.tikklesaver.domain.Challenge.dto.challenge.ChallengeRequestDTO;
import com.tikklesaver.domain.Challenge.dto.missionProof.MissionProofRequestDTO;
import com.tikklesaver.domain.Challenge.entity.MissionProof;
import com.tikklesaver.domain.member.entity.Member;
import org.springframework.web.multipart.MultipartFile;

public interface MissionProofCommandService {

    MissionProof createMissionProof(Member member, Long challengeId, MissionProofRequestDTO.CreateMissionDTO request, MultipartFile file);
    MissionProof updateMissionProof(Member member, Long missionProofId, MissionProofRequestDTO.CreateMissionDTO request, MultipartFile file);
    void deleteMissionProof(Member member, Long missionProofId);

}