package com.example.umcmatchingcenter.dto.ProjectDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TotalMatchingResponseDTO {

    private int nowMatchingNum;
    private int totalMatchingNum;

}
