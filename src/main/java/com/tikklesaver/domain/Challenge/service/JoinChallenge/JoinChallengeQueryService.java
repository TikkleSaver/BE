package com.tikklesaver.domain.Challenge.service.JoinChallenge;


import com.tikklesaver.domain.Challenge.entity.JoinChallenge;
import com.tikklesaver.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JoinChallengeQueryService {

    Page<JoinChallenge> getChallengeMembers(Long challengeId, Integer page);
    Page<JoinChallenge> getChallengeRequestMembers(Long challengeId, Integer page);


}
