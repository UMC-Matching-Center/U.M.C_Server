package com.example.umcmatchingcenter.apiPayload.code.status;

import static org.springframework.http.HttpStatus.*;

import com.example.umcmatchingcenter.apiPayload.code.BaseErrorCode;
import com.example.umcmatchingcenter.apiPayload.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(FORBIDDEN, "COMMON403", "금지된 요청입니다."),


    // 멤버 관려 에러
    MEMBER_NOT_FOUND(BAD_REQUEST, "MEMBER4001", "존재하지 않는 사용자입니다."),
    MEMBER_WRONG_PASSWORD(BAD_REQUEST, "MEMBER4002", "잘못된 비밀번호 입니다"),
    NICKNAME_NOT_EXIST(BAD_REQUEST, "MEMBER4003", "닉네임은 필수 입니다."),
    MEMBER_ALREADY_EXIST(BAD_REQUEST, "MEMBER4004", "이미 등록된 사용자 입니다."),
    MEMBER_PROFILE_ERROR(BAD_REQUEST, "MEMBER4005", "프로필사진 업로드에 실패했습니다."),

    // 내 프로젝트 에러
    NO_SUCH_APPLICANT(BAD_REQUEST, "MYPROJECT4001", "해당 프로젝트의 지원자가 없습니다."),
    NO_MORE_APPLICANT(BAD_REQUEST, "MYPROJECT4002", "더 이상 지원자를 받을 수 없습니다."),

    WRONG_AUTH_CODE(BAD_REQUEST, "EMAIL4002", "잘못된 인증 코드 입니다"),
    FAIL_CREATE_CODE(BAD_REQUEST, "EMAIL4003", "코드 생성에 실패했습니다"),
    FAIL_CREATE_EMAIL(BAD_REQUEST, "EMAIL4004", "이메일 전송에 실패했습니다"),

    //jwt
    JWT_FORBIDDEN(FORBIDDEN, "JWT4001", "권한이 존재하지 않습니다."),
    JWT_UNAUTHORIZED(UNAUTHORIZED, "JWT4002", "자격증명이 유효하지 않습니다."),
    JWT_EXPIRATION(UNAUTHORIZED, "JWT4003", "만료된 jwt 토큰입니다"),
    JWT_WRONG_SIGNATURE(UNAUTHORIZED, "JWT4004", "잘못된 jwt 서명입니다"),
    JWT_WRONG_REFRESHTOKEN(UNAUTHORIZED, "JWT4005", "잘못된 refresh 토큰입니다."),

    //알림
    NO_DELETE_ALARM(BAD_REQUEST, "ALARM4001", "삭제할 알림이 존재하지 않습니다."),
    NO_ALARM_LIST(BAD_REQUEST, "ALARM4002", "알림이 존재하지 않습니다."),

    // 프로젝트 관련 에러
    PROJECT_NOT_EXIST(BAD_REQUEST, "PROJECT4001", "프로젝트가 존재하지 않습니다."),
    PROJECT_NOT_COMPLETE(BAD_REQUEST, "PROJECT4002", "완료된 프로젝트가 아닙니다."),
    PROJECT_NOT_PROCEEDING(BAD_REQUEST, "PROJECT4003", "현재 매칭 중인 프로젝트가 아닙니다."),

    // 매칭 일정 관련 에러
    MATCHINGSCHEDULE_NOT_EXIST(BAD_REQUEST, "SCHEDULE4001", "매칭 일정이 없습니다."),
    MATCHINGSCHEDULE_UNAUTHORIZED(BAD_REQUEST, "SCHEDULE4002", "해당 매칭 일정에 대한 권한이 없습니다."),
    MATCHINGSCHEDULE_COLOR_NOT_EXIST(BAD_REQUEST, "SCHEDULE4003", "유효하지 않은 색상입니다."),

    //공지사항
    NOTICE_NOT_EXIST(HttpStatus.BAD_REQUEST, "NOTICE4001", "공지사항이 없습니다."),
    NOTICE_IMAGE_ERROR(HttpStatus.BAD_REQUEST, "NOTICE4002", "이미지 업로드에 실패했습니다."),

    // 지부 관련 에러
    BRANCH_NOT_FOUND(BAD_REQUEST, "BRANCH4001", "지부가 존재하지 않습니다."),

    //학교 관련 에러
    UNIVERSITY_NOT_FOUND(BAD_REQUEST, "UNIVERSITY4001", "학교가 존재하지 않습니다."),

    //상호평가 관련 에러
    EVALUATION_NOT_FOUND(BAD_REQUEST, "EVALUATION4001", "평가가 존재하지 않습니다."),
    TEAMMATE_NOT_FOUND(BAD_REQUEST, "EVALUATION4002", "해당 프로젝트의 멤버가 아닙니다."),
    ALREADY_EVALUATED_MEMBER(BAD_REQUEST, "EVALUATION4003", "이미 평가가 완료된 멤버입니다."),

    //지원 정보 에러
    RECRUITMENT_NOT_FOUNT(BAD_REQUEST, "RECRUITMENT4001", "지원 정보가 없습니다."),

    // Q&A 관련 에러
    QUESTION_NOT_FOUNT(BAD_REQUEST, "QUESTION4001", "질문이 존재하지 않습니다."),
    ANSWER_UNAUTHORIZED(BAD_REQUEST, "ANSWER4002", "질문에 대한 답변 권한이 없습니다."),


    // 예시,,,
    ARTICLE_NOT_FOUND(NOT_FOUND, "ARTICLE4001", "게시글이 없습니다."),

    // Ror test
    TEMP_EXCEPTION(BAD_REQUEST, "TEMP4001", "이거는 테스트");



    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}
