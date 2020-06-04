package ch12;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Person {
    private List<Question> charateristics = new ArrayList<>();

    public void add(Question question) {
        charateristics.add(question);
    }

    public List<Question> getCharateristics() {
        return charateristics;
    }

    public List<Question> withCharacteristic(String questionPattern) {
        return charateristics.stream().filter(c -> c.getText().endsWith(questionPattern)).collect(Collectors.toList());
    }
}
