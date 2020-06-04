package ch12;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class ProfileTest {
    @Test
    public void matchesNothingWhenProfileEmpty() {
        // Given
        Profile profile = new Profile();
        Question question = new BooleanQuestion(1, "Relocation package?");
        Criterion criterion = new Criterion(Weight.DontCare, new Answer(Bool.TRUE, question));

        // When
        boolean result = profile.matches(criterion);

        // Then
        assertThat(result).isFalse();
    }
}