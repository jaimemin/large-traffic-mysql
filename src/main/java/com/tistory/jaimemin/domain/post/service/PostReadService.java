package com.tistory.jaimemin.domain.post.service;

import com.tistory.jaimemin.domain.post.dto.DailyPostCount;
import com.tistory.jaimemin.domain.post.dto.DailyPostCountRequest;
import com.tistory.jaimemin.domain.post.entity.Post;
import com.tistory.jaimemin.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostReadService {

    private final PostRepository postRepository;

    public List<DailyPostCount> getDailyPostCount(DailyPostCountRequest request) {
        return postRepository.groupByCreatedDate(request);
    }

    public Page<Post> getPosts(Long memberId, Pageable pageable) {
        return postRepository.findAllByMemberId(memberId, pageable);
    }
}
