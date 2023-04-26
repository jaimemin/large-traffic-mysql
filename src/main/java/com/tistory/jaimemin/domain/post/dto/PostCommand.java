package com.tistory.jaimemin.domain.post.dto;

public record PostCommand(
        Long memberId,
        String contents
) {
}
