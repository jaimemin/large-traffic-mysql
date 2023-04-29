package com.tistory.jaimemin.application.usecase;

import com.tistory.jaimemin.domain.follow.entity.Follow;
import com.tistory.jaimemin.domain.follow.service.FollowReadService;
import com.tistory.jaimemin.domain.post.dto.PostCommand;
import com.tistory.jaimemin.domain.post.service.PostWriteService;
import com.tistory.jaimemin.domain.post.service.TimelineWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePostUseCase {

    private final PostWriteService postWriteService;

    private final FollowReadService followReadService;

    private final TimelineWriteService timelineWriteService;

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
