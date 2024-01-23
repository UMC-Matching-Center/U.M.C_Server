package com.example.umcmatchingcenter.service.MatchingService;

import com.example.umcmatchingcenter.domain.Branch;
import com.example.umcmatchingcenter.domain.MatchingSchedule;
import com.example.umcmatchingcenter.dto.MatchingDTO.MatchingScheduleRequestDTO;
import org.springframework.transaction.annotation.Transactional;

public interface MatchingScheduleCommandService {
    @Transactional
    public abstract MatchingSchedule postSchedule(MatchingScheduleRequestDTO.MatchingScheduleDTO request, Branch branch);

    @Transactional
    public abstract MatchingSchedule updateSchedule(Long scheduleId, MatchingScheduleRequestDTO.MatchingScheduleDTO request, Branch branch);
}
