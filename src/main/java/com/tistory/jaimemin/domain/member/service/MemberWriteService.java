package com.tistory.jaimemin.domain.member.service;

import com.tistory.jaimemin.domain.member.dto.RegisterMemberCommand;
import com.tistory.jaimemin.domain.member.entity.Member;
import com.tistory.jaimemin.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberWriteService {

    private final MemberRepository memberRepository;

    /**
     * 목표 - 회원정보(이메일, 닉네임, 생년월일) 등록
     *
     * 닉네임은 10자 넘길 수 없음
     */
    public Member create(RegisterMemberCommand command) {
        Member member = Member.builder()
                .nickname(command.nickname())
                .email(command.email())
                .birthday(command.birthdate())
                .build();
        memberRepository.save(member);

        return member;
    }
}
