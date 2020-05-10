package ch2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProfileTest {

    @Test
    public void matchAnswerFalseWhenMustMatchCriteriaNoteMet() {
        Profile profile = new Profile("Bull Hockey, Inc");
        Question question = new BooleanQuestion(1, "Got Bonuses?");
        Answer profileAnswer = new Answer(Bool.FALSE, question);
        profile.add(profileAnswer);

        Criteria criteria = new Criteria();
        Answer criteriaAnswer = new Answer(Bool.TRUE, question);
        Criterion criterion = new Criterion(Weight.MustMatch, criteriaAnswer);

        criteria.add(criterion);

        boolean matches = profile.matches(criteria);

        assertEquals(false, matches);
    }

    @Test
    public void matchAnswersTrueForAnyDontCareCriteria() {
        Profile profile = new Profile("Bull Hockey, Inc");
        Question question = new BooleanQuestion(1, "Got Milk?");
        Answer profileAnswer = new Answer(Bool.FALSE, question);
        profile.add(profileAnswer);

        Criteria criteria = new Criteria();
        Answer criteriaAnswer = new Answer(Bool.TRUE, question);
        Criterion criterion = new Criterion(Weight.DontCare, criteriaAnswer);
        criteria.add(criterion);

        boolean match = profile.matches(criteria);

        assertEquals(true, match);
    }
}