package com.example.umcmatchingcenter.service.memberService;


import static com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus.*;
import static com.example.umcmatchingcenter.domain.enums.MemberRole.*;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.MemberHandler;
import com.example.umcmatchingcenter.converter.MemberConverter;
import com.example.umcmatchingcenter.domain.Branch;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.University;
import com.example.umcmatchingcenter.domain.enums.MemberMatchingStatus;
import com.example.umcmatchingcenter.domain.enums.MemberPart;
import com.example.umcmatchingcenter.domain.enums.MemberStatus;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO.ChallengerInfoDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO.ApplyTeamDTO;

import com.example.umcmatchingcenter.jwt.SecurityUtil;

import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO.MyInfoDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO.SignUpRequestMemberDTO;

import com.example.umcmatchingcenter.repository.MemberRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.umcmatchingcenter.service.ProjectVolunteerQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryService {

    private final MemberRepository memberRepository;

    public Member findMember(Long id){
        Optional<Member> member = memberRepository.findById(id);
        if (member.isEmpty()){
            throw new MemberHandler(MEMBER_NOT_FOUND);
        }
        return member.get();
    }

    public Member findMemberByName(String name){
        Optional<Member> member = memberRepository.findByMemberName(name);
        if (member.isEmpty()){
            throw new MemberHandler(MEMBER_NOT_FOUND);
        }
        return member.get();
    }

    public MyInfoDTO getMyInfo(String name) {
        Member target = findMemberByName(name);
        return MemberConverter.toMyInfoDTO(target);
    }

    public Member getMember(Long memberId) {
        Optional<Member> foundMember = memberRepository.findById(memberId);
        if (foundMember.isPresent() && foundMember != null) {
            return foundMember.get();
        }
        return null;
    }

    public Member getCurrentLoginMember() {
        String memberName = SecurityUtil.getCurrentMember();
        Optional<Member> foundMember = memberRepository.findByMemberName(memberName);
        if (foundMember.isPresent() && foundMember != null) {
            return foundMember.get();
        }
        return null;
    }

    public List<Member> getNonMatchingMember(Branch branch, MemberPart part) {
        List<University> universities = branch.getUniversities();


        List<MemberMatchingStatus> matchingStatuses = Arrays.asList(MemberMatchingStatus.NON, MemberMatchingStatus.APPLY);

        return memberRepository.findByUniversityInAndMatchingStatusInAndPartAndMemberStatus(universities, matchingStatuses, part, MemberStatus.ACTIVE);
    }
}
