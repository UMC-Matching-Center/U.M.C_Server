package com.example.umcmatchingcenter.dto.ProjectDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyProjectResponseDTO {

    private TotalMatchingResponseDTO totalMatchingResponseDTO;
    private List<PartMatchingResponseDTO> partMatchingDTO;
    private double competitionRate;
    private List<ApplicantInfoResponseDTO> applicantInfoList;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddLandingPageResponseDTO {
        private Long Id;
        private String title;
        private LocalDateTime createdAt;
    }

}
