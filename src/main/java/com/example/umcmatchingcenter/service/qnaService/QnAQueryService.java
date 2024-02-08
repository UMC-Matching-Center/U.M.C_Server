package com.example.umcmatchingcenter.service.qnaService;

import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.QnA;

import java.util.List;

public interface QnAQueryService {
    List<QnA> getQnAList(Project project);
}
