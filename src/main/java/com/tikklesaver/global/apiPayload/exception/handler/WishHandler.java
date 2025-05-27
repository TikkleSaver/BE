package com.tikklesaver.global.apiPayload.exception.handler;

import com.tikklesaver.global.apiPayload.code.BaseErrorCode;
import com.tikklesaver.global.apiPayload.exception.GeneralException;

public class WishHandler extends GeneralException {
    public WishHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
