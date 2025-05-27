package com.tikklesaver.global.apiPayload.exception.handler;

import com.tikklesaver.global.apiPayload.code.BaseErrorCode;
import com.tikklesaver.global.apiPayload.exception.GeneralException;

public class FriendRequestHandler extends GeneralException {
    public FriendRequestHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}

