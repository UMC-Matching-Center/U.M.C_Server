package com.example.umcmatchingcenter.service;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.UniversityHandler;
import com.example.umcmatchingcenter.domain.University;
import com.example.umcmatchingcenter.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class UniversityQueryService {

    private final UniversityRepository universityRepository;

    public University findById(Long id){
        return universityRepository.findById(id)
                .orElseThrow(() -> new UniversityHandler(ErrorStatus.BRANCH_NOT_FOUND));
    }
}
