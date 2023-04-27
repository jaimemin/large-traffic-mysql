package com.tistory.jaimemin.util;

import java.util.List;

public record CursorResponse<T> (
    CursorRequest nextCursorRequest,
    List<T> body
) {

}
