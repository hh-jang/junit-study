package ch8;

import ch8.Bool;
import ch8.BooleanQuestion;
import ch8.Criteria;
import ch8.Criterion;
import ch8.Profile;
import ch8.Question;
import ch8.Weight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfileTest {
    private Profile profile;
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

    // Junit5에서 대응되는 annotation
    @BeforeEach
    public void createProfileAndCriteria() {
        this.profile = new Profile("Bull Hockey, Inc");
        this.criteria = new Criteria();
        // 테스트 코드에 static은 배제 -> 각 테스트별 공유하는 자원이 존재해서 서로 의존적이다? -> 헬게이트
        System.out.println("-----------매번 인스턴스를 생성하는지 확인하기 위해 hashcode 출력-----------");
        System.out.println("hashCode : " + this.hashCode());
    }

    @Test
    public void matchAnswerFalseWhenMustMatchCriteriaNoteMet() {
        profile.add(new Answer(Bool.FALSE, bonusQuestion));
        criteria.add(new Criterion(Weight.MustMatch, new Answer(Bool.TRUE, bonusQuestion)));

        boolean matches = profile.matches(criteria);

        assertEquals(false, matches);
    }

    @Test
    public void matchAnswersTrueForAnyDontCareCriteria() {
        profile.add(new Answer(Bool.FALSE, bonusQuestion));
        criteria.add(new Criterion(Weight.DontCare, new Answer(Bool.TRUE, bonusQuestion)));

        boolean match = profile.matches(criteria);

        assertEquals(true, match);
    }

    @Test
    public void anyAnswerMatchedMultipleMatch() {
        profile.add(bonusAnswerTrue);
        profile.add(offerFoodAnswerFalse);

        criteria.add(new Criterion(Weight.Important, bonusAnswerTrue));
        criteria.add(new Criterion(Weight.Important, bonusAnswerFalse));

        boolean match = profile.matches(criteria);

        assertEquals(true, match);
    }

    @Test
    public void allAnswerNotMatchedMultipleMatch() {
        profile.add(bonusAnswerFalse);
        profile.add(offerFoodAnswerFalse);

        criteria.add(new Criterion(Weight.Important, bonusAnswerTrue));
        criteria.add(new Criterion(Weight.Important, offerFoodAnswerTrue));

        boolean match = profile.matches(criteria);

        assertEquals(false, match);
    }

    @Test
    public void scoreIsZeroWhenNoMatches() {
        profile.add(bonusAnswerFalse);

        criteria.add(new Criterion(Weight.Important, bonusAnswerTrue));

        boolean match = profile.matches(criteria);

        assertEquals(0, profile.score());
        assertEquals(false, match);
    }

    @Test
    public void scoreEqualToCriteriaValueWhenMatches() {
        profile.add(bonusAnswerFalse);

        criteria.add(new Criterion(Weight.Important, bonusAnswerFalse));

        boolean match = profile.matches(criteria);

        assertEquals(Weight.Important.getValue(), profile.score());
        assertEquals(true, match);
    }

    @Test
    public void scoreSumEqualToSumOfCriteriaValues() {
        profile.add(bonusAnswerFalse);
        profile.add(offerFoodAnswerFalse);
        profile.add(offerEducationAnswerFalse);

        criteria.add(new Criterion(Weight.Important, bonusAnswerTrue));
        criteria.add(new Criterion(Weight.VeryImportant, offerFoodAnswerFalse));
        criteria.add(new Criterion(Weight.WouldPrefer, offerEducationAnswerFalse));
//        criteria.add(new Criterion(Weight.MustMatch, offerEducationAnswerFalse));

        profile.matches(criteria);

        int expectScore = Weight.VeryImportant.getValue() + Weight.WouldPrefer.getValue();
//        현재 코드 문제 점 : 가중치 중 MustMatch가 하나인 상태에서 다른 조건도 matching 되면 score가 overflow 발생(음수)
//        must match가 있을 시에 score의 값은 Integer.MAX_VALUE 고정으로 해야할듯
//        int expectScore = Weight.VeryImportant.getValue() + Weight.MustMatch.getValue();
//        System.out.println("score : " + expectScore);

        assertEquals(expectScore, profile.score());
    }

    @Test
    public void sumOfScoreMustPositiveOrZero() {
        profile.add(bonusAnswerFalse);
        profile.add(offerFoodAnswerFalse);
        profile.add(offerEducationAnswerFalse);

        criteria.add(new Criterion(Weight.Important, bonusAnswerTrue));
        criteria.add(new Criterion(Weight.VeryImportant, offerFoodAnswerFalse));
        criteria.add(new Criterion(Weight.MustMatch, offerEducationAnswerFalse));

        profile.matches(criteria);
        System.out.println(profile.score());

        assertEquals(true, profile.score() >= 0);
    }
}