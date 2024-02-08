package com.example.umcmatchingcenter.controller;

import com.example.umcmatchingcenter.apiPayload.ApiResponse;
import com.example.umcmatchingcenter.converter.QnAConverter;
import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.QnA;
import com.example.umcmatchingcenter.dto.QnADTO.QnARequestDTO;
import com.example.umcmatchingcenter.dto.QnADTO.QnAResponseDTO;
import com.example.umcmatchingcenter.service.matchingService.MatchingQueryService;
import com.example.umcmatchingcenter.service.memberService.MemberQueryService;
import com.example.umcmatchingcenter.service.qnaService.QnACommandService;
import com.example.umcmatchingcenter.service.qnaService.QnAQueryService;
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
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Q&A API")
@RequestMapping("/qna")
public class QnAController {

    private final MemberQueryService memberQueryService;
    private final MatchingQueryService matchingQueryService;

    private final QnACommandService qnaCommandService;
    private final QnAQueryService qnaQueryService;

    /**
     * Q&A 질문 생성
     */
    @Operation(summary = "Q&A 질문 생성 API")
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
    public ApiResponse<QnAResponseDTO.questionResultDTO> postQuestion(
            @PathVariable(name = "projectId") Long projectId,
            @RequestBody @Valid QnARequestDTO.questionDTO request,
            @Valid @ExistMember Principal principal
    ){
        QnA qna = qnaCommandService.postQuestion(request, projectId, principal.getName());

        return ApiResponse.onSuccess(QnAConverter.toPostQuestionResultDTO(qna));
    }

    /**
     * Q&A 질문 삭제
     */
    @Operation(summary = "Q&A 질문 삭제 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "JWT4001", description = "JWT 토큰을 주세요!",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "JWT4002", description = "JWT 토큰 만료",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4001", description = "name 에 맞는 사용자가 없습니다.",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
    })
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{questionId}")
    @Parameters({
            @Parameter(name = "questionId", description = "질문 아이디")
    })
    public ApiResponse<String> deleteQnA(
            @PathVariable(name = "questionId") Long questionId,
            @Valid @ExistMember Principal principal
    ){
        qnaCommandService.deleteQuestion(questionId, principal.getName());

        return ApiResponse.onSuccess(questionId + "번 질문을 삭제했습니다.");
    }

    /**
     * Q&A 답변 생성
     */
    @Operation(summary = "Q&A 답변 생성 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "JWT4001", description = "JWT 토큰을 주세요!",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "JWT4002", description = "JWT 토큰 만료",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4001", description = "name 에 맞는 사용자가 없습니다.",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
    })
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{questionId}")
    @Parameters({
            @Parameter(name = "questionId", description = "질문 아이디")
    })
    public ApiResponse<String> postAnswer(
            @PathVariable(name = "questionId") Long questionId,
            @RequestBody @Valid QnARequestDTO.answerDTO request,
            @Valid @ExistMember Principal principal
    ){
        qnaCommandService.postAnswer(request, questionId, principal.getName());

        return ApiResponse.onSuccess(questionId + "번 질문에 답변했습니다.");
    }

    /**
     * Q&A 목록 조회
     */
    @Operation(summary = "특정 프로젝트의 Q&A 목록 조회 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "JWT4001", description = "JWT 토큰을 주세요!",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "JWT4002", description = "JWT 토큰 만료",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4001", description = "name 에 맞는 사용자가 없습니다.",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{projectId}")
    @Parameters({
            @Parameter(name = "projectId", description = "프로젝트 아이디")
    })
    public ApiResponse<QnAResponseDTO.QnAListDTO> getQnAList(
            @PathVariable(name = "projectId") Long projectId,
            @Valid @ExistMember Principal principal
    ){
        Project project = matchingQueryService.findProject(projectId);
        List<QnA> qnaList = qnaQueryService.getQnAList(project);

        return ApiResponse.onSuccess(QnAConverter.toQnAPreViewListDTO(qnaList));
    }
}
