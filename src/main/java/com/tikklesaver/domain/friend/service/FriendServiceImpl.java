package com.tikklesaver.domain.friend.service;

import com.tikklesaver.domain.friend.dto.FriendResponseDto;
import com.tikklesaver.domain.friend.entity.Friend;
import com.tikklesaver.domain.friend.repository.FriendRepository;
import com.tikklesaver.domain.friendRequest.entity.FriendRequest;
import com.tikklesaver.domain.friendRequest.entity.enums.RequestStatus;
import com.tikklesaver.domain.friendRequest.repository.FriendReqRepository;
import com.tikklesaver.domain.friendRequest.service.FriendRequestService;
import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.member.repository.MemberRepository;
import com.tikklesaver.global.apiPayload.code.status.ErrorStatus;
import com.tikklesaver.global.apiPayload.exception.handler.FriendRequestHandler;
import com.tikklesaver.global.apiPayload.exception.handler.MemberHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class FriendServiceImpl implements FriendService {
    private final MemberRepository memberRepository;
    private final FriendReqRepository friendRequestRepository;
    private final FriendRepository friendRepository;


    @Override
    public List<FriendResponseDto.FriendDTO> getFriends(Member member) {
        return friendRepository.findAllByMemberId(member.getId());
    }
}


