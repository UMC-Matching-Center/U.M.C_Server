package com.example.umcmatchingcenter.domain;

import com.example.umcmatchingcenter.domain.common.BaseEntity;
import com.example.umcmatchingcenter.domain.enums.ProjectStatus;
import com.example.umcmatchingcenter.domain.mapping.Recruitment;
import com.example.umcmatchingcenter.dto.MatchingDTO.MatchingRequestDTO;
import com.example.umcmatchingcenter.dto.ProjectDTO.MyProjectRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class LandingPage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String introduction;

    private String body;

    @OneToMany(mappedBy = "project")
    private List<Image> images;

    public void updateLandingPage(MyProjectRequestDTO.UpdateLandingPageRequestDTO request){
        this.title = request.getTitle();
        this.body = request.getBody();
        this.introduction = request.getIntroduction();
    }

}
