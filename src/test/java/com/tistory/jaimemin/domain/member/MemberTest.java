package com.tistory.jaimemin.domain.member;

import com.tistory.jaimemin.util.MemberFixtureFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MemberTest {

    /**
     * keyword: ObjectMother, EasyRandom
     */
    @Test
    @DisplayName("member can change his or her nickname")
    public void testChangeName() {
        var member = MemberFixtureFactory.create();
        var expected = "jaimemin";
        member.changeNickname(expected);

        assertThat(expected).isEqualTo(member.getNickname());
    }

    @Test
    @DisplayName("member's nickname length can't surpass 10")
    public void testNicknameMaxLength() {
        var member = MemberFixtureFactory.create();
        var overMaxLength = "10000000000000000";

        org.junit.jupiter.api.Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> member.changeNickname(overMaxLength)
        );
    }
}
