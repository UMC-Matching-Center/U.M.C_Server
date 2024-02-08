package com.example.umcmatchingcenter.service.qnaService;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.ProjectHandler;
import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.QnA;
import com.example.umcmatchingcenter.repository.QnARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QnAQueryServiceImpl implements QnAQueryService {

    private final QnARepository qnaRepository;

    @Override
    public List<QnA> getQnAList(Project project) {
        try {
            return qnaRepository.findAllByProjectOrderByCreatedAt(project);
        } catch (Exception e) {
            throw new ProjectHandler(ErrorStatus.PROJECT_NOT_EXIST);
        }
    }
}
