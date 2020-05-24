package ch7.scratch;

public class Bearing {
    public static final int MAX_VALUE = 359;
    private int value;

    public Bearing(int value) {
        if(value < 0 || value > MAX_VALUE) throw new BearingOutOfRangeException();
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public int angleBetween(Bearing otherBearing) {
        return this.value - otherBearing.getValue();
    }
}
