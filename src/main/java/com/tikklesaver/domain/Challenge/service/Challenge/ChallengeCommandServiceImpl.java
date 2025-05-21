package com.tikklesaver.domain.Challenge.service.Challenge;

import com.tikklesaver.domain.Category.entity.Category;
import com.tikklesaver.domain.Category.repository.CategoryRepository;
import com.tikklesaver.domain.Challenge.converter.ChallengeConverter;
import com.tikklesaver.domain.Challenge.dto.challenge.ChallengeRequestDTO;
import com.tikklesaver.domain.Challenge.entity.Challenge;
import com.tikklesaver.domain.Challenge.entity.JoinChallenge;
import com.tikklesaver.domain.Challenge.entity.enums.Status;
import com.tikklesaver.domain.Challenge.repository.ChallengeRepository;
import com.tikklesaver.domain.Challenge.repository.JoinChallengeRepository;
import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.member.repository.MemberRepository;
import com.tikklesaver.global.apiPayload.code.status.ErrorStatus;
import com.tikklesaver.global.apiPayload.exception.handler.ChallengeHandler;
import com.tikklesaver.global.aws.s3.AmazonS3Manager;
import com.tikklesaver.global.common.Uuid;
import com.tikklesaver.global.repository.UuidRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChallengeCommandServiceImpl implements ChallengeCommandService {

    private final MemberRepository memberRepository;
    private final ChallengeRepository challengeRepository;
    private final CategoryRepository categoryRepository;
    private final AmazonS3Manager amazonS3Manager;
    private final UuidRepository uuidRepository;
    private final JoinChallengeRepository joinChallengeRepository;

    @Override
    public Challenge createChallenge(Long memberId, ChallengeRequestDTO.CreateChallengeDTO request, MultipartFile file) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 유저를 찾을 수 없습니다. ID: " + memberId));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found ID: " + request.getCategoryId()));

        String imageUrl = null;

        String uuid = UUID.randomUUID().toString();
        Uuid savedUuid = uuidRepository.save(Uuid.builder()
                .uuid(uuid).build());

        if (file != null && !file.isEmpty()) {
            imageUrl = amazonS3Manager.uploadFile(amazonS3Manager.generateChallengesKeyName(savedUuid),file);
        }


        Challenge newChallenge = ChallengeConverter.toChallenge(member, request,category,imageUrl);
        Challenge savedChallenge = challengeRepository.save(newChallenge);


        JoinChallenge leaderJoin = JoinChallenge.builder()
                .challenge(savedChallenge)
                .member(member)
                .status(Status.JOINED)
                .isLeader(true)  // 리더임을 명시
                .build();

        joinChallengeRepository.save(leaderJoin);

        return savedChallenge;

    }
}
