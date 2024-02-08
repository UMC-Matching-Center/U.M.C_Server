package com.example.umcmatchingcenter.service.noticeService;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.NoticeHandler;
import com.example.umcmatchingcenter.converter.NoticeConverter;
import com.example.umcmatchingcenter.domain.Branch;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.Notice;
import com.example.umcmatchingcenter.dto.noticeDTO.NoticeResponseDTO;
import com.example.umcmatchingcenter.repository.NoticeRepository;
import com.example.umcmatchingcenter.service.memberService.MemberQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeQueryService {

    private final NoticeRepository noticeRepository;
    private final MemberQueryService memberQueryService;

    public List<Notice> getNoticeList(String memberName){
        Member member = memberQueryService.findMemberByName(memberName);
        Branch branch = member.getBranch();

        List<Notice> noticeList = noticeRepository.findAllByBranch(branch);

        if (noticeList == null || noticeList.isEmpty())
            throw new NoticeHandler(ErrorStatus.NOTICE_NOT_EXIST);
        return noticeList;
    }

    public NoticeResponseDTO.NoticeDetailsDTO getNoticeDetails(Long noticeId){
        Notice notice = findNotice(noticeId);
        return NoticeConverter.toNoticeDetailsDTO(notice);
    }

    public Notice findNotice(Long id){
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(()->new NoticeHandler(ErrorStatus.NOTICE_NOT_EXIST));
        return notice;
    }
}
