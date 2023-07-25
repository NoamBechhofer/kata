package kata.string_calculator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class StringCalculatorTest {
    public long factorial(int n) {
        if (n <= 2) {
            return n;
        }
        return n * factorial(n - 1);
    }

    @Test
    public void getCalledCount() {
        StringCalculator calc = new StringCalculator();
        assertEquals(0, calc.getCalledCount());
        calc.add("1,2");
        assertEquals(1, calc.getCalledCount());
        calc.add("1,2");
        assertEquals(2, calc.getCalledCount());
    }

    @Test
    public void add() {
        final int UPPER_BOUND = 10;
        StringCalculator calc = new StringCalculator();

        String param = "";
        for (int i = 0; i < UPPER_BOUND; i++) {
            long sum = (i * (i + 1)) / 2;
            int result;
            if (i == 0) {
                result = 0;
            } else
                try {
                    result = calc.add(param.substring(0, param.length() - 1));
                } catch (NumberFormatException e) {
                    throw new RuntimeException(e);
                }
            System.out.printf("%d('%s') %d <-> %d%n", i, param, result, sum);
            assertEquals((long) result, (long) sum);
            param += i + 1 + (i % 2 == 0 ? "," : "\n");
        }

        assertEquals(6, calc.add("1\n2,3"));
        assertEquals(3, calc.add("//[;]\n1;2"));
        assertEquals(0, calc.add("//[;]\n"));
        assertEquals(6, calc.add("//[***]\n1***2***3"));
        assertEquals(6, calc.add("//[*][%]\n1*2%3"));

        // ignore numbers > 1000
        assertEquals(2, calc.add("2,1001"));

        assertThrows(NumberFormatException.class, () -> calc.add("a"));
        assertThrows(IllegalArgumentException.class, () -> calc.add("//;1;3")); // missing line break
        assertThrows(/*"negatives not allowed: -3",*/ IllegalArgumentException.class, () -> calc.add("1,2,-3"));
        assertThrows("negatives not allowed: -2, -3", IllegalArgumentException.class, () -> calc.add("1,-2,-3"));
    }
}
