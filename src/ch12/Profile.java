package ch12;

import java.util.HashMap;
import java.util.Map;

public class Profile {
    private Map<String, Answer> answers = new HashMap<>();

    public boolean matches(Criterion criterion) {
        return criterion.getWeight() == Weight.DontCare ||
                criterion.getAnswer().match(matchingProfileAnswers(criterion));
    }

    public Answer matchingProfileAnswers(Criterion criterion) {
        return answers.get(criterion.getAnswer().getQuestionText());
    }

    public void add(Answer answer) {
        answers.put(answer.getQuestionText(), answer);
    }

    public boolean matches(Criteria criteria) {
        boolean matches = false;
        for(Criterion criterion : criteria) {
            if (matches(criterion)) {
                matches = true;
            }
            else if(Weight.MustMatch == criterion.getWeight()) {
                return false;
            }
        }
        return matches;
    }
}
