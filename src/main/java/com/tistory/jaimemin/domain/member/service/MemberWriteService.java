package com.tistory.jaimemin.domain.member.service;

import com.tistory.jaimemin.domain.member.dto.MemberDto;
import com.tistory.jaimemin.domain.member.dto.RegisterMemberCommand;
import com.tistory.jaimemin.domain.member.entity.Member;
import com.tistory.jaimemin.domain.member.entity.MemberNicknameHistory;
import com.tistory.jaimemin.domain.member.repository.MemberNicknameHistoryRepository;
import com.tistory.jaimemin.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberWriteService {

    private final MemberRepository memberRepository;

    private final MemberNicknameHistoryRepository memberNicknameHistoryRepository;

    /**
     * 목표 - 회원정보(이메일, 닉네임, 생년월일) 등록
     *
     * 닉네임은 10자 넘길 수 없음
     */
    @Transactional
    public MemberDto register(RegisterMemberCommand command) {
        Member member = Member.builder()
                .nickname(command.nickname())
                .email(command.email())
                .birthday(command.birthdate())
                .build();
        var savedMember = memberRepository.save(member);
        saveMemberNicknameHistory(savedMember);

        return toDto(savedMember);
    }

    @Transactional
    public void changeNickname(Long memberId, String nickname) {
        var member = memberRepository.findById(memberId).orElseThrow();
        member.changeNickname(nickname);
        memberRepository.save(member);

        saveMemberNicknameHistory(member);
    }

    private void saveMemberNicknameHistory(Member member) {
        var history = MemberNicknameHistory.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .build();

        memberNicknameHistoryRepository.save(history);
    }

    private MemberDto toDto(Member member) {
        return new MemberDto(member.getId()
                , member.getEmail()
                , member.getNickname()
                , member.getBirthday());
    }
}
