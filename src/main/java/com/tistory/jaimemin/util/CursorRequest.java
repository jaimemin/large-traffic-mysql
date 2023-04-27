package com.tistory.jaimemin.util;

public record CursorRequest(
        Long key,
        Integer size
) {

    public static final Long NONE_KEY = -1L;

    public Boolean hasKey() {
        return key != null;
    }

    public CursorRequest next(Long key) {
        return new CursorRequest(key, size);
    }
}
