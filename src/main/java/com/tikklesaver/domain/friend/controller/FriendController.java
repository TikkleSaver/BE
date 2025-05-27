package com.tikklesaver.domain.friend.controller;

import com.tikklesaver.domain.Challenge.entity.Challenge;
import com.tikklesaver.domain.friend.converter.FriendConverter;
import com.tikklesaver.domain.friend.dto.FriendResponseDto;
import com.tikklesaver.domain.friend.entity.Friend;
import com.tikklesaver.domain.friend.service.FriendService;
import com.tikklesaver.domain.friendRequest.converter.FriendReqConverter;
import com.tikklesaver.domain.friendRequest.dto.FriendReqResponseDto;
import com.tikklesaver.domain.friendRequest.entity.FriendRequest;
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
@RequestMapping("/api/friends")
public class FriendController {
    private final MemberCommandService memberCommandService;
    private final FriendService friendService;
    //유저 정보 조회
    @GetMapping("")
    public ApiResponse<FriendResponseDto.FriendDTOList> getFriendList(@CurrentMember Member member) throws Exception{
        List<FriendResponseDto.FriendDTO> friendRequests = friendService.getFriends(member);
        return ApiResponse.onSuccess(FriendConverter.tofriendDTOList(member, friendRequests));
    }
}
