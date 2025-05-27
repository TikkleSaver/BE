package com.tikklesaver.global.apiPayload.exception.handler;

import com.tikklesaver.global.apiPayload.code.BaseErrorCode;
import com.tikklesaver.global.apiPayload.exception.GeneralException;

public class WishCommentHandler extends GeneralException {
    public WishCommentHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
