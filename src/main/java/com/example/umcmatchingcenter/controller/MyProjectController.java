package com.example.umcmatchingcenter.controller;

import com.example.umcmatchingcenter.apiPayload.ApiResponse;
import com.example.umcmatchingcenter.converter.myProject.MyProjectConverter;
import com.example.umcmatchingcenter.domain.LandingPage;
import com.example.umcmatchingcenter.dto.ProjectDTO.MyProjectRequestDTO;
import com.example.umcmatchingcenter.dto.ProjectDTO.MyProjectResponseDTO;
import com.example.umcmatchingcenter.service.myProjectService.MyProjectService;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/myProject")
@RequiredArgsConstructor
@Slf4j
public class MyProjectController {

    private final MyProjectService myProjectService;

    @GetMapping("")
    @Operation(summary = "내 프로젝트 관리 api")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공")
    })
    public ApiResponse<MyProjectResponseDTO> myProject() {
        return ApiResponse.onSuccess(myProjectService.myProject());
    }

    @PostMapping("/pass/{memberId}")
    @Operation(summary = "지원자 합격 api")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MYPROJECT401", description = "해당프로젝트의 지원자가 없습니다",
                    content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
    })
    @ApiParam(value = "유저 아이디")
    public ApiResponse<String> pass(@PathVariable Long memberId) {
        return ApiResponse.onSuccess(myProjectService.pass(memberId));
    }

    @PostMapping("/fail/{memberId}")
    @Operation(summary = "지원자 불합격 api")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MYPROJECT401", description = "해당프로젝트의 지원자가 없습니다",
                    content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
    })
    @ApiParam(value = "유저 아이디")
    public ApiResponse<String> fail(@PathVariable Long memberId) {
        return ApiResponse.onSuccess(myProjectService.fail(memberId));
    }

    @PostMapping("/landingpage")
    @Operation(summary = "랜딩 페이지 작성 api")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),

    })
    public ApiResponse<MyProjectResponseDTO.AddLandingPageResponseDTO> addLandingPage(@RequestBody MyProjectRequestDTO.AddLandingPageRequestDTO request) {
        LandingPage landingPage = myProjectService.AddLandingPage(request);
        return ApiResponse.onSuccess(MyProjectConverter.toAddLandingPageResponseDTO(landingPage));
    }

    @PatchMapping("/landingpage")
    @Operation(summary = "랜딩 페이지 수정 api")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),

    })
    public ApiResponse<String> updateLandingPage(@RequestBody MyProjectRequestDTO.UpdateLandingPageRequestDTO request) {
        return null;
    }
}
