package com.example.umcmatchingcenter.domain;

import com.example.umcmatchingcenter.domain.common.BaseEntity;
import com.example.umcmatchingcenter.domain.enums.ProjectStatus;
import com.example.umcmatchingcenter.domain.mapping.LandingPageImage;
import com.example.umcmatchingcenter.domain.mapping.ProjectImage;
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

    @OneToOne(mappedBy = "landingPage",fetch = FetchType.LAZY)
    private Project project;

    @OneToMany(mappedBy = "landingPage")
    private List<LandingPageImage> images;

    public void updateLandingPage(MyProjectRequestDTO.UpdateLandingPageRequestDTO request){
        this.title = request.getTitle();
        this.body = request.getBody();
        this.introduction = request.getIntroduction();
    }

    public Image getProfileImage(){
        LandingPageImage landingPageImage = this.getImages().stream()
                .filter(LandingPageImage::isProfile)
                .findFirst()
                .orElse(null);
        return landingPageImage.getImage();
    }

}
