package com.example.umcmatchingcenter.service.memberService;


import static com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus.*;
import static com.example.umcmatchingcenter.domain.enums.MemberRole.*;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.MemberHandler;
import com.example.umcmatchingcenter.converter.MemberConverter;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.enums.MemberMatchingStatus;
import com.example.umcmatchingcenter.domain.enums.MemberStatus;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO.ChallengerInfoDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO.ApplyTeamDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO.MyInfoDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO.SignUpRequestMemberDTO;
import com.example.umcmatchingcenter.repository.MemberRepository;
import com.example.umcmatchingcenter.service.ProjectVolunteerQueryService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryService {

    private static final int NOW_GENERATION = 5;
    private static final int PAGING_SIZE = 10;

    private final MemberRepository memberRepository;
    private final ProjectVolunteerQueryService projectVolunteerQueryService;

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

    public List<ChallengerInfoDTO> getChallengerList(MemberMatchingStatus memberMatchingStatus, int page) {
        Page<Member> members = memberRepository.findByGenerationAndRoleAndMatchingStatus(NOW_GENERATION, ROLE_CHALLENGER,
                memberMatchingStatus, PageRequest.of(page, PAGING_SIZE));

        return members.stream()
                .map(MemberConverter::toChallengerInfoDTO)
                .toList();
    }

    public List<ApplyTeamDTO> getMatcingRoundList(String name) {
        Member member = findMemberByName(name);

        return projectVolunteerQueryService.getAllApplyTeam(member);
    }


    public List<SignUpRequestMemberDTO> getSignUpRequestList(int page) {
        Page<Member> member = memberRepository.findAllByMemberStatus(MemberStatus.PENDING, PageRequest.of(page, PAGING_SIZE));

        return member.stream()
                .map(MemberConverter::toSignUpRequestDTO)
                .toList();
    }
}
