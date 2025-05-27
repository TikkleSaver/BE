package com.tikklesaver.domain.Challenge.service.MissionProof;

import com.tikklesaver.domain.Category.entity.Category;
import com.tikklesaver.domain.Challenge.converter.ChallengeConverter;
import com.tikklesaver.domain.Challenge.dto.missionProof.MissionProofRequestDTO;
import com.tikklesaver.domain.Challenge.entity.Challenge;
import com.tikklesaver.domain.Challenge.entity.MissionProof;
import com.tikklesaver.domain.Challenge.repository.ChallengeRepository.ChallengeRepository;
import com.tikklesaver.domain.Challenge.repository.JoinChallengeRepository;
import com.tikklesaver.domain.Challenge.repository.MissionProofRepository;
import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.member.repository.MemberRepository;
import com.tikklesaver.global.apiPayload.code.status.ErrorStatus;
import com.tikklesaver.global.apiPayload.exception.handler.JoinChallengeHandler;
import com.tikklesaver.global.aws.s3.AmazonS3Manager;
import com.tikklesaver.global.common.Uuid;
import com.tikklesaver.global.repository.UuidRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MissionProofCommandServiceImpl implements MissionProofCommandService {
    
    private final ChallengeRepository challengeRepository;
    private final AmazonS3Manager amazonS3Manager;
    private final UuidRepository uuidRepository;
    private final MissionProofRepository missionProofRepository;
    private final JoinChallengeRepository joinChallengeRepository;

    @Override
    public MissionProof createMissionProof(Member member, Long challengeId, MissionProofRequestDTO.CreateMissionDTO request, MultipartFile file) {


        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 챌린지를 찾을 수 없습니다. ID: " + challengeId));

        boolean isJoinChallenge = joinChallengeRepository.existsByChallengeIdAndMemberId(challengeId, member.getId());

        if (!isJoinChallenge) {
            throw new JoinChallengeHandler(ErrorStatus.JOIN_CHALLENGE_NOT_FOUND);
        }

        String imageUrl = null;

        String uuid = UUID.randomUUID().toString();
        Uuid savedUuid = uuidRepository.save(Uuid.builder()
                .uuid(uuid).build());

        if (file != null && !file.isEmpty()) {
            imageUrl = amazonS3Manager.uploadFile(amazonS3Manager.generateChallengeMissionsKeyName(savedUuid),file);
        }

        MissionProof newMissionProof = ChallengeConverter.toMissionProof(member, challenge, request, imageUrl);
        return missionProofRepository.save(newMissionProof);

    }
}
