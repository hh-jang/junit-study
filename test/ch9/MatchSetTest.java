package ch9;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchSetTest {
    private Criteria criteria;

    private Question bonusQuestion;
    private Answer bonusAnswerTrue;
    private Answer bonusAnswerFalse;

    private Question offerFoodQuestion;
    private Answer offerFoodAnswerTrue;
    private Answer offerFoodAnswerFalse;

    private Question offerEducationQuestion;
    private Answer offerEducationAnswerTrue;
    private Answer offerEducationAnswerFalse;

    private AnswerCollection answers;

    @BeforeEach
    public void createAnswers() {
        answers = new AnswerCollection();
    }

    @BeforeEach
    public void createCriteria() {
        criteria = new Criteria();
    }

    @BeforeEach
    public void createQuestionAndAnswer() {
        this.bonusQuestion = new BooleanQuestion(1, "보너스 주나여");
        this.bonusAnswerTrue = new Answer(Bool.TRUE, bonusQuestion);
        this.bonusAnswerFalse = new Answer(Bool.FALSE, bonusQuestion);

        this.offerFoodQuestion = new BooleanQuestion(1, "밥 주나여");
        this.offerFoodAnswerTrue = new Answer(Bool.TRUE, offerFoodQuestion);
        this.offerFoodAnswerFalse = new Answer(Bool.FALSE, offerFoodQuestion);

        this.offerEducationQuestion = new BooleanQuestion(1, "자기학습을 위해 지원 해주나여");
        this.offerEducationAnswerTrue = new Answer(Bool.TRUE, offerEducationQuestion);
        this.offerEducationAnswerFalse = new Answer(Bool.FALSE, offerEducationQuestion);
    }

    private void add(Answer answer) {
        answers.add(answer);
    }

    private MatchSet createMatchSet() {
        return new MatchSet(answers, criteria);
    }


    @Test
    public void matchAnswerFalseWhenMustMatchCriteriaNoteMet() {
        add(new Answer(Bool.FALSE, bonusQuestion));
        criteria.add(new Criterion(Weight.MustMatch, new Answer(Bool.TRUE, bonusQuestion)));

        assertEquals(false, createMatchSet().matches());
    }

    @Test
    public void matchAnswersTrueForAnyDontCareCriteria() {
        add(new Answer(Bool.FALSE, bonusQuestion));
        criteria.add(new Criterion(Weight.DontCare, new Answer(Bool.TRUE, bonusQuestion)));

        assertEquals(true, createMatchSet().matches());
    }

    @Test
    public void anyAnswerMatchedMultipleMatch() {
        add(bonusAnswerTrue);
        add(offerFoodAnswerFalse);

        criteria.add(new Criterion(Weight.Important, bonusAnswerTrue));
        criteria.add(new Criterion(Weight.Important, bonusAnswerFalse));

        assertEquals(true, createMatchSet().matches());
    }

    @Test
    public void allAnswerNotMatchedMultipleMatch() {
        add(bonusAnswerFalse);
        add(offerFoodAnswerFalse);

        criteria.add(new Criterion(Weight.Important, bonusAnswerTrue));
        criteria.add(new Criterion(Weight.Important, offerFoodAnswerTrue));

        assertEquals(false, createMatchSet().matches());
    }

    @Test
    public void scoreIsZeroWhenNoMatches() {
        add(bonusAnswerFalse);
        criteria.add(new Criterion(Weight.Important, bonusAnswerTrue));

        assertEquals(0, createMatchSet().getScore());
        assertEquals(false, createMatchSet().matches());
    }

    @Test
    public void scoreEqualToCriteriaValueWhenMatches() {
        add(bonusAnswerFalse);

        criteria.add(new Criterion(Weight.Important, bonusAnswerFalse));

        assertEquals(Weight.Important.getValue(), createMatchSet().getScore());
        assertEquals(true, createMatchSet().matches());
    }

    @Test
    public void scoreSumEqualToSumOfCriteriaValues() {
        add(bonusAnswerFalse);
        add(offerFoodAnswerFalse);
        add(offerEducationAnswerFalse);

        criteria.add(new Criterion(Weight.Important, bonusAnswerTrue));
        criteria.add(new Criterion(Weight.VeryImportant, offerFoodAnswerFalse));
        criteria.add(new Criterion(Weight.WouldPrefer, offerEducationAnswerFalse));

        createMatchSet().matches();

        int expectScore = Weight.VeryImportant.getValue() + Weight.WouldPrefer.getValue();
//        현재 코드 문제 점 : 가중치 중 MustMatch가 하나인 상태에서 다른 조건도 matching 되면 score가 overflow 발생(음수)
//        must match가 있을 시에 score의 값은 Integer.MAX_VALUE 고정으로 해야할듯
//        int expectScore = Weight.VeryImportant.getValue() + Weight.MustMatch.getValue();
//        System.out.println("score : " + expectScore);

        assertEquals(expectScore, createMatchSet().getScore());
    }

    @Test
    public void sumOfScoreMustPositiveOrZero() {
        add(bonusAnswerFalse);
        add(offerFoodAnswerFalse);
        add(offerEducationAnswerFalse);

        criteria.add(new Criterion(Weight.Important, bonusAnswerTrue));
        criteria.add(new Criterion(Weight.VeryImportant, offerFoodAnswerFalse));
        criteria.add(new Criterion(Weight.MustMatch, offerEducationAnswerFalse));

        createMatchSet().matches();

        assertEquals(true, createMatchSet().getScore() >= 0);
    }
}