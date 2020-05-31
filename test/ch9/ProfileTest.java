package ch9;

import ch9.Answer;
import ch9.Bool;
import ch9.BooleanQuestion;
import ch9.Criteria;
import ch9.Criterion;
import ch9.Profile;
import ch9.Question;
import ch9.Weight;
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
}