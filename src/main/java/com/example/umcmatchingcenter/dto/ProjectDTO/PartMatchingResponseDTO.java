package com.example.umcmatchingcenter.dto.ProjectDTO;

import com.example.umcmatchingcenter.domain.enums.MemberPart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartMatchingResponseDTO {

    private MemberPart part;
    private int matchingNum;
    private int totalNum;

}
