package ch4;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class ProfileTest {

    @Test
    public void matches() {
        Profile profile = new Profile("Bull Hockey, Inc");
        Question question = new BooleanQuestion(1, "Got milk");

        // mush-match 항목이 맞지 않으면 false
        profile.add(new Answer(Bool.FALSE, question));
        Criteria criteria = new Criteria();
        criteria.add(new Criterion(Weight.MustMatch, new Answer(Bool.TRUE, question)));

        assertThat(profile.matches(criteria)).isFalse();

        // don't card 항목에 대해서는 true
        profile.add(new Answer(Bool.FALSE, question));
        criteria = new Criteria();
        criteria.add(new Criterion(Weight.DontCare, new Answer(Bool.TRUE, question)));

        assertThat(profile.matches(criteria)).isTrue();
    }
}