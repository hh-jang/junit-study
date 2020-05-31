package ch9;

public class Criterion implements Scoreable {
    private Weight weight;
    private Answer answer;
    private int score;

    public Criterion(Weight weight, Answer answer) {
        this.weight = weight;
        this.answer = answer;
    }

    public Weight getWeight() {
        return weight;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setScore(int score) {
        this.score = score;
    }

    // 한눈에 안읽히는 로직은 메소드로 분기
    public boolean matches(Answer answer) {
        return getWeight() == Weight.DontCare ||
                answer.match(getAnswer());
    }

    @Override
    public int getScore() {
        return score;
    }
}
