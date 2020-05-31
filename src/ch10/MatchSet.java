package ch10;

public class MatchSet {
    private AnswerCollection answers;
    private Criteria criteria;

    public MatchSet(AnswerCollection answers, Criteria criteria) {
        this.answers = answers;
        this.criteria = criteria;
    }

    public boolean matches() {
        // 리팩토링을 하면서 반복문이 여러개되면서 실행시간이 4배가 되었지만, 성능 최적화가 반드시 필요한 로직이 아니면 차라리 코드를 깔끔하게 하는게 낫다
        // 왜냐? -> 깔끔하면 어차피 최적화가 필요해질 때에도 진행에 있어 유리하다.
        if(doesNotMeetAnyMustMatchCriterion(criteria)) return false;
        return anyMatches(criteria);
    }

    private boolean doesNotMeetAnyMustMatchCriterion(Criteria criteria) {
        for(Criterion criterion : criteria) {
            boolean match = criterion.matches(answerMatching(criterion));
            if(match == false && criterion.getWeight() == Weight.MustMatch) return true;
        }
        return false;
    }

    private boolean anyMatches(Criteria criteria) {
        boolean anyMatches = false;
        for(Criterion criterion : criteria)
            anyMatches |= criterion.matches(answerMatching(criterion));
        return anyMatches;
    }

    private Answer answerMatching(Criterion criterion) {
        return answers.answerMatching(criterion);
    }

    public int getScore() {
        int score = 0;
        for(Criterion criterion : criteria) {
            boolean match = criterion.matches(answerMatching(criterion));

            if(match == true && criterion.getWeight() == Weight.MustMatch) {
                score = Weight.MustMatch.getValue();        // overflow를 피하기 위함
                break;
            }
            if(match) {
                score += criterion.getWeight().getValue();
            }
        }
        return score;
    }
}
