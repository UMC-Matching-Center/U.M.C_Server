package com.example.umcmatchingcenter.repository;

import com.example.umcmatchingcenter.domain.Branch;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.University;
import com.example.umcmatchingcenter.domain.enums.MemberPart;
import com.example.umcmatchingcenter.domain.enums.MemberRole;
import com.example.umcmatchingcenter.domain.enums.MemberMatchingStatus;
import com.example.umcmatchingcenter.domain.enums.MemberStatus;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberNameAndMemberStatus(String username, MemberStatus memberStatus);

    Member findByUniversityAndRole(University university, MemberRole role);

    Member findByRole(MemberRole memberRole);

    Page<Member> findByGenerationAndRoleAndMatchingStatusAndUniversityAndMemberStatus(int nowGeneration, MemberRole memberRole, MemberMatchingStatus memberMatchingStatus, PageRequest of, University adminUniversity, MemberStatus memberStatus);

    Page<Member> findAllByMemberStatus(MemberStatus memberStatus, PageRequest pageRequest);

    List<Member> findByUniversity_Branch(Branch branch);

    List<Member> findByUniversityInAndMatchingStatusInAndPartAndMemberStatus(List<University> universities, List<MemberMatchingStatus> matchingStatuses, MemberPart part, MemberStatus memberStatus);

    Page<Member> findByGenerationAndRoleAndUniversityAndMemberStatus(int nowGeneration, MemberRole memberRole, PageRequest of, University adminUniversity, MemberStatus memberStatus);


}
