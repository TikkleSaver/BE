package com.tikklesaver.global.apiPayload.exception.handler;

import com.tikklesaver.global.apiPayload.code.BaseErrorCode;
import com.tikklesaver.global.apiPayload.exception.GeneralException;

public class MissionProofHandler extends GeneralException {
    public MissionProofHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
