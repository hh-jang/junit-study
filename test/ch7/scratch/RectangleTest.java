package ch7.scratch;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class RectangleTest {
    private Rectangle rectangle;

    @Test
    public void answerArea() {
        rectangle = new Rectangle(new Point(5, 5), new Point(15, 10));
        assertThat(rectangle.area()).isEqualTo(50);
    }

    @Test
    public void allowDynamicallyChangingSize() {
        rectangle = new Rectangle(new Point(5, 5));
        rectangle.setOpposite(new Point(130, 130));
        assertThat(rectangle.area()).isEqualTo(15625);
    }
}