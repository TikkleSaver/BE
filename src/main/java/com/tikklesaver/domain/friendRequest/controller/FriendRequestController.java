package com.tikklesaver.domain.friendRequest.controller;

import com.tikklesaver.domain.Challenge.entity.Challenge;
import com.tikklesaver.domain.friendRequest.converter.FriendReqConverter;
import com.tikklesaver.domain.friendRequest.dto.FriendReqRequestDto;
import com.tikklesaver.domain.friendRequest.dto.FriendReqResponseDto;
import com.tikklesaver.domain.friendRequest.entity.FriendRequest;
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

    //친구 요청
    @PostMapping("")
    public ApiResponse<String> requestFriend(@CurrentMember Member member, @RequestBody @Valid FriendReqRequestDto.RequestFriendDTO requestDTO) throws Exception {
        friendRequestService.addFriendRequest(member, requestDTO.getReceiverId());
        return ApiResponse.onSuccess("친구 요청 성공");
    }

    //친구 요청 수락
    @PatchMapping("/{id}")
    public ApiResponse<String> acceptRequest(@CurrentMember Member member, @PathVariable(name = "id") Long id) throws Exception {
        friendRequestService.addFriend(member, id);
        return ApiResponse.onSuccess("요청을 수락했습니다.");
    }

    //친구 요청 취소 & 거절
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteRequest(@CurrentMember Member member, @PathVariable(name = "id") Long id) throws Exception {
        return ApiResponse.onSuccess(friendRequestService.deleteFriendRequest(member, id));
    }

    @GetMapping("")
    public ApiResponse<FriendReqResponseDto.FriendReqDTOList> getFriendRequests(@CurrentMember Member member) throws Exception {
        List<FriendRequest> friendRequests = friendRequestService.getFriendRequests(member);
        return ApiResponse.onSuccess(FriendReqConverter.toReqDTOList(member, friendRequests));
    }
}
