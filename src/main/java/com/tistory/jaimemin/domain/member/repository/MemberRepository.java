package com.tistory.jaimemin.domain.member.repository;

import com.tistory.jaimemin.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final static String TABLE = "Member";

    private final static RowMapper<Member> rowMapper = (ResultSet resultSet, int rowNum) -> Member.builder()
            .id(resultSet.getLong("id"))
            .email(resultSet.getString("email"))
            .nickname(resultSet.getString("nickname"))
            .birthday(resultSet.getObject("birthday", LocalDate.class))
            .createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
            .build();

    final private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Optional<Member> findById(Long id) {
        var sql = String.format("SELECT * FROM %s WHERE id = :id", TABLE);
        var param = new MapSqlParameterSource()
                .addValue("id", id);

        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(sql, param, rowMapper));
    }

    public List<Member> findAllByIdIn(List<Long> ids) {
        if (ids.isEmpty()) {
            return List.of();
        }

        var sql = String.format("SELECT * FROM %s WHERE id in (:ids)", TABLE);
        var params = new MapSqlParameterSource().addValue("ids", ids);

        return namedParameterJdbcTemplate.query(sql, params, rowMapper);
    }

    public Member save(Member member) {
        if (ObjectUtils.isEmpty(member.getId())) {
            return insert(member);
        }

        return update(member);
    }

    private Member insert(Member member) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id");
        SqlParameterSource params = new BeanPropertySqlParameterSource(member);
        Number id = simpleJdbcInsert.executeAndReturnKey(params);

        return Member.builder()
                .id(id.longValue())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .birthday(member.getBirthday())
                .build();
    }

    private Member update(Member member) {
        var sql = String.format("UPDATE %s SET email = :email, nickname = :nickname, birthday = :birthday WHERE id = :id", TABLE);
        SqlParameterSource params = new BeanPropertySqlParameterSource(member);
        namedParameterJdbcTemplate.update(sql, params);

        return member;
    }
}
