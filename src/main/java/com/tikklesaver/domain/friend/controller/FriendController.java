package com.tikklesaver.domain.friend.controller;

import com.tikklesaver.domain.Challenge.entity.Challenge;
import com.tikklesaver.domain.friend.converter.FriendConverter;
import com.tikklesaver.domain.friend.dto.FriendResponseDto;
import com.tikklesaver.domain.friend.service.FriendService;
import com.tikklesaver.domain.friendRequest.repository.FriendReqRepository;
import com.tikklesaver.domain.friendRequest.service.FriendRequestService;
import com.tikklesaver.domain.member.converter.MemberConverter;
import com.tikklesaver.domain.member.dto.MemberResponseDto;
import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.member.repository.MemberRepository;
import com.tikklesaver.domain.member.service.MemberCommandService;
import com.tikklesaver.global.annotation.CurrentMember;
import com.tikklesaver.global.apiPayload.ApiResponse;
import com.tikklesaver.global.apiPayload.code.status.ErrorStatus;
import com.tikklesaver.global.apiPayload.exception.handler.MemberHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/friends")
public class FriendController {
    private final FriendRequestService friendRequestService;
    private final FriendService friendService;
    private final MemberCommandService memberCommandService;
    private final MemberRepository memberRepository;

    //친구 리스트 조회
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

    //다른 사용자 ( 친구/친구X) 프로필 조회
    @GetMapping("/{id}")
    public ApiResponse<FriendResponseDto.FriendProfileDTO> getProfile(@CurrentMember Member member, @PathVariable(name = "id") Long userId) throws Exception {
        int wishListNum = memberCommandService.getWishListCount(userId);
        int challengeNum = memberCommandService.getChallengeCount(userId);
        int friendNum = memberCommandService.getFriendCount(userId);
        List<Challenge> challengeScrapedList = memberCommandService.getScrappedChallenges(userId);

        Long friendId = friendService.getFriendId(member, userId);

        FriendResponseDto.FriendReqDTO friendReqInfo = null;

        if(friendId == 0){
            friendReqInfo = friendRequestService.getFriendRequest(member, userId);
        }

        Member user = memberRepository.findById(userId) .
                orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        return ApiResponse.onSuccess(
                FriendConverter.toFriendProfileDTO(user, wishListNum,
                        challengeNum, friendNum, challengeScrapedList, friendId, friendReqInfo ));
    }
    
    
}
