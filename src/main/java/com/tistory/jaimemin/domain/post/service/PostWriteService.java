package com.tistory.jaimemin.domain.post.service;

import com.tistory.jaimemin.domain.post.dto.PostCommand;
import com.tistory.jaimemin.domain.post.entity.Post;
import com.tistory.jaimemin.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostWriteService {

    private final PostRepository postRepository;

    @Transactional
    public Long create(PostCommand command) {
        var post = Post.builder()
                .memberId(command.memberId())
                .contents(command.contents())
                .build();

        return postRepository.save(post).getId();
    }

    @Transactional
    public void likePost(Long postId) {
        var post = postRepository.findById(postId, true).orElseThrow();
        post.incrementLikeCount();

        postRepository.save(post);
    }

    public void likePostByOptimisticLock(Long postId) {
        var post = postRepository.findById(postId, false).orElseThrow();
        post.incrementLikeCount();

        postRepository.save(post);
    }
}
