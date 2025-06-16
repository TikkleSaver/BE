package com.tikklesaver.domain.Challenge.converter;

import com.tikklesaver.domain.Category.entity.Category;
import com.tikklesaver.domain.Challenge.dto.challenge.ChallengeRequestDTO;
import com.tikklesaver.domain.Challenge.dto.challenge.ChallengeResponseDTO;
import com.tikklesaver.domain.Challenge.dto.challengeScrap.ChallengeScrapResponseDTO;
import com.tikklesaver.domain.Challenge.dto.joinChallenge.JoinChallengeResponseDTO;
import com.tikklesaver.domain.Challenge.dto.missionProof.MissionProofRequestDTO;
import com.tikklesaver.domain.Challenge.dto.missionProof.MissionProofResponseDTO;
import com.tikklesaver.domain.Challenge.entity.Challenge;
import com.tikklesaver.domain.Challenge.entity.JoinChallenge;
import com.tikklesaver.domain.Challenge.entity.MissionProof;
import com.tikklesaver.domain.Challenge.entity.enums.PublicStatus;
import com.tikklesaver.domain.Challenge.entity.enums.Status;
import com.tikklesaver.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ChallengeConverter {

    public static Challenge toChallenge(Member member, ChallengeRequestDTO.CreateChallengeDTO request, Category category, String imageUrl) {
        return Challenge.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .challengeUrl(imageUrl)
                .missionMethods(request.getMissionMethods())
                .category(category)
                .publicStatus(request.getPublicStatus())
                .member(member)
                .build();
    }

    public static ChallengeResponseDTO.challengeResultDTO toChallengeResultDTO(Challenge challenge) {
        return ChallengeResponseDTO.challengeResultDTO.builder()
                .challengeId(challenge.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static ChallengeResponseDTO.ChallengePreViewListDTO challengePreViewListDTO(Page<Challenge> challengeList){
        List<ChallengeResponseDTO.ChallengePreViewDTO> postPreViewDTOList = challengeList.stream().map(ChallengeConverter::challengePreViewDTO).collect(Collectors.toList());

        return ChallengeResponseDTO.ChallengePreViewListDTO.builder()
                .isFirst(challengeList.isFirst())
                .isLast(challengeList.isLast())
                .totalPage(challengeList.getTotalPages())
                .totalElements(challengeList.getTotalElements())
                .listSize(postPreViewDTOList.size())
                .challengeList(postPreViewDTOList)
                .build();
    }

    public static ChallengeResponseDTO.ChallengePreViewDTO challengePreViewDTO(Challenge challenge) {
        return ChallengeResponseDTO.ChallengePreViewDTO.builder()
                .challengeId(challenge.getId())
                .title(challenge.getTitle())
                .categoryId(challenge.getCategory().getId())
                .imgUrl(challenge.getChallengeUrl())
                .createdAt(challenge.getCreatedAt())
                .build();
    }


    public static ChallengeResponseDTO.ChallengePreviewWithStatusResponseDTO challengePreviewWithStatusResponseDTO(Challenge challenge, Status status, boolean isScrapped, Integer challengerCount, List<String> challengerImages) {
        return ChallengeResponseDTO.ChallengePreviewWithStatusResponseDTO.builder()
                .challengeId(challenge.getId())
                .title(challenge.getTitle())
                .description(challenge.getDescription())
                .missionMethods(challenge.getMissionMethods())
                .category(challenge.getCategory().getCategory_name())
                .status(status)
                .leaderId(challenge.getMember().getId())
                .isPublic(challenge.getPublicStatus().toString())
                .isScrapped(isScrapped)
                .challengerCount(challengerCount)
                .challengerImages(challengerImages)
                .imgUrl(challenge.getChallengeUrl())
                .build();

    }


    public static ChallengeScrapResponseDTO challengeScrapResponseDTO(Challenge challenge,Member member,  boolean isScrapped, String message) {
        return ChallengeScrapResponseDTO.builder()
                .challengeId(challenge.getId())
                .memberId(member.getId())
                .isScrapped(isScrapped)
                .message(message)
                .build();
    }

    public static JoinChallenge toJoinPublicChallenge(Member member, Challenge challenge) {
        return JoinChallenge.builder()
                .challenge(challenge)
                .member(member)
                .isLeader(false)
                .status(Status.JOINED)
                .build();
    }

    public static JoinChallenge toJoinPrivateChallenge(Member member, Challenge challenge) {
        return JoinChallenge.builder()
                .challenge(challenge)
                .member(member)
                .isLeader(false)
                .status(Status.PENDING)
                .build();
    }

    public static JoinChallengeResponseDTO toJoinChallengeResultDTO(JoinChallenge joinChallenge) {
        return JoinChallengeResponseDTO.builder()
                .id(joinChallenge.getId())
                .memberId(joinChallenge.getMember().getId())
                .challengeId(joinChallenge.getChallenge().getId())
                .status(joinChallenge.getStatus())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static MissionProof toMissionProof(Member member, Challenge challenge, MissionProofRequestDTO.CreateMissionDTO request, String imageUrl) {
        return MissionProof.builder()
                .member(member)
                .challenge(challenge)
                .content(request.getContent())
                .imageUrl(imageUrl)
                .build();
    }

    public static MissionProofResponseDTO.missionProofResultDTO toMissionProofResultDTO(MissionProof missionProof) {
        return MissionProofResponseDTO.missionProofResultDTO.builder()
                .missionProofId(missionProof.getId())
                .content(missionProof.getContent())
                .imageUrl(missionProof.getImageUrl())
                .createdAt(missionProof.getCreatedAt())
                .build();
    }

    public static ChallengeResponseDTO.DetailChallengerTabListResponseDTO detailChallengerTabListDTO(Page<JoinChallenge> memberList) {
        List<ChallengeResponseDTO.DetailChallengerTabResponseDTO> memberDTOList = memberList.stream().map(ChallengeConverter::detailChallengerTabDTO).collect(Collectors.toList());

        return ChallengeResponseDTO.DetailChallengerTabListResponseDTO.builder()
                .isFirst(memberList.isFirst())
                .isLast(memberList.isLast())
                .totalPage(memberList.getTotalPages())
                .totalElements(memberList.getTotalElements())
                .listSize(memberDTOList.size())
                .memberList(memberDTOList)
                .build();
    }


    public static ChallengeResponseDTO.DetailChallengerTabResponseDTO detailChallengerTabDTO(JoinChallenge joinChallenge) {
        return ChallengeResponseDTO.DetailChallengerTabResponseDTO.builder()
                .id(joinChallenge.getId())
                .challengeId(joinChallenge.getChallenge().getId())
                .memberId(joinChallenge.getMember().getId())
                .memberName(joinChallenge.getMember().getNickname())
                .memberImgUrl(joinChallenge.getMember().getProfileUrl())
                .build();

    }






}
