package com.example.umcmatchingcenter.service.matchingService;

import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.enums.ProjectStatus;
import com.example.umcmatchingcenter.repository.MatchingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MatchingQueryServiceImpl implements MatchingQueryService {

    private static final int PAGING_SIZE = 15;

    private final MatchingRepository matchingRepository;

    @Override
    public Optional<Project> findProject(Long id) {
        return matchingRepository.findById(id);
    }

    @Override
    public List<Project> getProjectList(ProjectStatus status, Integer page) {
        try {
            return matchingRepository.findAllByStatusOrderByCreatedAt(status, PageRequest.of(page, PAGING_SIZE)).getContent();
        } catch (Exception e) {
            throw new RuntimeException("프로젝트 목록을 가져오는 중 오류가 발생했습니다", e);
        }
    }

    @Override
    public Project getProjectDetail(Long projectId) {
        return matchingRepository.findById(projectId).orElse(null);
    }

//    @Override
//    public Project getProjectDetail(Long projectId) {
//        return projectRepository.getProjectDetail(projectId);
//    }

    @Override
    public boolean existProject(Long projectId) {
        return matchingRepository.existsById(projectId);
    }

}