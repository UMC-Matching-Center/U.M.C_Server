package com.example.umcmatchingcenter.converter;


import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.University;
import com.example.umcmatchingcenter.domain.mapping.ProjectVolunteer;
import com.example.umcmatchingcenter.dto.MemberDTO.LoginResponseDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.LoginResponseDTO.RenewalAccessTokenResponseDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberRequestDTO.JoinDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO.AcceptResultDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO.ChallengerInfoDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO.DepartResultDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO.JoinResultDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO.MyInfoDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO.RejectResultDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO.SignUpRequestMemberDTO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



public class MemberConverter {


    public static LoginResponseDTO toLoginResponseDto(String memberName, String accessToken, String refreshToken, String memberRole){
        return LoginResponseDTO.builder()
                .memberRole(memberRole)
                .memberName(memberName)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();


    }
    public static JoinResultDTO toJoinResultDTO(Member member){
        return JoinResultDTO.builder()
                .id(member.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }
    public static Member toMember(JoinDTO request, University university){
        return Member.builder()
                .memberName(request.getMemberName())
                .email(request.getEmail())
                .password(request.getPassword())
                .nameNickname(request.getNameNickname())
                .university(university)
                .phoneNumber(request.getPhoneNumber())
                .part(request.getPart())
                .generation(request.getGeneration())
                .portfolio(request.getPortfolio())
                .build();
    }

    public static MyInfoDTO toMyInfoDTO(Member member) {
        return MyInfoDTO.builder()
                .name(member.getNameNickname())
                .generation(member.getGeneration())
                .profileImage(member.getProfileImage())
                .email(member.getEmail())
                .universityName(member.getUniversity().getName())
                .part(member.getPart().name())
                .phoneNumber(member.getPhoneNumber())
                .portfolio(member.getPortfolio())
                .branch(member.getUniversity().getBranch().getName())
                .build();
    }

    public static ChallengerInfoDTO toChallengerInfoDTO(Member member){
        return ChallengerInfoDTO.builder()
                .name(member.getMemberName())
                .generation(member.getGeneration())
                .nameNickname(member.getNameNickname())
                .part(member.getPart().toString())
                .matchCount(member.getProjectVolunteerList().stream()
                        .map(ProjectVolunteer::getRound)
                        .max(Comparator.naturalOrder())
                        .orElse(0))
                .matchingStatus(member.getMatchingStatus().toString())
                .build();
    }


    public static DepartResultDTO toDepartResultDTO(Member member){
        return DepartResultDTO.builder()
                .memberId(member.getId())
                .nameNickname(member.getNameNickname())
                .build();
    }

    public static SignUpRequestMemberDTO toSignUpRequestDTO(Member member){
        return SignUpRequestMemberDTO.builder()
                .memberId(member.getId())
                .generation(member.getGeneration())
                .nameNickname(member.getNameNickname())
                .part(member.getPart().name())
                .build();
    }

    public static AcceptResultDTO toAcceptResultDTO(Member member){
        return AcceptResultDTO.builder()
                .memberId(member.getId())
                .memberStatus(member.getMemberStatus().name())
                .build();
    }

    public static RejectResultDTO toRejectResultDTO(Member member) {
        return RejectResultDTO.builder()
                .memberId(member.getId())
                .memberStatus(member.getMemberStatus().name())
                .build();
    }

    public static RenewalAccessTokenResponseDTO toRenewalAccessTokenResponseDTO(String memberName, String memberRole, String newAccessToken){
        return RenewalAccessTokenResponseDTO.builder()
                .memberName(memberName)
                .memberRole(memberRole)
                .accessToken(newAccessToken)
                .build();
    }
}
