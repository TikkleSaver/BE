package com.tikklesaver.domain.friendRequest.service;

import com.tikklesaver.domain.friendRequest.entity.FriendRequest;
import com.tikklesaver.domain.friendRequest.entity.enums.RequestStatus;
import com.tikklesaver.domain.friendRequest.repository.FriendReqRepository;
import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.member.repository.MemberRepository;
import com.tikklesaver.global.apiPayload.code.status.ErrorStatus;
import com.tikklesaver.global.apiPayload.exception.handler.FriendRequestHandler;
import com.tikklesaver.global.apiPayload.exception.handler.MemberHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class FriendRequestServiceImpl implements FriendRequestService {
    private final MemberRepository memberRepository;
    private final FriendReqRepository friendRequestRepository;

    @Override
    public void addFriendRequest(Member sender, Long receiverId) {
        if (sender.getId().equals(receiverId)) {
            throw new FriendRequestHandler(ErrorStatus.FRIEND_REQ_PERMISSION_DENIED);
        }

        Member receiver = memberRepository.findById(receiverId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        boolean exists = friendRequestRepository.existsBySenderAndReceiver(sender, receiver);
        if (exists) {
            throw new FriendRequestHandler(ErrorStatus.FRIEND_REQ_ALREADY_EXISTS);
        }

        FriendRequest request = FriendRequest.builder()
                .sender(sender)
                .receiver(receiver)
                .status(RequestStatus.PENDING)
                .build();

        friendRequestRepository.save(request);
    }

}


