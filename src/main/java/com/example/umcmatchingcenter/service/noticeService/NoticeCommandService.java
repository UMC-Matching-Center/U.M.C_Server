package com.example.umcmatchingcenter.service.noticeService;

import com.example.umcmatchingcenter.converter.NoticeConverter;
import com.example.umcmatchingcenter.domain.Branch;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.Notice;
import com.example.umcmatchingcenter.domain.enums.AlarmType;
import com.example.umcmatchingcenter.dto.noticeDTO.NoticeRequestDTO;
import com.example.umcmatchingcenter.repository.NoticeRepository;
import com.example.umcmatchingcenter.service.AlarmService.AlarmCommandService;
import com.example.umcmatchingcenter.service.memberService.MemberQueryService;
import com.example.umcmatchingcenter.service.s3Service.S3UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class NoticeCommandService {

    private final NoticeRepository noticeRepository;
    private final AlarmCommandService alarmCommandService;
    private final S3UploadService s3UploadService;
    private final MemberQueryService memberQueryService;
    private final NoticeQueryService noticeQueryService;

    public Notice addNotice(NoticeRequestDTO.AddNoticeDTO request, List<MultipartFile> imageListString, String memberName){

        Notice notice = NoticeConverter.toNotice(request);

        Member member = memberQueryService.findMemberByName(memberName);

        s3UploadService.uploadFileList(imageListString);

        Branch branch = member.getUniversity().getBranch();
        alarmCommandService.sendToBranch(branch, AlarmType.NOTICE, "새로운 공지사항이 등록되었습니다.");

        notice.setBranch(branch);
        return noticeRepository.save(notice);
    }

    public Notice updateNotice(Long noticeId,NoticeRequestDTO.UpdateNoticeDTO request){
        Notice notice = noticeQueryService.findNotice(noticeId);
        notice.update(request.getTitle(), request.getBody());
        return notice;
    }
}
