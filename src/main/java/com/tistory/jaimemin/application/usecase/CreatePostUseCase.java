package com.tistory.jaimemin.application.usecase;

import com.tistory.jaimemin.domain.follow.entity.Follow;
import com.tistory.jaimemin.domain.follow.service.FollowReadService;
import com.tistory.jaimemin.domain.post.dto.PostCommand;
import com.tistory.jaimemin.domain.post.service.PostWriteService;
import com.tistory.jaimemin.domain.post.service.TimelineWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreatePostUseCase {

    private final PostWriteService postWriteService;

    private final FollowReadService followReadService;

    private final TimelineWriteService timelineWriteService;

    /**
     * 서비스 성격에 따라 해당 메서드에 Transactional 적용할지 여부가 갈림
     *
     * @param postCommand
     * @return
     */
    // @Transactional
    public Long execute(PostCommand postCommand) {
        var postId = postWriteService.create(postCommand);
        var followMemberIds = followReadService.getFollowers(postCommand.memberId())
                .stream()
                .map(Follow::getFromMemberId)
                .toList();
        timelineWriteService.deliverToTimeline(postId, followMemberIds);

        return postId;
    }
}
