package com.tistory.jaimemin.application.controller;

import com.tistory.jaimemin.application.usecase.CreateFollowMemberUseCase;
import com.tistory.jaimemin.application.usecase.GetFollowingMembersUseCase;
import com.tistory.jaimemin.domain.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {

    private final CreateFollowMemberUseCase createFollowMemberUseCase;

    private final GetFollowingMembersUseCase getFollowingMembersUseCase;

    @PostMapping("/{fromId}/{toId}")
    public void create(@PathVariable Long fromId, @PathVariable Long toId) {
        createFollowMemberUseCase.execute(fromId, toId);
    }

    @GetMapping("/members/{fromId}")
    public List<MemberDto> create(@PathVariable Long fromId) {
        return getFollowingMembersUseCase.execute(fromId);
    }
}
