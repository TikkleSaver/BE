package com.tikklesaver.domain.Challenge.dto.missionProof;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;


public class MissionProofRequestDTO {

    @Getter
    public static class CreateMissionDTO {

        @NotEmpty
        @Column(nullable = false, length = 30)
        String content;

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        Date expenseDate;


    }
}
