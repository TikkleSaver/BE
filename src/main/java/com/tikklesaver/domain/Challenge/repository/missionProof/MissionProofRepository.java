package com.tikklesaver.domain.Challenge.repository.missionProof;

import com.tikklesaver.domain.Challenge.dto.missionProof.MissionProofResponseDTO;
import com.tikklesaver.domain.Challenge.entity.Challenge;
import com.tikklesaver.domain.Challenge.entity.MissionProof;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MissionProofRepository extends JpaRepository<MissionProof, Long>, MissionProofRepositoryCustom {

}
