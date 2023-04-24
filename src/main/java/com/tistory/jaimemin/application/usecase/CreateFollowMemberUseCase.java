package com.tistory.jaimemin.application.usecase;

import com.tistory.jaimemin.domain.follow.service.FollowWriteService;
import com.tistory.jaimemin.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateFollowMemberUseCase {

    private final MemberReadService memberReadService;

    private final FollowWriteService followWriteService;

    /**
     * 1. 입력받은 memberId로 회원 조회
     * 2. FollowWriteService.create()
     *
     * @param fromMemberId
     * @param toMemberId
     */
    public void execute(Long fromMemberId, Long toMemberId) {
        var fromMember = memberReadService.getMember(fromMemberId);
        var toMember = memberReadService.getMember(toMemberId);

        followWriteService.create(fromMember, toMember);
    }
}
