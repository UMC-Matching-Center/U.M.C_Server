package com.example.umcmatchingcenter.controller.additional;

import com.example.umcmatchingcenter.apiPayload.ApiResponse;
import com.example.umcmatchingcenter.converter.TempConverter;
import com.example.umcmatchingcenter.dto.TempResponse;
import com.example.umcmatchingcenter.dto.TempResponse.TempTestDTO;
import com.example.umcmatchingcenter.service.tempService.TempQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/temp")
@RequiredArgsConstructor
public class TestController {
    private final TempQueryService tempQueryService;

    /**
     * Test
     * @return
     */
    @Tag(name = "test API")
    @Operation(summary = "test API")
    @ApiResponses({
            //예시 내역입니다: 예외 처리 내역을 추가해 주세요
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "JWT4001", description = "JWT 토큰을 주세요!",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "JWT4002", description = "JWT 토큰 만료",content = @Content(schema = @Schema(implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class))),
    })
    @Parameters({
            //예시 파라미터 입니다: 필요한 파라미터들을 추가해주세요
            @Parameter(name = "page", description = "페이징 넘버 입니다."),
            @Parameter(name = "categoryId", description = "조회할 게시물들의 카테고리 ID 입니다")
    })
    @GetMapping("/test")
    @PreAuthorize("hasRole('ROLE_CHALLENGER')")
    public ApiResponse<TempTestDTO> testAPI(){
        return ApiResponse.onSuccess(TempConverter.toTempTestDTO());
    }

    
    @GetMapping("/exception")
    public ApiResponse<TempResponse.TempExceptionDTO> exceptionAPI(@RequestParam Integer flag){
        tempQueryService.CheckFlag(flag);
        return ApiResponse.onSuccess(TempConverter.toTempExceptionDTO(flag));
    }
}
