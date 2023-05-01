package com.tistory.jaimemin.domain.post.service;

import com.tistory.jaimemin.domain.member.dto.MemberDto;
import com.tistory.jaimemin.domain.post.entity.Post;
import com.tistory.jaimemin.domain.post.entity.PostLike;
import com.tistory.jaimemin.domain.post.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeWriteService {

    private final PostLikeRepository postLikeRepository;

    public Long create(Post post, MemberDto memberDto) {
        var postLike = PostLike.builder()
                .postId(post.getId())
                .memberId(memberDto.id())
                .build();

        return postLikeRepository.save(postLike).getPostId();
    }

}
