package com.tikklesaver.domain.Challenge.repository.missionProof;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tikklesaver.domain.Challenge.dto.missionProof.MissionProofResponseDTO;
import com.tikklesaver.domain.Challenge.entity.MissionProof;
import com.tikklesaver.domain.Challenge.entity.QMissionProof;
import com.tikklesaver.domain.member.entity.QMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MissionProofRepositoryImpl implements MissionProofRepositoryCustom {


    private final JPAQueryFactory jpaQueryFactory;
    private final QMissionProof missionProof = QMissionProof.missionProof;

    public List<MissionProof> findByMonth(Long memberId, Long challengeId, int year, int month) {
        LocalDateTime start = LocalDate.of(year, month, 1).atStartOfDay();
        LocalDateTime end = start.plusMonths(1);

        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(missionProof.member.id.eq(memberId));
        predicate.and(missionProof.createdAt.goe(start));
        predicate.and(missionProof.createdAt.lt(end));

        if (challengeId != null) {
            predicate.and(missionProof.challenge.id.eq(challengeId));
        }

        return jpaQueryFactory
                .selectFrom(missionProof)
                .where(predicate)
                .orderBy(missionProof.createdAt.asc())
                .fetch();
    }

    @Override
    public List<MissionProofResponseDTO.top3RankingDTO> findTop3RankersByChallengeAndMonth(Long challengeId, int year, int month) {
        QMissionProof missionProof = QMissionProof.missionProof;
        QMember member = QMember.member;

        LocalDateTime start = LocalDate.of(year, month, 1).atStartOfDay();
        LocalDateTime end = start.plusMonths(1);

        return jpaQueryFactory
                .select(Projections.constructor(
                        MissionProofResponseDTO.top3RankingDTO.class,
                        member.id,
                        member.nickname,
                        member.profileUrl
                ))
                .from(missionProof)
                .join(missionProof.member, member)
                .where(
                        missionProof.challenge.id.eq(challengeId)
                                .and(missionProof.createdAt.goe(start))
                                .and(missionProof.createdAt.lt(end))
                )
                .groupBy(member.id, member.nickname, member.profileUrl)
                .orderBy(missionProof.id.count().desc())
                .limit(3)
                .fetch();
    }
}