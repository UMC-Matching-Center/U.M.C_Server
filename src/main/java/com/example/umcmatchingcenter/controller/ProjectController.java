package com.example.umcmatchingcenter.controller;

import com.example.umcmatchingcenter.apiPayload.ApiResponse;
import com.example.umcmatchingcenter.converter.ProjectConverter;
import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.enums.ProjectStatus;
import com.example.umcmatchingcenter.dto.projectDto.ProjectResponseDTO;
import com.example.umcmatchingcenter.service.projectService.ProjectQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "project API")
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectQueryService projectQueryService;

    // 프로젝트 목록 조회
    @GetMapping("")
    @Operation(summary = "프로젝트 목록 조회 API")
    @ApiResponses({ // API 응답
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "status", description = "프로젝트 상태 (현재 매칭중: PROCEEDING, OB 프로젝트: COMPLETE)"),
            @Parameter(name = "page", description = "페이지 번호 (1부터 시작, 15개를 기준으로 페이징)")
    })
    public ApiResponse<ProjectResponseDTO.ProjectListDTO> getProjectList(@RequestParam(name = "status") ProjectStatus status, @RequestParam(name = "page") Integer page){
        List<Project> projectList = projectQueryService.getProjectList(status, page - 1);
        return ApiResponse.onSuccess(ProjectConverter.projectPreViewListDTO(projectList));
    }

    // 프로젝트 상세 조회
    @GetMapping("/{projectId}")
    @Operation(summary = "프로젝트 상세 조회 API")
    @ApiResponses({ // API 응답
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "projectId", description = "프로젝트 아이디")
    })
    public ApiResponse<ProjectResponseDTO.ProjectDTO> getProject(@PathVariable(name = "projectId") Long projectId){
        Project project = projectQueryService.getProjectDetail(projectId);
        return ApiResponse.onSuccess(ProjectConverter.projectDetailDTO(project));
    }
}
