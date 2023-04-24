package com.tistory.jaimemin.application.usecase;

import com.tistory.jaimemin.domain.follow.service.FollowReadService;
import com.tistory.jaimemin.domain.member.dto.MemberDto;
import com.tistory.jaimemin.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetFollowingMembersUseCase {

    private final MemberReadService memberReadService;

    private final FollowReadService followReadService;

    /**
     * 1. fromMemberId = memberId -> Follow list
     * 2. 1번을 순회하면서 회원정보를 찾기
     *
     * @param memberId
     * @return
     */
    public List<MemberDto> execute(Long memberId) {
        var followings = followReadService.getFollowings(memberId);
        var followingMemberIds = followings.stream()
                .map(f -> f.getToMemberId())
                .toList();

        return memberReadService.getMembers(followingMemberIds);
    }
}
