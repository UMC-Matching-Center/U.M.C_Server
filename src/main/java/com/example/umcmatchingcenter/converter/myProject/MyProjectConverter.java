package com.example.umcmatchingcenter.converter.myProject;

import com.example.umcmatchingcenter.domain.LandingPage;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.dto.ProjectDTO.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public static LandingPage toLandingPage(MyProjectRequestDTO.AddLandingPageRequestDTO request, Project project){
        return LandingPage.builder()
                .title(request.getTitle())
                .introduction(request.getIntroduction())
                .body(request.getBody())
                .project(project)
                .build();
    }

    public static MyProjectResponseDTO.LandingPageResponseDTO toAddLandingPageResponseDTO(LandingPage landingPage){
        return MyProjectResponseDTO.LandingPageResponseDTO.builder()
                .Id(landingPage.getId())
                .title(landingPage.getTitle())
                .createdAt(landingPage.getCreatedAt())
                .build();
    }

    public static MyProjectResponseDTO.LandingPageDetailsResponseDTO toLandingPageDetailsResponseDTO(LandingPage landingPage, Map<Long, String> images, List<Member> memberList){
        List<MyProjectResponseDTO.LandingPageMemberListDTO> LandingPageMemberList = memberList.stream()
                .map(MyProjectConverter::toLandingPageMemberListDTO).collect(Collectors.toList());

        return MyProjectResponseDTO.LandingPageDetailsResponseDTO.builder()
                .landingPageId(landingPage.getId())
                .profileImageId(landingPage.getProfileImage().getId())
                .profileImageUrl(landingPage.getProfileImage().getS3ImageUrl())
                .title(landingPage.getTitle())
                .introduction(landingPage.getIntroduction())
                .body(landingPage.getBody())
                .Images(images)
                .memberList(LandingPageMemberList)
                .build();
    }

    public static MyProjectResponseDTO.LandingPageMemberListDTO toLandingPageMemberListDTO(Member member){
        return MyProjectResponseDTO.LandingPageMemberListDTO.builder()
                .profileImage(member.getProfileImage())
                .nameNickname(member.getNameNickname())
                .memberPart(member.getPart())
                .university(member.getUniversity().getName())
                .generation(member.getGeneration())
                .build();
    }
}
