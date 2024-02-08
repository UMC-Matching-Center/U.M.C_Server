package com.example.umcmatchingcenter.dto.QnADTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;

public class QnARequestDTO {
    @Getter
    public static class questionDTO {
        @NotNull
        @Schema(description = "질문 내용", example = "질문 내용")
        String question;
    }

    @Getter
    public static class answerDTO {
        @NotNull
        @Schema(description = "답변 내용", example = "답변 내용")
        String answer;
    }
}
