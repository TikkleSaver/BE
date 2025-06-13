package com.tikklesaver.domain.wish.service.wish;

import com.tikklesaver.domain.friend.entity.Friend;
import com.tikklesaver.domain.friend.repository.FriendRepository;
import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.member.repository.MemberRepository;
import com.tikklesaver.domain.wish.dto.wish.WishResponseDTO;
import com.tikklesaver.domain.wish.repository.wish.WishRepository;
import com.tikklesaver.global.apiPayload.code.status.ErrorStatus;
import com.tikklesaver.global.apiPayload.exception.handler.WishHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishQueryServiceImpl implements WishQueryService {

    private final MemberRepository memberRepository;
    private final FriendRepository friendRepository;
    private final WishRepository wishRepository;

    // 위시리스트 상세 조회
    @Override
    public WishResponseDTO.WishDetailDTO getWishDetail(Long wishId, Member member){
        return wishRepository.getWishDetail(wishId, member);
    }

    // 나의 위시리스트 목록 구매 예정 조회
    @Override
    public List<WishResponseDTO.MyWishPlannedPreviewDTO> getMyWishPlannedList(Member member){

        return wishRepository.getMyWishPlannedList(member);
    }

    // 나의 위시리스트 목록 구매 완료 조회
    @Override
    public List<WishResponseDTO.MyWishPurchasedPreviewDTO> getMyWishPurchasedList(Member member){

        return wishRepository.getMyWishPurchasedList(member);
    }


    // 친구의 위시리스트 목록 구매 예정 조회
    @Override
    public List<WishResponseDTO.FriendWishPlannedPreviewDTO> getFriendWishPlannedList(Member member, Long friendId){

        Member friend = memberRepository.findById(friendId)
                .orElseThrow(() -> new WishHandler(ErrorStatus.MEMBER_NOT_FOUND));

        return wishRepository.getFriendWishPlannedList(friend);
    }


    // 친구의 위시리스트 목록 구매 완료 조회
    @Override
    public List<WishResponseDTO.FriendWishPurchasedPreviewDTO> getFriendWishPurchasedList(Member member, Long friendId){

        Member friend = memberRepository.findById(friendId)
                .orElseThrow(() -> new WishHandler(ErrorStatus.MEMBER_NOT_FOUND));

        return wishRepository.getFriendWishPurchasedList(friend);
    }

    // 나와 친구의 위시리스트 목록 조회
    @Override
    public List<WishResponseDTO.WishPreviewDTO> getWishList(Member member){

        List<Friend> friend1List = friendRepository.findAllByMember1(member);
        List<Friend> friend2List = friendRepository.findAllByMember2(member);

        List<Long> memberIdList = friend1List.stream()
                .map(friend -> friend.getMember2().getId())
                .collect(java.util.stream.Collectors.toList());

        memberIdList.addAll(friend2List.stream()
                .map(friend -> friend.getMember1().getId())
                .toList());

        memberIdList.add(member.getId());

        return wishRepository.getWishList(memberIdList, member);
    }
}
