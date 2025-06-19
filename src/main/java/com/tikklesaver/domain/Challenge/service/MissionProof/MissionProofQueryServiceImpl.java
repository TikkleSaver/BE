package com.tikklesaver.domain.Challenge.service.MissionProof;

import com.tikklesaver.domain.Challenge.converter.ChallengeConverter;
import com.tikklesaver.domain.Challenge.dto.missionProof.MissionProofResponseDTO;
import com.tikklesaver.domain.Challenge.entity.Challenge;
import com.tikklesaver.domain.Challenge.entity.MissionProof;
import com.tikklesaver.domain.Challenge.repository.challenge.ChallengeRepository;
import com.tikklesaver.domain.Challenge.repository.missionProof.MissionProofRepository;
import com.tikklesaver.domain.member.entity.Member;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    @Override
    public MissionProofResponseDTO.missionProofMainDTO getMissionProofMain(Member member, Long challengeId) {

        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int today = now.getDayOfMonth();


        List<MissionProof> proofs = missionProofRepository.findByMonth(member.getId(), challengeId, year, month);

        int missionCountThisMonth = proofs.size();

        // 성공 횟수 = 오늘 날짜 - 이번 달 미션 수행 개수
        int successCount =  missionCountThisMonth;

        // 성공률 계산 (오늘 날짜가 0일인 경우 방지)
        float successRate = today == 0 ? 0 : (successCount * 100.0f / today);

        // 실패 횟수는 미션 수행한 개수
        int failCount = today - missionCountThisMonth;

        List<MissionProofResponseDTO.missionProofResultDTO> missionProofDTOs = proofs.stream()
                .map(ChallengeConverter::toMissionProofResultDTO)
                .collect(Collectors.toList());

        List<MissionProofResponseDTO.top3RankingDTO> top3 = missionProofRepository.findTop3RankersByChallengeAndMonth(challengeId, year, month);

        return MissionProofResponseDTO.missionProofMainDTO.builder()
                .successRate(successRate)
                .successCount(successCount)
                .failCount(failCount)
                .missionProofs(missionProofDTOs)
                .top3Rankings(top3)
                .build();
    }
}
