package com.example.umcmatchingcenter.dto.noticeDTO;

import com.example.umcmatchingcenter.dto.AlarmDTO.AlarmResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class NoticeResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddNoticeDTO{
        private Long noticeId;
        private String title;
        private String body;
        private LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NoticeListDTO{
        private List<NoticeResponseDTO.NoticeViewDTO> noticeList;
        private Integer listSize;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NoticeDetailsDTO{
        private Long noticeId;
        private String title;
        private String body;
        private Map<Long, String> images;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateNoticeDetailsDTO{
        private Long noticeId;
        private String title;
        private String body;
        private LocalDateTime updatedAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NoticeViewDTO{
        private Long noticeId;
        private String title;
        private String body;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}
