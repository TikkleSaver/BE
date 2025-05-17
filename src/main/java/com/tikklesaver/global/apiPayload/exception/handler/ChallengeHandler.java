package com.tikklesaver.global.apiPayload.exception.handler;

import com.tikklesaver.global.apiPayload.code.BaseErrorCode;
import com.tikklesaver.global.apiPayload.exception.GeneralException;

public class ChallengeHandler extends GeneralException {
    public ChallengeHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }



}
