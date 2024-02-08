package com.example.umcmatchingcenter.apiPayload.exception.handler;

import com.example.umcmatchingcenter.apiPayload.code.BaseErrorCode;
import com.example.umcmatchingcenter.apiPayload.exception.GeneralException;

public class RecruitmentHandler extends GeneralException {
    public RecruitmentHandler(BaseErrorCode code) {
        super(code);
    }
}
