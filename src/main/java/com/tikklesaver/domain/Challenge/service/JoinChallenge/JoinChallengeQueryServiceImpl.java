package com.tikklesaver.domain.Challenge.service.JoinChallenge;


import com.tikklesaver.domain.Challenge.entity.JoinChallenge;
import com.tikklesaver.domain.Challenge.entity.enums.Status;
import com.tikklesaver.domain.Challenge.repository.JoinChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JoinChallengeQueryServiceImpl implements JoinChallengeQueryService {

    private final JoinChallengeRepository joinChallengeRepository;

    @Override
    public Page<JoinChallenge> getChallengeMembers(Long challengeId, Integer page) {

        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.DESC, "createdAt"));

        return joinChallengeRepository.findByChallengeIdAndStatus(challengeId, Status.JOINED, pageRequest);


    }

    @Override
    public Page<JoinChallenge> getChallengeRequestMembers(Long challengeId, Integer page) {

        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.DESC, "createdAt"));

        return joinChallengeRepository.findByChallengeIdAndStatus(challengeId, Status.PENDING, pageRequest);


    }
}
