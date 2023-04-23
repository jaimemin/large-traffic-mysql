package com.tistory.jaimemin.domain.member.service;

import com.tistory.jaimemin.domain.member.dto.MemberDto;
import com.tistory.jaimemin.domain.member.dto.MemberNicknameHistoryDto;
import com.tistory.jaimemin.domain.member.entity.Member;
import com.tistory.jaimemin.domain.member.entity.MemberNicknameHistory;
import com.tistory.jaimemin.domain.member.repository.MemberNicknameHistoryRepository;
import com.tistory.jaimemin.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberReadService {

    private final MemberRepository memberRepository;

    private final MemberNicknameHistoryRepository memberNicknameHistoryRepository;

    public MemberDto getMember(Long id) {
        return toDto(memberRepository.findById(id).orElseThrow());
    }

    public List<MemberNicknameHistoryDto> getNicknameHistories(Long memberId) {
        return memberNicknameHistoryRepository.findAllByMemberId(memberId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    private MemberDto toDto(Member member) {
        return new MemberDto(member.getId()
                , member.getEmail()
                , member.getNickname()
                , member.getBirthday());
    }

    private MemberNicknameHistoryDto toDto(MemberNicknameHistory history) {
        return new MemberNicknameHistoryDto(
                history.getId(),
                history.getMemberId(),
                history.getNickname(),
                history.getCreatedAt()
        );
    }
}
