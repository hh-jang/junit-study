package ch2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProfileTest {

    @Test
    public void test() {
        Profile profile = new Profile("Bull Hockey, Inc");
        Question question = new BooleanQuestion(1, "Got Bonuses?");
        Answer profileAnswer = new Answer(Bool.FALSE, question);
        profile.add(profileAnswer);

        Criteria criteria = new Criteria();
        Answer criteriaAnswer = new Answer(Bool.TRUE, question);
        Criterion criterion = new Criterion(Weight.MustMatch, criteriaAnswer);

        criteria.add(criterion);
    }
}