package com.tikklesaver.domain.friendRequest.controller;

import com.tikklesaver.domain.Challenge.entity.Challenge;
import com.tikklesaver.domain.friendRequest.dto.FriendReqRequestDto;
import com.tikklesaver.domain.friendRequest.service.FriendRequestService;
import com.tikklesaver.domain.member.converter.MemberConverter;
import com.tikklesaver.domain.member.dto.*;
import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.member.service.MemberCommandService;
import com.tikklesaver.global.annotation.CurrentMember;
import com.tikklesaver.global.apiPayload.ApiResponse;
import com.tikklesaver.global.jwt.dto.JwtDto;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/friend-requests")
public class FriendRequestController {
    private final FriendRequestService friendRequestService;

    @PostMapping("")
    public ApiResponse<String> requestFriend(@CurrentMember Member member, @RequestBody @Valid FriendReqRequestDto.RequestFriendDTO requestDTO) throws Exception {
        friendRequestService.addFriendRequest(member, requestDTO.getReceiverId());
        return ApiResponse.onSuccess("친구 요청 성공");
    }

}
