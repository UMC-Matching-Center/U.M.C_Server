package com.example.umcmatchingcenter.service;

import static com.example.umcmatchingcenter.domain.enums.MemberRole.ROLE_CHALLENGER;
import com.example.umcmatchingcenter.converter.MemberConverter;
import com.example.umcmatchingcenter.domain.Branch;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.University;
import com.example.umcmatchingcenter.domain.enums.MemberMatchingStatus;
import com.example.umcmatchingcenter.domain.enums.MemberStatus;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberRequestDTO.UpdateAdminInfoDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO.AcceptResultDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO.ApplyTeamDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO.ChallengerInfoDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO.RejectResultDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO.SignUpRequestMemberDTO;
import com.example.umcmatchingcenter.repository.MemberRepository;
import com.example.umcmatchingcenter.repository.UniversityRepository;
import com.example.umcmatchingcenter.service.branchService.BranchQueryService;
import com.example.umcmatchingcenter.service.matchingService.MatchingCommandService;
import com.example.umcmatchingcenter.service.memberService.MemberCommandService;
import com.example.umcmatchingcenter.service.memberService.MemberQueryService;
import com.example.umcmatchingcenter.service.s3Service.S3UploadService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private static final int NOW_GENERATION = 5;
    private static final int PAGING_SIZE = 10;

    private final MatchingCommandService matchingCommandService;
    private final MemberRepository memberRepository;
    private final MemberQueryService memberQueryService;
    private final ProjectVolunteerQueryService projectVolunteerQueryService;
    private final S3UploadService s3UploadService;
    private final BranchQueryService branchQueryService;
    private final UniversityRepository universityRepository;
    private final MemberCommandService memberCommandService;

    public List<ChallengerInfoDTO> getChallengerList(MemberMatchingStatus memberMatchingStatus, int page) {
        Page<Member> members = memberRepository.findByGenerationAndRoleAndMatchingStatus(NOW_GENERATION, ROLE_CHALLENGER,
                memberMatchingStatus, PageRequest.of(page, PAGING_SIZE));

        return members.stream()
                .map(MemberConverter::toChallengerInfoDTO)
                .toList();
    }

    public List<ApplyTeamDTO> getMatcingRoundList(String name) {
        Member member = memberQueryService.findMemberByName(name);

        return projectVolunteerQueryService.getAllApplyTeam(member);
    }


    public List<SignUpRequestMemberDTO> getSignUpRequestList(int page) {
        Page<Member> member = memberRepository.findAllByMemberStatus(MemberStatus.PENDING, PageRequest.of(page, PAGING_SIZE));

        return member.stream()
                .map(MemberConverter::toSignUpRequestDTO)
                .toList();
    }

    public AcceptResultDTO requestMemberAccept(Long id) {
        Member member = memberQueryService.findMember(id);
        member.accept();
        memberRepository.save(member);

        return MemberConverter.toAcceptResultDTO(member);
    }

    public RejectResultDTO requestMemberReject(Long id) {
        Member member = memberQueryService.findMember(id);
        member.reject();
        memberRepository.save(member);

        return MemberConverter.toRejectResultDTO(member);
    }

    public void updateAdminInfo(UpdateAdminInfoDTO updateAdminInfoDTO, MultipartFile image, String name) {
        Member member = memberQueryService.findMemberByName(name);
        String profileImage = member.getProfileImage();
        if (image != null) {
            profileImage = s3UploadService.uploadFile(image);
        }
        member.updateAdminInfo(updateAdminInfoDTO.getPhoneNumber(), profileImage);

        University university = member.getUniversity();
        Branch newBranch = branchQueryService.findBranchByName(updateAdminInfoDTO.getBranch());
        university.updateBranch(newBranch);
        universityRepository.save(university);

        memberRepository.save(member);
    }

    public MemberResponseDTO.DepartResultDTO departingMember(String name){
        return memberCommandService.memberDepart(name);
    }

    public void startRandomMatching(int generation) {
        List<Branch> generationBranch = branchQueryService.getGenerationBranch(generation);
        for (Branch branch : generationBranch) {
            matchingCommandService.processBranch(branch);
        }
    }

}
