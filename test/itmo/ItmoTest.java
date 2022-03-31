package itmo;

import itmo.minimizers.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ItmoTest {

    private static final List<TestCase> testCases = new ArrayList<>();
    private static final List<Minimizer> minimizers = new ArrayList<>();
    private static final List<Arguments> arguments = new ArrayList<>();

    private static final Minimizer dichotomyMinimizer = new DichotomyMinimizer();
    private static final Minimizer goldenRatioMinimizer = new GoldenRatioMinimizer();
    private static final Minimizer fibonacciMinimizer = new FibonacciMinimizer();
    private static final Minimizer parabolaMinimizer = new ParabolaMinimizer();
    private static final Minimizer brentMinimizer = new BrentMinimizer();

    public static List<Arguments> getArguments () {
//        testCases.add(new TestCase((x) -> Math.log(x * x) + 1 - Math.sin(x), 6, 10, 1E-7, "ln(x^2)+1-sin(x)"));
        testCases.add(new TestCase((x) -> x * x, -10, 150, 1E-7, 0, "x ^ 2"));
        testCases.add(new TestCase((x) -> x * x * x - x * x + 1, 0, 10, 1E-7, 2D/3D, "x ^ 3 - x ^ 2 + 1"));

        minimizers.add(dichotomyMinimizer);
        minimizers.add(goldenRatioMinimizer);
        minimizers.add(fibonacciMinimizer);
        minimizers.add(parabolaMinimizer);
        minimizers.add(brentMinimizer);

        testCases.forEach(testCase -> {
            minimizers.forEach(minimizer -> {
                arguments.add(Arguments.of(minimizer, testCase));
            });
        });
        return arguments;
    }

    public static Stream<Arguments> testMinimizerArgs() {
        return getArguments().stream();
    }

    @ParameterizedTest
    @MethodSource("testMinimizerArgs")
    public void testMinimizer(Minimizer minimizer, TestCase testCase) {
        try {
            CountedOracle countedOracle = new CountedOracle(testCase.getOracle());
            Interval interval = minimizer.minimize(countedOracle, testCase.getEpsilon(), testCase.getA(), testCase.getB());
            double minX = (interval.a + interval.b) / 2;
            System.out.printf("Minimizer %s; testCase: %s\nInterval: %s\nMin at point: %f",
                    minimizer.getClass().getSimpleName(),
                    testCase.getDescription(),
                    interval,
                    minX
            );
            Assertions.assertTrue(interval.length() < testCase.getEpsilon());
            Assertions.assertTrue(interval.a <= minX);
            Assertions.assertTrue(interval.b >= minX);
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }
}
