package com.tikklesaver.domain.friend.controller;

import com.tikklesaver.domain.friend.converter.FriendConverter;
import com.tikklesaver.domain.friend.dto.FriendResponseDto;
import com.tikklesaver.domain.friend.service.FriendService;
import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.global.annotation.CurrentMember;
import com.tikklesaver.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/friends")
public class FriendController {
    private final FriendService friendService;
    //유저 정보 조회
    @GetMapping("")
    public ApiResponse<FriendResponseDto.FriendDTOList> getFriendList(@CurrentMember Member member) throws Exception{
        List<FriendResponseDto.FriendDTO> friendRequests = friendService.getFriends(member);
        return ApiResponse.onSuccess(FriendConverter.tofriendDTOList(member, friendRequests));
    }

    //친구 삭제
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteRequest(@CurrentMember Member member, @PathVariable(name = "id") Long id) throws Exception {
        friendService.deleteFriend(member, id);
        return ApiResponse.onSuccess("친구에서 삭제되었습니다.");
    }
}
