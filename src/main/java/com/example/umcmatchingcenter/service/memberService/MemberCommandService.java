package com.example.umcmatchingcenter.service.memberService;

import com.example.umcmatchingcenter.apiPayload.ApiResponse;
import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.code.status.SuccessStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.MemberHandler;
import com.example.umcmatchingcenter.converter.MemberConverter;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.University;
import com.example.umcmatchingcenter.domain.enums.AlarmType;
import com.example.umcmatchingcenter.domain.enums.MemberPart;
import com.example.umcmatchingcenter.domain.enums.MemberRole;
import com.example.umcmatchingcenter.dto.MemberDTO.LoginRequestDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.LoginResponseDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberRequestDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberRequestDTO.UpdateMyInfoDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO;
import com.example.umcmatchingcenter.jwt.JwtFilter;
import com.example.umcmatchingcenter.jwt.TokenProvider;
import com.example.umcmatchingcenter.repository.MemberRepository;
import com.example.umcmatchingcenter.repository.UniversityRepository;
import com.example.umcmatchingcenter.service.AlarmService.AlarmCommandService;
import com.example.umcmatchingcenter.service.RedisService;
import com.example.umcmatchingcenter.service.s3Service.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberCommandService {

    private final MemberRepository memberRepository;
    private final UniversityRepository universityRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final MemberQueryService memberQueryService;
    private final AlarmCommandService alarmCommandService;
    private final RedisService redisService;
    private final S3UploadService s3UploadService;



    public Member join(MemberRequestDTO.JoinDTO request){
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        Optional<University> university = universityRepository.findById(request.getUniversityId());

        Member newMember = MemberConverter.toMember(request, university.get());

        Member adminMember = memberRepository.findByUniversityAndRole(university.get(), MemberRole.ROLE_ADMIN);
        alarmCommandService.send(adminMember, AlarmType.JOIN, "새로운 챌린저의 가입 신청이 등록되었습니다.");

        return memberRepository.save(checkRole(request, newMember));
    }

    private Member checkRole(MemberRequestDTO.JoinDTO request, Member member){
        if(request.getPart()== MemberPart.PLAN)
            member.setRole(MemberRole.ROLE_PM);

        return member;
    }

    public LoginResponseDTO login(LoginRequestDTO request, HttpServletResponse response){

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getMemberName(), request.getPassword());

        Authentication authentication = getAuthentication(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = tokenProvider.createToken(authentication,1); //1시간
        String refreshToken = tokenProvider.createToken(authentication,24); //24시간

        redisService.setData(request.getMemberName(),refreshToken, 1440L);

        response.setHeader(JwtFilter.AUTHORIZATION_ACCESSS, "Bearer " + accessToken);
        response.setHeader(JwtFilter.AUTHORIZATION_REFRESH, "Bearer " + refreshToken);
        String memberRole = tokenProvider.getAuthorities(authentication);

        return MemberConverter.toLoginResponseDto(request.getMemberName(),
                accessToken, refreshToken,memberRole);
    }

    public Authentication getAuthentication(UsernamePasswordAuthenticationToken authenticationToken){
        try{
            return authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        }catch (BadCredentialsException e){
            throw new MemberHandler(ErrorStatus.MEMBER_WRONG_PASSWORD);
        }catch (InternalAuthenticationServiceException e){
            throw new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND);
        }
    }


    public MemberResponseDTO.DepartResultDTO memberDepart(String name) {
        Member member = memberQueryService.findMemberByName(name);
        member.depart();

        return MemberConverter.toDepartResultDTO(memberRepository.save(member));
    }

    public ApiResponse duplicationMemberName(String memberName){
        if(memberRepository.findByMemberName(memberName).isPresent()){
            throw new MemberHandler(ErrorStatus.MEMBER_ALREADY_EXIST);
        }

        return ApiResponse.of(SuccessStatus._MEMBERNICKNAME_OK,memberName);
    }

    public LoginResponseDTO.RenewalAccessTokenResponseDTO renewalAccessToken(String memberName, HttpServletRequest request, HttpServletResponse response){
        String refreshToken = JwtFilter.resolveToken(request);
        String redisToken = redisService.getData(memberName);
        if(refreshToken.equals(redisToken)){
            Authentication authentication = tokenProvider.getAuthentication(refreshToken);
            String newAccessToken = tokenProvider.createToken(authentication, 1);
            response.setHeader(JwtFilter.AUTHORIZATION_ACCESSS, newAccessToken);
            return MemberConverter.toRenewalAccessTokenResponseDTO(memberName, tokenProvider.getAuthorities(authentication), newAccessToken);
        }else{
            throw new MemberHandler(ErrorStatus.JWT_WRONG_REFRESHTOKEN);
        }
    }


    public void updateMyInfo(UpdateMyInfoDTO updateMyInfoDTO, MultipartFile image, String name) {
        Member member = memberQueryService.findMemberByName(name);
        String profileImage = member.getProfileImage();
        if (image != null) {
            profileImage = s3UploadService.uploadFile(image);
        }
        member.updateMyInfo(updateMyInfoDTO, profileImage);
        memberRepository.save(member);
    }

}
