package itmo;

import itmo.minimizers.*;

import java.util.ArrayList;
import java.util.List;

public class ItmoTest {

    private static final List<TestCase> testCases = new ArrayList<>();
    private static final List<Minimizer> minimizers = new ArrayList<>();

    private static final Minimizer dichotomyMinimizer = new DichotomyMinimizer();
    private static final Minimizer goldenRatioMinimizer = new GoldenRatioMinimizer();
    private static final Minimizer fibonacciMinimizer = new FibonacciMinimizer();
    private static final Minimizer parabolaMinimizer = new ParabolaMinimizer();
    private static final Minimizer brentMinimizer = new BrentMinimizer();

    public static void main(String[] args) {
        testCases.add(new TestCase((x) -> Math.log(x * x) + 1 - Math.sin(x), 6, 10, 1E-7, "ln(x^2)+1-sin(x)"));
        testCases.add(new TestCase((x) -> x * x, -10, 150, 1E-7, "x ^ 2"));
        testCases.add(new TestCase((x) -> x * x * x - x * x + 1, 0, 10, 1E-7, "x ^ 3 - x ^ 2 + 1"));

//        minimizers.add(dichotomyMinimizer);
//        minimizers.add(goldenRatioMinimizer);
        minimizers.add(fibonacciMinimizer);
//        minimizers.add(parabolaMinimizer);
//        minimizers.add(brentMinimizer);

        testCases.forEach(testCase -> {
            minimizers.forEach(minimizer -> testMinimizer(minimizer, testCase));
        });
    }

    private static void testMinimizer(Minimizer minimizer, TestCase testCase) {
        try {
            CountedOracle countedOracle = new CountedOracle(testCase.getOracle());
            Interval interval = minimizer.minimize(countedOracle, testCase.getEpsilon(), testCase.getA(), testCase.getB());
            System.out.printf("Test case: %s\nFound interval: %s, asked the Oracle: %d times\n",
                    testCase.getDescription(),
                    interval,
                    countedOracle.getTimesUsed());
            if (interval.length() > testCase.getEpsilon())
                throw new Exception("Interval bigger than epsilon");
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }
}
