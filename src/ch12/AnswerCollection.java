package ch12;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AnswerCollection {
    private Map<String, Answer> answerMap = new HashMap<>();

    public void add(Answer answer) {
        answerMap.put(answer.getQuestionText(), answer);
    }

    public Answer answerMatching(Criterion criterion) {
        return answerMap.get(criterion.getAnswer().getQuestionText());
    }

    public List<Answer> find(Predicate<Answer> pred) {
        return answerMap.values().stream()
                .filter(pred)
                .collect(Collectors.toList());
    }
}
