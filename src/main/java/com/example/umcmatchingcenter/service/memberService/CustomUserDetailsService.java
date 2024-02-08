package com.example.umcmatchingcenter.service.memberService;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.MemberHandler;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.enums.MemberStatus;
import com.example.umcmatchingcenter.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) {
        return memberRepository.findByMemberName(username)
                .map(member -> createUser(username,member))
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
    }

    private User createUser(String username, Member member) {
        if (!(member.getMemberStatus() == MemberStatus.ACTIVE)) {
            throw new RuntimeException(username + " -> 활성화되어 있지 않습니다.");
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(member.getRole().toString()));

        return new User(member.getMemberName(),
                member.getPassword(),
                grantedAuthorities);
    }
}
