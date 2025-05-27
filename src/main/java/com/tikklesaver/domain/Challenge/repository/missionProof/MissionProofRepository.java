package com.tikklesaver.domain.Challenge.repository.missionProof;

import com.tikklesaver.domain.Challenge.entity.MissionProof;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionProofRepository extends JpaRepository<MissionProof, Long>, MissionProofRepositoryCustom {
}
