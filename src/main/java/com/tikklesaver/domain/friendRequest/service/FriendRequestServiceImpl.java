package com.tikklesaver.domain.friendRequest.service;

import com.tikklesaver.domain.friend.entity.Friend;
import com.tikklesaver.domain.friend.repository.FriendRepository;
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
    private final FriendRepository friendRepository;

    @Override
    public void addFriendRequest(Member sender, Long receiverId) {
        if (sender.getId().equals(receiverId)) {
            throw new FriendRequestHandler(ErrorStatus.FRIEND_REQ_PERMISSION_DENIED);
        }

        Member receiver = memberRepository.findById(receiverId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        //친구 관계 확인
        boolean alreadyFriends = friendRepository.existsByMember1AndMember2(
                sender.getId() < receiver.getId() ? sender : receiver,
                sender.getId() < receiver.getId() ? receiver : sender
        );

        if (alreadyFriends) {
            throw new FriendRequestHandler(ErrorStatus.FRIEND_ALREADY_EXISTS);
        }

        // 기존 요청 있는지 확인
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

    @Override
    public void addFriend(Member member, Long id) {
        // 요청 ID로 FriendRequest 조회
        FriendRequest request = friendRequestRepository.findById(id)
                .orElseThrow(() -> new FriendRequestHandler(ErrorStatus.FRIEND_REQ_NOT_FOUND));

        // 사용자가 친구 요청의 수신자가 맞는지 확인
        if (!request.getReceiver().getId().equals(member.getId())) {
            throw new FriendRequestHandler(ErrorStatus.FRIEND_REQ_PERMISSION_DENIED);
        }

        //요청 기록은 삭제됨
        friendRequestRepository.delete(request);
        
        // Friend 엔티티 생성 (작은 id를 member1으로)
        Member sender = request.getSender();
        Member receiver = request.getReceiver();

        Member member1 = sender.getId() < receiver.getId() ? sender : receiver;
        Member member2 = sender.getId() < receiver.getId() ? receiver : sender;

        Friend friend = Friend.builder()
                .member1(member1)
                .member2(member2)
                .build();

        friendRepository.save(friend);
    }

    @Override
    public String deleteFriendRequest(Member member, Long id) {
        // 요청 ID로 FriendRequest 조회
        FriendRequest request = friendRequestRepository.findById(id)
                .orElseThrow(() -> new FriendRequestHandler(ErrorStatus.FRIEND_REQ_NOT_FOUND));

        // 사용자가 친구 요청의 수신자나 발신자가 맞는지 확인
        String message;
        if (request.getReceiver().getId().equals(member.getId())) {//수신자면 거절
            message = "요청이 거절되었습니다.";
        }else if(request.getSender().getId().equals(member.getId())) {//발신자면 삭제
            message = "요청이 취소되었습니다.";
        }else{
            throw new FriendRequestHandler(ErrorStatus.FRIEND_REQ_PERMISSION_DENIED);
        }

        //요청 기록 삭제
        friendRequestRepository.delete(request);

        return message;
    }

}


