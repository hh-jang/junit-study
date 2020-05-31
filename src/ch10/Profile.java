package ch10;

public class Profile {
    private AnswerCollection answers;
    private String name;

    public Profile(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void add(Answer answer) {
        answers.add(answer);
    }

    public boolean matches(Criteria criteria) {
        MatchSet matchSet = new MatchSet(answers, criteria);
        return matchSet.matches();
    }

    public MatchSet getMatchSet(Criteria criteria) {
        return new MatchSet(answers, criteria);
    }
}
