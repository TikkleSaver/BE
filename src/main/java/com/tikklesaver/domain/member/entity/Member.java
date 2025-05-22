package com.tikklesaver.domain.member.entity;

import com.tikklesaver.domain.member.entity.enums.MemberStatus;
import com.tikklesaver.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 로그인 아이디
    @Column(nullable = false)
    private String loginId;
    
    // 비밀번호
    @Column(nullable = false)
    private String password;
    
    // 닉네임
    @Column(nullable = false)
    private String nickname;
    
    // 하루 지출 목표 금액
    private String goalCost;
    
    // 프로필 이미지
    private String profileUrl;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(15) DEFAULT 'ACTIVE'")
    private MemberStatus status;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<MemberCategory> memberCategories;

    private String refreshToken; // 리프레시 토큰

    // 비밀번호 암호화 메소드
    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public void updatePassword(PasswordEncoder passwordEncoder, String password){
        this.password = passwordEncoder.encode(password);
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }

}
