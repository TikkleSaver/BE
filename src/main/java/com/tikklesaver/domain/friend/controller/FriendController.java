package com.tikklesaver.domain.friend.controller;

import com.tikklesaver.domain.Challenge.entity.Challenge;
import com.tikklesaver.domain.member.converter.MemberConverter;
import com.tikklesaver.domain.member.dto.*;
import com.tikklesaver.domain.member.entity.Member;
import com.tikklesaver.domain.member.service.MemberCommandService;
import com.tikklesaver.global.annotation.CurrentMember;
import com.tikklesaver.global.apiPayload.ApiResponse;
import com.tikklesaver.global.jwt.dto.JwtDto;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/friends")
public class FriendController {

}
