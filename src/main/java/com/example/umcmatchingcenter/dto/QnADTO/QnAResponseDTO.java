package com.example.umcmatchingcenter.dto.QnADTO;

import com.example.umcmatchingcenter.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class QnAResponseDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class questionResultDTO {
        private Long questionId;
    }

    // Q&A 목록 조회
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QnAListDTO {
        private List<QnAPreViewDTO> qnaList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QnAPreViewDTO {
        private Long questionId;
        private String question;
        private String answer;
        private String createdAt;
    }

}
