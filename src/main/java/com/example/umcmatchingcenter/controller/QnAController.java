package com.example.umcmatchingcenter.controller;

import com.example.umcmatchingcenter.apiPayload.ApiResponse;
import com.example.umcmatchingcenter.converter.QnAConverter;
import com.example.umcmatchingcenter.converter.matching.MatchingScheduleConverter;
import com.example.umcmatchingcenter.domain.Branch;
import com.example.umcmatchingcenter.domain.MatchingSchedule;
import com.example.umcmatchingcenter.domain.QnA;
import com.example.umcmatchingcenter.dto.MatchingDTO.MatchingScheduleRequestDTO;
import com.example.umcmatchingcenter.dto.MatchingDTO.MatchingScheduleResponseDTO;
import com.example.umcmatchingcenter.dto.QnADTO.QnARequestDTO;
import com.example.umcmatchingcenter.dto.QnADTO.QnAResponseDTO;
import com.example.umcmatchingcenter.service.memberService.MemberQueryService;
import com.example.umcmatchingcenter.service.qnaService.QnACommandService;
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
@Tag(name = "Q&A API")
@RequestMapping("/qna")
public class QnAController {

    private final MemberQueryService memberQueryService;
    private final QnACommandService qnaCommandService;

    /**
     * Q&A 질문 생성
     */
    @Operation(summary = "Q&A 생성 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "JWT4001", description = "JWT 토큰을 주세요!",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "JWT4002", description = "JWT 토큰 만료",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4001", description = "name 에 맞는 사용자가 없습니다.",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
    })
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{projectId}")
    @Parameters({
            @Parameter(name = "projectId", description = "매칭 프로젝트 아이디")
    })
    public ApiResponse<QnAResponseDTO.QnAResultDTO> postQnA (
            @PathVariable(name = "projectId") Long projectId,
            @RequestBody @Valid QnARequestDTO.QnADTO request,
            @Valid @ExistMember Principal principal
    ){
        QnA qna = qnaCommandService.postQnA(request, projectId);

        return ApiResponse.onSuccess(QnAConverter.toPostQnAResultDTO(qna));
    }
}
