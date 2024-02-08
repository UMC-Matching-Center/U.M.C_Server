package com.example.umcmatchingcenter.service.matchingService;

import com.example.umcmatchingcenter.domain.Branch;
import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.enums.ProjectStatus;

import java.util.List;
import java.util.Optional;

public interface MatchingQueryService {
    Project findProject(Long id);

    List<Project> getProjectList(Branch branch, ProjectStatus status, Integer page);

    // TODO: 작성자(pm)가 나인지 확인
    Project getProjectDetail(Long projectId);

    boolean existProject(Long projectId);
}
