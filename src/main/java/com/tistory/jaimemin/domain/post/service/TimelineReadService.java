package com.tistory.jaimemin.domain.post.service;

import com.tistory.jaimemin.domain.post.entity.Timeline;
import com.tistory.jaimemin.domain.post.repository.TimelineRepository;
import com.tistory.jaimemin.util.CursorRequest;
import com.tistory.jaimemin.util.CursorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimelineReadService {

    private final TimelineRepository timelineRepository;

    public CursorResponse<Timeline> getTimelines(Long memberId, CursorRequest cursorRequest) {
        var timelines = cursorRequest.hasKey() ?
                timelineRepository.findAllByLessThanIdAndMemberIdAndOrderByIdDesc(cursorRequest.key(), memberId, cursorRequest.size())
                : timelineRepository.findAllByMemberIdAndOrderByIdDesc(memberId, cursorRequest.size());
        var nextKey = timelines.stream()
                .mapToLong(Timeline::getId)
                .min()
                .orElse(CursorRequest.NONE_KEY);

        return new CursorResponse<>(cursorRequest.next(nextKey), timelines);
    }

}
