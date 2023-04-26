package com.tistory.jaimemin.domain.post.repository;

import com.tistory.jaimemin.domain.post.dto.DailyPostCount;
import com.tistory.jaimemin.domain.post.dto.DailyPostCountRequest;
import com.tistory.jaimemin.domain.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private static final String TABLE = "Post";

    private static final RowMapper<DailyPostCount> DAILY_POST_COUNT_MAPPER = (ResultSet resultSet, int rowNum)
            -> new DailyPostCount(
                    resultSet.getLong("memberId")
            , resultSet.getObject("createdDate", LocalDate.class)
            , resultSet.getLong("count")
    );

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<DailyPostCount> groupByCreatedDate(DailyPostCountRequest request) {
        var sql = String.format("""
                SELECT createdDate, memberId, COUNT(id) as count
                FROM %s 
                WHERE memberId = :memberId and createdDate between :firstDate and :lastDate 
                GROUP BY memberId, createdDate 
                """, TABLE);
        var params = new BeanPropertySqlParameterSource(request);

        return namedParameterJdbcTemplate.query(sql, params, DAILY_POST_COUNT_MAPPER);
    }

    public Post save(Post post) {
        if (post.getId() == null) {
            return insert(post);
        }

        throw new UnsupportedOperationException("Post는 갱신을 지원하지 않습니다.");
    }

    public void bulkInsert(List<Post> posts) {
        var sql = String.format("""
                INSERT INTO `%s` (memberId, contents, createdDate, createdAt) 
                VALUES (:memberId, :contents, :createdDate, :createdAt)
                """, TABLE);
        SqlParameterSource[] params = posts.stream()
                .map(BeanPropertySqlParameterSource::new)
                .toArray(SqlParameterSource[]::new);

        namedParameterJdbcTemplate.batchUpdate(sql, params);
    }

    private Post insert(Post post) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id");
        SqlParameterSource params = new BeanPropertySqlParameterSource(post);
        var id = jdbcInsert.executeAndReturnKey(params).longValue();

        return Post.builder()
                .id(id)
                .memberId(post.getMemberId())
                .contents(post.getContents())
                .createdDate(post.getCreatedDate())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
