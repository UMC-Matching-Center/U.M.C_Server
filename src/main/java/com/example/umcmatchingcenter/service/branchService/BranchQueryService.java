package com.example.umcmatchingcenter.service.branchService;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.MemberHandler;
import com.example.umcmatchingcenter.domain.Branch;
import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.enums.RecruitmentStatus;
import com.example.umcmatchingcenter.domain.mapping.Recruitment;
import com.example.umcmatchingcenter.repository.BranchRepository;
import com.example.umcmatchingcenter.repository.RecruitmentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BranchQueryService {

    private final BranchRepository branchRepository;
    private final RecruitmentRepository recruitmentRepository;

    public Branch findBranchByName(String name){
        return branchRepository.findByName(name)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.BRANCH_NOT_FOUND));
    }

    public List<Branch> getGenerationBranch(int generation){
        return branchRepository.findAllByGeneration(generation);
    }

    public List<Recruitment> getBranchRecruitments(Branch branch){
        List<Project> projects = branch.getProjects();
        return projects.stream()
                .flatMap(project ->
                        recruitmentRepository.findByProjectAndRecruitmentStatus(project, RecruitmentStatus.RECRUITING).stream())
                .toList();
    }
}