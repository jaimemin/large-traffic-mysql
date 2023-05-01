package com.tistory.jaimemin.domain.post.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Post {

    final private Long id;

    final private Long memberId;

    final private String contents;

    final private LocalDate createdDate;

    private Long likeCount;

    private Long version;

    final private LocalDateTime createdAt;

    @Builder
    public Post(Long id, Long memberId, String contents, LocalDate createdDate, Long likeCount, Long version, LocalDateTime createdAt) {
        this.id = id;
        this.memberId = Objects.requireNonNull(memberId);
        this.contents = Objects.requireNonNull(contents);
        this.createdDate = createdDate == null ? LocalDate.now() : createdDate;
        this.likeCount = likeCount == null ? 0 : likeCount;
        this.version = version == null ? 0 : version;
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }

    public void incrementLikeCount() {
        likeCount++;
    }
}
