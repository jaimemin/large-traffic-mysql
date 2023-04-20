package com.tistory.jaimemin.domain.member.dto;

import java.time.LocalDate;

/**
 * 14부터 추가된 record
 *
 * @param email
 * @param nickname
 * @param birthdate
 */
public record RegisterMemberCommand(
        String email,
        String nickname,
        LocalDate birthdate
) {

}
