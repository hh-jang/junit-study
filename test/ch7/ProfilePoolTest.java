package ch7;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ProfilePoolTest {
    private ProfilePool profiles;
    private Profile kakao;
    private Profile naver;
    private BooleanQuestion doTheyReimburseTuition;

    @BeforeEach
    public void create() {
        profiles = new ProfilePool();
        kakao = new Profile("KAKAO");
        naver = new Profile("NAVER");
        doTheyReimburseTuition = new BooleanQuestion(1, "Reimburse tuition?");
    }

    @Test
    public Criteria soleNeed(Question question, int value, Weight weight) {
        Criteria criteria = new Criteria();
        criteria.add(new Criterion(weight, new Answer(value, question)));
        return criteria;
    }

    @Test
    public void answersResultsInScoredOrder() {
        kakao.add(new Answer(Bool.FALSE, doTheyReimburseTuition));
        profiles.add(kakao);
        naver.add(new Answer(Bool.TRUE, doTheyReimburseTuition));
        profiles.add(naver);

        profiles.score(soleNeed(doTheyReimburseTuition, Bool.TRUE, Weight.Important));
        List<Profile> ranked = profiles.ranked();

        assertThat(ranked.toArray()).containsExactly(new Profile[] {naver, kakao});
    }
}