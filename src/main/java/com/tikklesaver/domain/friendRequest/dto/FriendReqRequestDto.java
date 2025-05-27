package com.tikklesaver.domain.friendRequest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class FriendReqRequestDto
{
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestFriendDTO {
        @NotNull
        private Long receiverId;
    }
}
