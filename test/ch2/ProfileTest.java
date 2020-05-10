package ch2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProfileTest {
    private Profile profile;
    private Question question;
    private Criteria criteria;

    // Junit5에서 대응되는 annotation
    @BeforeEach
    public void create() {
        this.profile = new Profile("Bull Hockey, Inc");
        this.question = new BooleanQuestion(1, "Got Bonuses?");
        this.criteria = new Criteria();
        // 테스트 코드에 static은 배제 -> 각 테스트별 공유하는 자원이 존재해서 서로 의존적이다? -> 헬게이트
        System.out.println("-----------매번 인스턴스를 생성하는지 확인하기 위해 hashcode 출력-----------");
        System.out.println(this.hashCode());
    }

    @Test
    public void matchAnswerFalseWhenMustMatchCriteriaNoteMet() {
        profile.add(new Answer(Bool.FALSE, question));
        criteria.add(new Criterion(Weight.MustMatch, new Answer(Bool.TRUE, question)));

        boolean matches = profile.matches(criteria);

        assertEquals(false, matches);
    }

    @Test
    public void matchAnswersTrueForAnyDontCareCriteria() {
        profile.add(new Answer(Bool.FALSE, question));
        criteria.add(new Criterion(Weight.DontCare, new Answer(Bool.TRUE, question)));

        boolean match = profile.matches(criteria);

        assertEquals(true, match);
    }
}