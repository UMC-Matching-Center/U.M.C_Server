package com.example.umcmatchingcenter.domain;

import com.example.umcmatchingcenter.domain.common.BaseEntity;
import com.example.umcmatchingcenter.domain.enums.MemberPart;
import com.example.umcmatchingcenter.domain.enums.MemberRole;
import com.example.umcmatchingcenter.domain.enums.MemberMatchingStatus;

import com.example.umcmatchingcenter.domain.mapping.ProjectVolunteer;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberRequestDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberRequestDTO.UpdateAdminInfoDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberRequestDTO.UpdateMyInfoDTO;
import javax.persistence.*;

import com.example.umcmatchingcenter.domain.enums.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DynamicUpdate
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nameNickname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "universityId")
    private University university;

    @Column(nullable = false)
    private String phoneNumber;

    private String portfolio;

    private String profileImage;

    @Column(nullable = false)
    private int generation;

    @Column(nullable = false)
    private int round;

    @Column(nullable = false)
    private String memberName;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(15) DEFAULT 'PENDING'")
    private MemberStatus memberStatus;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(30) DEFAULT 'ROLE_CHALLENGER'")
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    private MemberPart part;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(15) DEFAULT 'NON'")
    private MemberMatchingStatus matchingStatus;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Alarm> alarms;

    @OneToMany(mappedBy = "pm", cascade = CascadeType.ALL)
    private List<Chat> pmChats;

    @OneToMany(mappedBy = "inquirer", cascade = CascadeType.ALL)
    private List<Chat> inquirerChats;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(mappedBy = "member")
    private List<ProjectVolunteer> projectVolunteerList;

    public void setUniversity(University university){
        this.university = university;
        university.getMembers().add(this);
    }


    public void setProject(Project project) {
        this.project = project;
    }

    public void setMatchingStatus(MemberMatchingStatus matchingStatus) {
        this.matchingStatus = matchingStatus;
    }

    public void addRound() {
        this.round++;
    }


    public void depart(){
        this.memberStatus = MemberStatus.INACTIVE;
    }

    public void accept() {
        this.memberStatus = MemberStatus.ACTIVE;
    }

    public void reject() {
        this.memberStatus = MemberStatus.INACTIVE;
    }

    public void setRole(MemberRole role){this.role = role;}

    public void updateMyInfo(UpdateMyInfoDTO updateMyInfoDTO, String profileImage) {
        this.phoneNumber = updateMyInfoDTO.getPhoneNumber();
        this.portfolio = updateMyInfoDTO.getPortfolio();
        this.profileImage = profileImage;
    }

    public void updateAdminInfo(String newPhoneNumber, String profileImage) {
        this.phoneNumber = newPhoneNumber;
        this.profileImage = profileImage;

    }

    public Branch getBranch(){
        return this.university.getBranch();
    }

    @PrePersist
    protected void onCreate() {
        this.round = 1;
    }
}