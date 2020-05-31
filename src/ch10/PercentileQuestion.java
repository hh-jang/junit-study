package ch10;

public class PercentileQuestion extends Question {

    public PercentileQuestion(int id, String text, String[] answerChoice) {
        super(id, text, answerChoice);
    }

    @Override
    public boolean match(int expected, int actual) {
        return expected <= actual;
    }
}
