package com.example.umcmatchingcenter.dto.ProjectDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class MyProjectRequestDTO {

    @Setter
    @Getter
    public static class AddLandingPageRequestDTO{
        private Long profileImageId;
        private String title;
        private String introduction;
        private String body;
        private List<Long> imageIdList;
    }

    @Setter
    @Getter
    public static class UpdateLandingPageRequestDTO{
        private Long profileImageId;
        private String title;
        private String introduction;
        private String body;
        private List<Long> imageIdList;
        private List<Long> deleteImageIdList;
    }
}
