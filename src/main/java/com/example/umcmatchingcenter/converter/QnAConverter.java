package com.example.umcmatchingcenter.converter;

import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.QnA;
import com.example.umcmatchingcenter.dto.QnADTO.QnARequestDTO;
import com.example.umcmatchingcenter.dto.QnADTO.QnAResponseDTO;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class QnAConverter {
    // Q&A 생성 (질문하기)
    public static QnAResponseDTO.questionResultDTO toPostQuestionResultDTO(QnA qna) {
        return QnAResponseDTO.questionResultDTO.builder()
                .questionId(qna.getId())
                .build();
    }

    public static QnA toQuestion(QnARequestDTO.questionDTO request, Project project, Member inquirer) {

        return QnA.builder()
                .project(project)
                .inquirer(inquirer)
                .question(request.getQuestion())
                .build();
    }

    // Q&A 리스트 조회
    public static QnAResponseDTO.QnAPreViewDTO toQnAPreViewDTO(QnA qna) {
        return QnAResponseDTO.QnAPreViewDTO.builder()
                .questionId(qna.getId())
                .question(qna.getQuestion())
                .answer(qna.getAnswer())
                .createdAt(qna.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")))
                .build();
    }

    public static QnAResponseDTO.QnAListDTO toQnAPreViewListDTO(List<QnA> qnaList) {

        List<QnAResponseDTO.QnAPreViewDTO> qnAPreViewDTOList =  qnaList.stream()
                .map(QnAConverter::toQnAPreViewDTO).collect(Collectors.toList());

        return QnAResponseDTO.QnAListDTO.builder()
                .qnaList(qnAPreViewDTOList)
                .build();
    }

}
