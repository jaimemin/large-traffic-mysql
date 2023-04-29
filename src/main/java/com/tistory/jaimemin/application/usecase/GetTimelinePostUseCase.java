package com.tistory.jaimemin.application.usecase;

import com.tistory.jaimemin.domain.follow.entity.Follow;
import com.tistory.jaimemin.domain.follow.service.FollowReadService;
import com.tistory.jaimemin.domain.post.entity.Post;
import com.tistory.jaimemin.domain.post.entity.Timeline;
import com.tistory.jaimemin.domain.post.service.PostReadService;
import com.tistory.jaimemin.domain.post.service.TimelineReadService;
import com.tistory.jaimemin.util.CursorRequest;
import com.tistory.jaimemin.util.CursorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTimelinePostUseCase {

    private final PostReadService postReadService;

    private final FollowReadService followReadService;

    private final TimelineReadService timelineReadService;

    public CursorResponse<Post> execute(Long memberId, CursorRequest cursorRequest) {
        var followings = followReadService.getFollowings(memberId);
        var followingMemberIds = followings.stream()
                .map(Follow::getToMemberId)
                .toList();

        return postReadService.getPosts(followingMemberIds, cursorRequest);
    }

    /**
     * 1. Timeline 테이블 조회
     * 2. 1번에 해당하는 게시물 조회
     *
     * @param memberId
     * @param cursorRequest
     * @return
     */
    public CursorResponse<Post> executeByTimeline(Long memberId, CursorRequest cursorRequest) {
        var pagedTimelines = timelineReadService.getTimelines(memberId, cursorRequest);
        var postIds = pagedTimelines.body().stream()
                .map(Timeline::getPostId)
                .toList();
        var posts = postReadService.getPosts(postIds);

        return new CursorResponse<>(pagedTimelines.nextCursorRequest(), posts);
    }
}
