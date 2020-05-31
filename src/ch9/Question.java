package ch9;

public abstract class Question {
    private int id;
    private String text;
    private String[] answerChoice;

    public Question(int id, String text, String[] answerChoice) {
        this.id = id;
        this.text = text;
        this.answerChoice = answerChoice;
    }

    public String getText() {
        return text;
    }

    public String getAnswerChoice(int i) {
        return answerChoice[i];
    }

    public boolean match(Answer answer) {
        return false;
    }

    abstract public boolean match(int expected, int actual);

    public int indexOf(String matchingAnswerChoice) {
        for(int i = 0; i < answerChoice.length; i++) {
            if(answerChoice[i].equals(matchingAnswerChoice)) return i;
        }
        return -1;
    }
}
