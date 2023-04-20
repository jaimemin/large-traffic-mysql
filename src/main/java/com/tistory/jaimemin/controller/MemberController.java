package com.tistory.jaimemin.controller;

import com.tistory.jaimemin.domain.member.dto.RegisterMemberCommand;
import com.tistory.jaimemin.domain.member.entity.Member;
import com.tistory.jaimemin.domain.member.service.MemberWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberWriteService memberWriteService;

    @PostMapping("/members")
    public Member register(@RequestBody RegisterMemberCommand command) {
        return memberWriteService.create(command);
    }
}
