package com.tistory.jaimemin.domain.post.service;

import com.tistory.jaimemin.domain.post.dto.DailyPostCount;
import com.tistory.jaimemin.domain.post.dto.DailyPostCountRequest;
import com.tistory.jaimemin.domain.post.dto.PostDto;
import com.tistory.jaimemin.domain.post.entity.Post;
import com.tistory.jaimemin.domain.post.repository.PostLikeRepository;
import com.tistory.jaimemin.domain.post.repository.PostRepository;
import com.tistory.jaimemin.util.CursorRequest;
import com.tistory.jaimemin.util.CursorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.OptionalLong;

@Service
@RequiredArgsConstructor
public class PostReadService {

    private final PostRepository postRepository;

    private final PostLikeRepository postLikeRepository;

    public List<DailyPostCount> getDailyPostCount(DailyPostCountRequest request) {
        return postRepository.groupByCreatedDate(request);
    }

    public Post getPost(Long postId) {
        return postRepository.findById(postId, false).orElseThrow();
    }

    public Page<PostDto> getPosts(Long memberId, Pageable pageable) {
        return postRepository.findAllByMemberId(memberId, pageable).map(this::toDto);
    }

    public CursorResponse<Post> getPosts(Long memberId, CursorRequest cursorRequest) {
        var posts = cursorRequest.hasKey() ?
                postRepository.findAllByLessThanIdAndMemberIdAndOrderByIdDesc(cursorRequest.key(), memberId, cursorRequest.size())
                : postRepository.findAllByMemberIdAndOrderByIdDesc(memberId, cursorRequest.size());
        var nextKey = getNextKey(posts);

        return new CursorResponse<>(cursorRequest.next(nextKey), posts);
    }

    public CursorResponse<Post> getPosts(List<Long> memberIds, CursorRequest cursorRequest) {
        var posts = cursorRequest.hasKey() ?
                postRepository.findAllByLessThanIdAndInMemberIdsAndOrderByIdDesc(cursorRequest.key(), memberIds, cursorRequest.size())
                : postRepository.findAllByInMemberIdsAndOrderByIdDesc(memberIds, cursorRequest.size());
        var nextKey = getNextKey(posts);

        return new CursorResponse<>(cursorRequest.next(nextKey), posts);
    }

    public List<Post> getPosts(List<Long> ids) {
        return postRepository.findAllByInIds(ids);
    }

    private static long getNextKey(List<Post> posts) {
        return posts.stream()
                .mapToLong(Post::getId)
                .min()
                .orElse(CursorRequest.NONE_KEY);
    }

    private PostDto toDto(Post post) {
        return new PostDto(
                post.getId(),
                post.getContents(),
                post.getCreatedAt(),
                postLikeRepository.count(post.getId()));
    }
}
