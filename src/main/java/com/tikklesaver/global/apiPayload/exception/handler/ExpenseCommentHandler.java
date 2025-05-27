package com.tikklesaver.global.apiPayload.exception.handler;

import com.tikklesaver.global.apiPayload.code.BaseErrorCode;
import com.tikklesaver.global.apiPayload.exception.GeneralException;

public class ExpenseCommentHandler extends GeneralException {
    public ExpenseCommentHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
