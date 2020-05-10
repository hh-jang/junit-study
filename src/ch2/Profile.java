package ch2;

import com.sun.corba.se.impl.ior.ObjectIdImpl;

import java.util.HashMap;
import java.util.Map;

public class Profile {
    private Map<String, Answer> answerMap = new HashMap<>();
    private int score;
    private String name;

    public Profile(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void add(Answer answer) {
        answerMap.put(answer.getQuestionText(), answer);
    }

    // return false : MustMatch || 모든 case 미매칭인 문항이 미매칭
    // return true : DontCase 하나라도 존재 || 하나라도 케이스 맞음
    public boolean matches(Criteria criteria) {
        score = 0;

        boolean kill = false;
        boolean anyMatch = false;

        for(Criterion criterion : criteria) {
            Answer answer = answerMap.get(
                    criterion.getAnswer().getQuestionText());
            boolean match =
                    criterion.getWeight() == Weight.DontCare ||
                            answer.match(criterion.getAnswer());

            if(match == false && criterion.getWeight() == Weight.MustMatch) {
                kill = true;
            }
            if(match == true && criterion.getWeight() == Weight.MustMatch) {
                score = Weight.MustMatch.getValue();        // overflow를 피하기 위함
                break;
            }
            if(match) {
                score += criterion.getWeight().getValue();
            }
            anyMatch |= match;
        }
        if(kill) return false;
        return anyMatch;
    }

    public int score() {
        return score;
    }
}
