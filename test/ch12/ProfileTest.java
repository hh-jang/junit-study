package ch12;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class ProfileTest {
    private Profile profile;
    private BooleanQuestion questionIsRelocation;
    private Answer answerIsRelocation;
    private Answer answerIsNotRelocation;

    @BeforeEach
    public void createProfile() {
        profile = new Profile();
    }

    @BeforeEach
    public void createQuetionAndAnswer() {
        questionIsRelocation = new BooleanQuestion(1, "Relocation package?");
        answerIsRelocation = new Answer(Bool.TRUE, questionIsRelocation);
        answerIsNotRelocation = new Answer(Bool.FALSE, questionIsRelocation);
    }

    @Test
    public void matchesNothingWhenProfileEmpty() {
        // Given
        Criterion criterion = new Criterion(Weight.DontCare, answerIsRelocation);

        // When
        boolean result = profile.matches(criterion);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    public void matchesWhenProfileContainMatchingAnswer() {
        // Given
        profile.add(answerIsRelocation);
        Criterion criterion = new Criterion(Weight.Important, answerIsRelocation);

        // When
        boolean result = profile.matches(criterion);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    public void doesNotMatchWhenNoMatchingAnswer() {
        // Given
        profile.add(answerIsNotRelocation);
        Criterion criterion = new Criterion(Weight.Important, answerIsRelocation);

        // When
        boolean result = profile.matches(criterion);

        // Then
        assertThat(result).isFalse();
    }
}