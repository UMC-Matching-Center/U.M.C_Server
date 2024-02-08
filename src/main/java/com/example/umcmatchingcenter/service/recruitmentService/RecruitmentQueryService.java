package com.example.umcmatchingcenter.service.recruitmentService;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.RecruitmentHandler;
import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.enums.MemberPart;
import com.example.umcmatchingcenter.domain.mapping.Recruitment;
import com.example.umcmatchingcenter.repository.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecruitmentQueryService {
    private final RecruitmentRepository recruitmentRepository;

    public Recruitment getRecruitment(MemberPart part, Project project) {
        return recruitmentRepository.findByPartAndProject(part, project)
                .orElseThrow(() -> new RecruitmentHandler(ErrorStatus.RECRUITMENT_NOT_FOUNT));
    }

}
