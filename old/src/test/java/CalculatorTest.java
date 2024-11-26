import ru.vsu.cs.kostryukov.ICalculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CalculatorTest {
    @Test
    public void testGetFour() {
        Assertions.assertEquals(4, ICalculator.getFour());
    }

    @Test
    public void testDivideByZero() {
        Assertions.assertThrows(ArithmeticException.class, () -> ICalculator.divide(1.7, 0));
    }

    @Test
    public void testDivide() {
        Assertions.assertEquals(1.5, ICalculator.divide(3, 2));
    }

    @Test
    public void testAdd() {
        Assertions.assertEquals(5.1, ICalculator.add(3, 2.1));
    }

    @Test
    public void testSubtract() {
        Assertions.assertEquals(5.5, ICalculator.subtract(8, 2.5));
    }
    @Test
    public void testMultiply() {
        Assertions.assertEquals(5.02, ICalculator.multiply(2.51, 2));
    }
}
