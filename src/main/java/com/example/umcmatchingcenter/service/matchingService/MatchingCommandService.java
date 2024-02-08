package com.example.umcmatchingcenter.service.matchingService;

import com.example.umcmatchingcenter.converter.ProjectVolunteerConverter;
import com.example.umcmatchingcenter.domain.Branch;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.enums.MemberMatchingStatus;
import com.example.umcmatchingcenter.domain.mapping.ProjectVolunteer;
import com.example.umcmatchingcenter.domain.mapping.Recruitment;
import com.example.umcmatchingcenter.repository.MatchingRepository;
import com.example.umcmatchingcenter.repository.MemberRepository;
import com.example.umcmatchingcenter.repository.ProjectVolunteerRepository;
import com.example.umcmatchingcenter.service.branchService.BranchQueryService;
import com.example.umcmatchingcenter.service.memberService.MemberQueryService;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MatchingCommandService {
    private final MatchingRepository matchingRepository;
    private final MemberRepository memberRepository;
    private final ProjectVolunteerRepository projectVolunteerRepository;
    private final BranchQueryService branchQueryService;
    private final MemberQueryService memberQueryService;

    public void processBranch(Branch branch) {
        List<Recruitment> branchRecruitments = branchQueryService.getBranchRecruitments(branch);
        for (Recruitment recruitment : branchRecruitments) {
            processRecruitment(branch, recruitment);
        }
    }

    public void processRecruitment(Branch branch, Recruitment recruitment) {
        List<Member> branchNonMatchingMember = memberQueryService.getNonMatchingMember(branch, recruitment.getPart());
        selectAndPassMembers(branchNonMatchingMember, recruitment.getNowRecruitment(), recruitment.getProject(), recruitment);
    }


    public void selectAndPassMembers(List<Member> branchNonMatchingMember, int numberOfMembersToSelect, Project project, Recruitment recruitment) {
        Random random = new Random();
        int maxSize = branchNonMatchingMember.size();
        int numberOfMembersSelected = Math.min(numberOfMembersToSelect, maxSize);
        for (int i = 0; i < numberOfMembersSelected; i++) {
            int randomIndex = random.nextInt(maxSize);
            Member selectedMember = branchNonMatchingMember.get(randomIndex);
            selectedMember.setMatchingStatus(MemberMatchingStatus.MATCH);
            memberRepository.save(selectedMember);

            ProjectVolunteer projectVolunteer = ProjectVolunteerConverter.toProjectVolunteer(project, selectedMember, 4);
            projectVolunteerRepository.save(projectVolunteer);
            recruitment.minusRecruitment();
        }
    }

}
