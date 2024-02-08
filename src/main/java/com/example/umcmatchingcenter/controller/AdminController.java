package com.example.umcmatchingcenter.controller;

import com.example.umcmatchingcenter.apiPayload.ApiResponse;
import com.example.umcmatchingcenter.domain.enums.MemberMatchingStatus;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberRequestDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO.AcceptResultDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO.ChallengerInfoDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO.ApplyTeamDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO.RejectResultDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO.SignUpRequestMemberDTO;
import com.example.umcmatchingcenter.service.AdminService;
import com.example.umcmatchingcenter.service.branchService.BranchQueryService;
import com.example.umcmatchingcenter.service.memberService.MemberCommandService;
import com.example.umcmatchingcenter.service.memberService.MemberQueryService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.security.Principal;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/manage")
@Tag(name = "관리자 API")
public class AdminController {

    private final AdminService adminService;

    @Operation(summary = "챌린저 관리용 조회 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "JWT4001", description = "JWT 토큰을 주세요!",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "JWT4002", description = "JWT 토큰 만료",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "page", description = "1부터 시작하는 페이징 넘버 입니다."),
            @Parameter(name = "matchingStatus", description = "조회할 챌린저의 매칭 상태입니다.")
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/challenger")
    public ApiResponse<List<MemberResponseDTO.ChallengerInfoDTO>> challengerList(@RequestParam("matchingStatus") MemberMatchingStatus memberMatchingStatus, @RequestParam("page") int page){
        List<ChallengerInfoDTO> challengerList = adminService.getChallengerList(memberMatchingStatus, page - 1 );

        return ApiResponse.onSuccess(challengerList);
    }

    @Operation(summary = "매칭 차수 별 지원 팀 조회 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "JWT4001", description = "JWT 토큰을 주세요!",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "JWT4002", description = "JWT 토큰 만료",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4001", description = "name 에 맞는 사용자가 없습니다.",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "name", description = "조회할 챌린저의 name(membername) 입니다.")
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/challenger/{name}")
    public ApiResponse<List<ApplyTeamDTO>> matchRoundList(@PathVariable(name = "name") String name){
        List<ApplyTeamDTO> matchingRoundDTOList = adminService.getMatcingRoundList(name);

        return ApiResponse.onSuccess(matchingRoundDTOList);
    }

    @Operation(summary = "챌린저 탈부 처리 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "JWT4001", description = "JWT 토큰을 주세요!",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "JWT4002", description = "JWT 토큰 만료",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4001", description = "name 에 맞는 사용자가 없습니다.",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "name", description = "탈부시킬 챌린저의 name(membername) 입니다.")
    })
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/challenger/depart/{name}")
    public ApiResponse<MemberResponseDTO.DepartResultDTO> memberDepart(@PathVariable(name = "name") String name){
        MemberResponseDTO.DepartResultDTO departResultDTO = adminService.departingMember(name);

        return ApiResponse.onSuccess(departResultDTO);
    }

    @Operation(summary = "회원가입 대기 챌린저 조회 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "JWT4001", description = "JWT 토큰을 주세요!",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "JWT4002", description = "JWT 토큰 만료",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "page", description = "1부터 시작하는 페이징입니다. 10건씩 조회됩니다.")
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/challenger/signup-requests")
    public ApiResponse<List<SignUpRequestMemberDTO>> requestMemberList(@RequestParam("page") int page){
        List<SignUpRequestMemberDTO> signUpRequestDTOList = adminService.getSignUpRequestList(page - 1);

        return ApiResponse.onSuccess(signUpRequestDTOList);
    }

    @Operation(summary = "회원가입 대기 챌린저 수락 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "JWT4001", description = "JWT 토큰을 주세요!",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "JWT4002", description = "JWT 토큰 만료",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4001", description = "id에 맞는 사용자가 없습니다.",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),

    })
    @Parameters({
            @Parameter(name = "id", description = "수락하려는 챌린저 ID 값 입니다..")
    })
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/challenger/signup-requests/{id}/accept")
    public ApiResponse<AcceptResultDTO> memberAccept(@PathVariable("id")Long id){
        AcceptResultDTO acceptResultDTO = adminService.requestMemberAccept(id);

        return ApiResponse.onSuccess(acceptResultDTO);
    }


    @Operation(summary = "회원가입 대기 챌린저 거절 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "JWT4001", description = "JWT 토큰을 주세요!",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "JWT4002", description = "JWT 토큰 만료",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4001", description = "id에 맞는 사용자가 없습니다.",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),

    })
    @Parameters({
            @Parameter(name = "id", description = "거절하려는 챌린저 ID 값 입니다..")
    })
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/challenger/signup-requests/{id}/reject")
    public ApiResponse<RejectResultDTO> memberReject(@PathVariable("id") Long id){
        RejectResultDTO rejectResultDTO = adminService.requestMemberReject(id);

        return ApiResponse.onSuccess(rejectResultDTO);
    }

    @Operation(summary = "관리자 정보 수정 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4001", description = "사용자가 없습니다.", content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4005", description = "사진 업로드에 실패했습니다.", content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "415 Unsupported Media Type", description = "UpdateMyInfoDTO JSON 전송시 Content-Type에 application/json 을 명시해 주세요", content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
    })
    @PreAuthorize("isAuthenticated()")
    @PatchMapping(value = "/info" , consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResponse<String> updateMyInfo(@RequestPart @Valid MemberRequestDTO.UpdateAdminInfoDTO updateAdminInfoDTO,
                                            @RequestPart(required = false) MultipartFile image,
                                            Principal principal) {

        adminService.updateAdminInfo(updateAdminInfoDTO, image, principal.getName());
        return ApiResponse.onSuccess("내 정보 수정에 성공했습니다.");
    }

    @Operation(summary = "랜덤매칭 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4001", description = "사용자가 없습니다.", content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4005", description = "사진 업로드에 실패했습니다.", content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "generation", description = "랜덤 매칭 돌리는 기수 번호입니다.")
    })
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/match/random/{generation}")
    public ApiResponse<String> randomMatching(@PathVariable(name = "generation")int generation){
        adminService.startRandomMatching(generation);
        return ApiResponse.onSuccess("랜덤매칭 완료");
    }
}
