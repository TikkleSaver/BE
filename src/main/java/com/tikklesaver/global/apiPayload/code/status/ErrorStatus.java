package com.tikklesaver.global.apiPayload.code.status;

import com.tikklesaver.global.apiPayload.code.BaseErrorCode;
import com.tikklesaver.global.apiPayload.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 일반 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    // 테스트
    TEMP_EXCEPTION(HttpStatus.BAD_REQUEST, "TEMP4001", "이거는 테스트"),

    // 위시
    WISH_NOT_FOUND(HttpStatus.NOT_FOUND, "WISH4001", "존재하지 않는 위시입니다."),


    //지출
    EXPENSE_NOT_FOUND(HttpStatus.NOT_FOUND, "EXPENSE4001", "ID에 해당하는 지출이 없습니다."),
    EXPENSE_AND_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "EXPENSE4002", "멤버ID와 지출ID를 모두 만족하는 지출이 없습니다."),

    //챌린지
    TITLE_NOT_PROVIDED(HttpStatus.BAD_REQUEST, "POST4001", "제목은 필수 입력 사항입니다."),
    DESCRIPTION_NOT_PROVIDED(HttpStatus.BAD_REQUEST, "POST4002", "소개는 필수 입력 사항입니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}