package com.example.umcmatchingcenter.repository;

import com.example.umcmatchingcenter.domain.Branch;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.University;
import com.example.umcmatchingcenter.domain.enums.MemberPart;
import com.example.umcmatchingcenter.domain.enums.MemberRole;
import com.example.umcmatchingcenter.domain.enums.MemberMatchingStatus;
import com.example.umcmatchingcenter.domain.enums.MemberRole;
import com.example.umcmatchingcenter.domain.enums.MemberStatus;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberName(String username);
    Member findByUniversityAndRole(University university, MemberRole role);

    Page<Member> findByGenerationAndRoleAndMatchingStatus(int generation, MemberRole role, MemberMatchingStatus matchingStatus, Pageable pageable);

    Page<Member> findAllByMemberStatus(MemberStatus memberStatus, PageRequest pageRequest);

    List<Member> findByUniversity_Branch(Branch branch);

    List<Member> findByUniversityInAndMatchingStatusInAndPartAndMemberStatus(List<University> universities, List<MemberMatchingStatus> matchingStatuses, MemberPart part, MemberStatus memberStatus);
}
