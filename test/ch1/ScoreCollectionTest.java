package ch1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreCollectionTest {
    @Test
    public void answersArithmeticMeanOfTwoNumbers() {
        // Ready
        ScoreCollection collection = new ScoreCollection();
        collection.add(() -> 5);
        collection.add(() -> 7);

        // Run
        int actualResult = collection.arithmeticMean();

        // Then
        assertEquals(6, actualResult);
    }

    @Test
    public void maybeFlawExample() {
        // Ready
        ScoreCollection collection = new ScoreCollection();
        collection.add(() -> 5);
        collection.add(() -> 6);

        // Run
        int actualResult = collection.arithmeticMean();

        // Then -> fail
        assertEquals(6.5, actualResult);
    }
}