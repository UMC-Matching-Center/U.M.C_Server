package com.example.umcmatchingcenter.converter.myProject;

import com.example.umcmatchingcenter.domain.LandingPage;
import com.example.umcmatchingcenter.dto.ProjectDTO.*;

import java.util.List;

public class MyProjectConverter {

    public static MyProjectResponseDTO toMyProjectResponseDto(TotalMatchingResponseDTO totalMatchingResponseDTO,
                                                              List<PartMatchingResponseDTO> partMatchingDTO,
                                                              double competitionRate,
                                                              List<ApplicantInfoResponseDTO> applicantInfoDTO) {

        return MyProjectResponseDTO.builder()
                .totalMatchingResponseDTO(totalMatchingResponseDTO)
                .partMatchingDTO(partMatchingDTO)
                .competitionRate(competitionRate)
                .applicantInfoList(applicantInfoDTO)
                .build();
    }

    public LandingPage toLandingPage(MyProjectRequestDTO.AddLandingPageRequestDTO request){
        return LandingPage.builder()
                .title(request.getTitle())
                .introduction(request.getIntroduction())
                .body(request.getBody())
                .build();
    }
}
