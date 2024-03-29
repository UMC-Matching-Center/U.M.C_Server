package com.example.umcmatchingcenter.controller;

import com.example.umcmatchingcenter.apiPayload.ApiResponse;
import com.example.umcmatchingcenter.converter.myProject.MyProjectConverter;
import com.example.umcmatchingcenter.domain.LandingPage;
import com.example.umcmatchingcenter.dto.ProjectDTO.MyProjectRequestDTO;
import com.example.umcmatchingcenter.dto.ProjectDTO.MyProjectResponseDTO;
import com.example.umcmatchingcenter.service.myProjectService.MyProjectQueryService;
import com.example.umcmatchingcenter.service.myProjectService.MyProjectService;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/myProject")
@RequiredArgsConstructor
@Slf4j
public class MyProjectController {

    private final MyProjectService myProjectService;
    private final MyProjectQueryService myProjectQueryService;

    @GetMapping("")
    @Operation(summary = "내 프로젝트 관리 api")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공")
    })
    @PreAuthorize("hasRole('ROLE_PM')")
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
    @PreAuthorize("hasRole('ROLE_PM')")
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
    @PreAuthorize("hasRole('ROLE_PM')")
    public ApiResponse<String> fail(@PathVariable Long memberId) {
        return ApiResponse.onSuccess(myProjectService.fail(memberId));
    }

    @PostMapping("/landingpage")
    @Operation(summary = "랜딩 페이지 작성 api")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),

    })
    @PreAuthorize("hasRole('ROLE_PM')")
    public ApiResponse<MyProjectResponseDTO.LandingPageResponseDTO> addLandingPage(@RequestBody MyProjectRequestDTO.AddLandingPageRequestDTO request, Principal principal) {
        LandingPage landingPage = myProjectService.addLandingPage(request, principal.getName());
        return ApiResponse.onSuccess(MyProjectConverter.toAddLandingPageResponseDTO(landingPage));
    }

    @PatchMapping("/landingpage/{landingPageId}")
    @Operation(summary = "랜딩 페이지 수정 api")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),

    })
    @PreAuthorize("hasRole('ROLE_PM')")
    public ApiResponse<MyProjectResponseDTO.LandingPageResponseDTO> updateLandingPage(@PathVariable(name = "landingPageId") Long landingPageId,@RequestBody MyProjectRequestDTO.UpdateLandingPageRequestDTO request) {
        LandingPage landingPage = myProjectService.UpdateLandingPage(request, landingPageId);
        return ApiResponse.onSuccess(MyProjectConverter.toAddLandingPageResponseDTO(landingPage));
    }

    @GetMapping("/landingpage")
    @Operation(summary = "랜딩 페이지 조회 api")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MYPROJECT4003", description = "랜딩페이지가 존재하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),

    })
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<MyProjectResponseDTO.LandingPageDetailsResponseDTO> getLandingPage(Principal principal) {
        return ApiResponse.onSuccess(myProjectQueryService.getLandingPage(principal.getName()));
    }
}
