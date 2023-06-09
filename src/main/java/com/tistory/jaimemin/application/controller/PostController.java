package com.tistory.jaimemin.application.controller;

import com.tistory.jaimemin.application.usecase.CreatePostLikeUseCase;
import com.tistory.jaimemin.application.usecase.CreatePostUseCase;
import com.tistory.jaimemin.application.usecase.GetTimelinePostUseCase;
import com.tistory.jaimemin.domain.post.dto.PostDto;
import com.tistory.jaimemin.domain.post.dto.DailyPostCount;
import com.tistory.jaimemin.domain.post.dto.DailyPostCountRequest;
import com.tistory.jaimemin.domain.post.dto.PostCommand;
import com.tistory.jaimemin.domain.post.entity.Post;
import com.tistory.jaimemin.domain.post.service.PostReadService;
import com.tistory.jaimemin.domain.post.service.PostWriteService;
import com.tistory.jaimemin.util.CursorRequest;
import com.tistory.jaimemin.util.CursorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostReadService postReadService;

    private final PostWriteService postWriteService;

    private final CreatePostUseCase createPostUseCase;

    private final CreatePostLikeUseCase createPostLikeUseCase;

    private final GetTimelinePostUseCase getTimelinePostUsecase;

    @PostMapping
    public Long create(PostCommand command) {
        return createPostUseCase.execute(command);
    }

    @GetMapping("/daily-post-counts")
    public List<DailyPostCount> getDailyPostCounts(@RequestBody DailyPostCountRequest request) {
        return postReadService.getDailyPostCount(request);
    }

    @GetMapping("/members/{memberId}")
    public Page<PostDto> getPosts(
            @PathVariable Long memberId,
            Pageable pageable
    ) {
        return postReadService.getPosts(memberId, pageable);
    }

    @GetMapping("/members/{memberId}/by-cursor")
    public CursorResponse<Post> getPosts(
            @PathVariable Long memberId,
            CursorRequest cursorRequest
    ) {
        return postReadService.getPosts(memberId, cursorRequest);
    }

    @GetMapping("/member/{memberId}/timeline")
    public CursorResponse<Post> getTimeline(
            @PathVariable Long memberId,
            CursorRequest cursorRequest
    ) {
        return getTimelinePostUsecase.executeByTimeline(memberId, cursorRequest);
    }

    @PostMapping("/{postId}/like/v1")
    public void likePostV1(@PathVariable Long postId) {
//        postWriteService.likePost(postId);
        postWriteService.likePostByOptimisticLock(postId);
    }

    @PostMapping("/{postId}/like/v2")
    public void likePostV2(@PathVariable Long postId, @RequestParam Long memberId) {
        createPostLikeUseCase.execute(postId, memberId);
    }
}
