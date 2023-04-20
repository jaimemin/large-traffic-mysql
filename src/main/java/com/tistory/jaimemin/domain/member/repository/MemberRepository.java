package com.tistory.jaimemin.domain.member.repository;

import com.tistory.jaimemin.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    final private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Member save(Member member) {
        if (ObjectUtils.isEmpty(member.getId())) {
            return insert(member);
        }

        return update(member);
    }

    private Member insert(Member member) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("Member")
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
        // TODO: 추후 구현
        return member;
    }
}
