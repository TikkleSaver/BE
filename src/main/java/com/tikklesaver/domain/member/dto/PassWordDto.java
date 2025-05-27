package com.tikklesaver.domain.member.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PassWordDto {
    @NotNull(message = "비밀번호는 필수입니다.")
    private String password;

    @NotNull(message = "비밀번호는 필수입니다.")
    private String newPassword;
}
