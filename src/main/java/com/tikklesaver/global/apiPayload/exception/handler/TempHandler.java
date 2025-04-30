package com.tikklesaver.global.apiPayload.exception.handler;


import com.tikklesaver.global.apiPayload.code.BaseErrorCode;
import com.tikklesaver.global.apiPayload.exception.GeneralException;

public class TempHandler extends GeneralException {

    public TempHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}