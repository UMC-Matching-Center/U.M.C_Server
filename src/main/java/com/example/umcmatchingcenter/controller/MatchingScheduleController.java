package com.example.umcmatchingcenter.controller;

import com.example.umcmatchingcenter.apiPayload.ApiResponse;
import com.example.umcmatchingcenter.converter.matching.MatchingScheduleConverter;
import com.example.umcmatchingcenter.domain.Branch;
import com.example.umcmatchingcenter.domain.MatchingSchedule;
import com.example.umcmatchingcenter.dto.MatchingDTO.MatchingScheduleRequestDTO;
import com.example.umcmatchingcenter.dto.MatchingDTO.MatchingScheduleResponseDTO;
import com.example.umcmatchingcenter.service.MatchingService.MatchingScheduleCommandService;
import com.example.umcmatchingcenter.service.memberService.MemberCommandService;
import com.example.umcmatchingcenter.service.memberService.MemberQueryService;
import com.example.umcmatchingcenter.validation.annotation.ExistMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/manage")
@Tag(name = "매칭 일정 API")
public class MatchingScheduleController {

    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;
    private final MatchingScheduleCommandService matchingScheduleCommandService;

    /**
     * 매칭 일정 생성
     */
    @Operation(summary = "매칭 일정 생성 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "JWT4001", description = "JWT 토큰을 주세요!",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "JWT4002", description = "JWT 토큰 만료",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4001", description = "name 에 맞는 사용자가 없습니다.",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
    })
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/schedule")
    public ApiResponse<MatchingScheduleResponseDTO.ScheduleResultDTO> postMatchingSchedule (
            @RequestBody @Valid MatchingScheduleRequestDTO.MatchingScheduleDTO request,
            @Valid @ExistMember Principal principal
    ){
        Branch branch = memberQueryService.findMemberByName(principal.getName()).getUniversity().getBranch();
        MatchingSchedule schedule = matchingScheduleCommandService.postSchedule(request, branch);

        return ApiResponse.onSuccess(MatchingScheduleConverter.toPostScheduleResultDTO(schedule));
    }

    /**
     * 매칭 일정 수정
     */
    @Operation(summary = "매칭 일정 수정 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "JWT4001", description = "JWT 토큰을 주세요!",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "JWT4002", description = "JWT 토큰 만료",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4001", description = "name 에 맞는 사용자가 없습니다.",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
    })
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/schedule/{scheduleId}")
    @Parameters({
            @Parameter(name = "scheduleId", description = "일정 아이디")
    })
    public ApiResponse<String> patchMatchingSchedule (
            @PathVariable(name = "scheduleId") Long scheduleId,
            @RequestBody @Valid MatchingScheduleRequestDTO.MatchingScheduleDTO request,
            @Valid @ExistMember Principal principal
    ) {
        Branch branch = memberQueryService.findMemberByName(principal.getName()).getUniversity().getBranch();
        matchingScheduleCommandService.updateSchedule(scheduleId, request, branch);

        return ApiResponse.onSuccess("일정 수정에 성공했습니다.");
    }
}
