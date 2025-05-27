package com.tikklesaver.global.apiPayload.exception.handler;

import com.tikklesaver.global.apiPayload.code.BaseErrorCode;
import com.tikklesaver.global.apiPayload.exception.GeneralException;

public class VoteHandler extends GeneralException {
    public VoteHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
