package com.tistory.jaimemin.domain.post;

import com.tistory.jaimemin.domain.post.entity.Post;
import com.tistory.jaimemin.domain.post.repository.PostRepository;
import com.tistory.jaimemin.util.PostFixtureFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.time.LocalDate;
import java.util.stream.IntStream;

@SpringBootTest
public class PostBulkInsertTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    public void bulkInsert() {
        var easyRandom = PostFixtureFactory.get(1L
                , LocalDate.of(2023, 1, 1)
                , LocalDate.of(2023, 4, 16));

        var stopWatch = new StopWatch();
        stopWatch.start();
        var posts = IntStream.range(0, 10000 * 100)
                .parallel()
                .mapToObj(i -> easyRandom.nextObject(Post.class))
                .toList();
        stopWatch.stop();
        System.out.println("객체 생성 시간 : " + stopWatch.getTotalTimeSeconds());

        var queryStopWatch = new StopWatch();
        queryStopWatch.start();
        postRepository.bulkInsert(posts);
        queryStopWatch.stop();
        System.out.println("DB insert 시간 : " + queryStopWatch.getTotalTimeSeconds());
    }
}
