package ch7.scratch;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class BearingTest {
    @Test
    public void throwsOnNegativeNumber() {
        assertThatExceptionOfType(BearingOutOfRangeException.class).isThrownBy(() -> {
            new Bearing(-1);});
    }

    @Test
    public void throwsWhenBearingToLarge() {
        assertThatExceptionOfType(BearingOutOfRangeException.class).isThrownBy(() -> {
            new Bearing(Bearing.MAX_VALUE + 1);});
    }

    @Test
    public void answersValidBearing() {
        assertThat(new Bearing(Bearing.MAX_VALUE).getValue()).isEqualTo(Bearing.MAX_VALUE);
    }

    @Test
    public void answersAngleBetweenItAndAnotherBearing() {
        assertThat(new Bearing(15).angleBetween(new Bearing(12))).isEqualTo(3);
    }

    @Test
    public void answersAngleBetweenIsNegativeWhenThisBearingSmaller() {
        assertThat(new Bearing(12).angleBetween(new Bearing(15))).isEqualTo(-3);
    }
}