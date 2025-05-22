package com.tikklesaver.domain.member.dto;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class CustomUserInfoDto  {

    private Long id;
    private String loginId;
    private String password;
    private String nickname;
    private String role;
//    private RoleType role
}