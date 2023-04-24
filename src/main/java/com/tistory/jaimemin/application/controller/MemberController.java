package com.tistory.jaimemin.application.controller;

import com.tistory.jaimemin.domain.member.dto.MemberDto;
import com.tistory.jaimemin.domain.member.dto.MemberNicknameHistoryDto;
import com.tistory.jaimemin.domain.member.dto.RegisterMemberCommand;
import com.tistory.jaimemin.domain.member.entity.Member;
import com.tistory.jaimemin.domain.member.service.MemberReadService;
import com.tistory.jaimemin.domain.member.service.MemberWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberReadService memberReadService;

    private final MemberWriteService memberWriteService;

    @PostMapping
    public MemberDto register(@RequestBody RegisterMemberCommand command) {
        var member = memberWriteService.register(command);

        return member;
    }

    @GetMapping("/{id}")
    public MemberDto getMember(@PathVariable Long id) {
        return memberReadService.getMember(id);
    }

    @PostMapping("/{id}")
    public MemberDto changeNickname(@PathVariable Long id, @RequestBody String nickname) {
        memberWriteService.changeNickname(id, nickname);

        return memberReadService.getMember(id);
    }

    @GetMapping("/{memberId}/nickname-histories")
    public List<MemberNicknameHistoryDto> getNicknameHistories(@PathVariable Long memberId) {
        return memberReadService.getNicknameHistories(memberId);
    }

}
