package com.example.umcmatchingcenter.repository;

import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.enums.MemberPart;
import com.example.umcmatchingcenter.domain.enums.RecruitmentStatus;
import com.example.umcmatchingcenter.domain.mapping.Recruitment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {

    Optional<Recruitment> findByPartAndProject(MemberPart part, Project project);

    List<Recruitment> findByProjectAndRecruitmentStatus(Project project, RecruitmentStatus recruitmentStatus);
}
