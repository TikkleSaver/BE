package com.tikklesaver.domain.Challenge.dto.challenge;

import com.tikklesaver.domain.Challenge.entity.enums.PublicStatus;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

public class ChallengeRequestDTO {

    @Getter
    public static class CreateChallengeDTO {

        @NotEmpty
        @Column(nullable = false, length = 100)
        String title;
        @NotEmpty
        @Column(nullable = false, length = 4000)
        String description;
        @NotNull
        Long categoryId;
        @NotNull
        PublicStatus publicStatus;
        @NotNull
        List<String> missionMethods;



    }

}
