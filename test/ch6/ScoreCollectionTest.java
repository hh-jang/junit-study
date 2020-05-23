package ch6;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class ScoreCollectionTest {
    private ScoreCollection collection;

    @BeforeEach
    public void createScoreCollectionInstance() {
        collection = new ScoreCollection();
    }

    @Test
    public void answersArithmeticMeanOfTwoNumbers() {
        // Ready
        collection.add(() -> 5);
        collection.add(() -> 7);

        // Run
        int actualResult = collection.arithmeticMean();

        // Then
        assertThat(actualResult).isEqualTo(6);
//        assertEquals(6, actualResult);
    }

    @Test()
    public void maybeFlawExample() {
        // Ready
        collection.add(() -> 5);
        collection.add(() -> 6);

        // Run
        int actualResult = collection.arithmeticMean();

        // Then -> fail
//        assertEquals(6.5, actualResult);
    }

    @Test
    public void throwsExceptionWhenAddingNull() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> { collection.add(null); });
    }

    @Test
    public void answersZeroWhenNoElementsAdded() {
        assertThat(collection.arithmeticMean()).isEqualTo(0);
    }

    @Test
    public void dealsWithIntegerOverflow() {
        collection.add(() -> Integer.MAX_VALUE);
        collection.add(() -> 2);

        assertThat(collection.arithmeticMean()).isEqualTo(1073741824);
    }
}