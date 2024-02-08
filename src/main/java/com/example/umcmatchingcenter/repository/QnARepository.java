package com.example.umcmatchingcenter.repository;

import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.QnA;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnARepository extends JpaRepository<QnA, Long> {
    QnA findQnAByProject(Project project);

    List<QnA> findAllByProjectOrderByCreatedAt(Project project);
}
