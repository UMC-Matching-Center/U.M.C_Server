package com.example.umcmatchingcenter.apiPayload.exception.handler;

import com.example.umcmatchingcenter.apiPayload.code.BaseErrorCode;
import com.example.umcmatchingcenter.apiPayload.exception.GeneralException;


public class QnAHandler extends GeneralException {
    public QnAHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
