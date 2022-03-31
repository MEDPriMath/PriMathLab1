package itmo;

import itmo.minimizers.BrentMinimizer;
import itmo.minimizers.DichotomyMinimizer;
import itmo.minimizers.FibonacciMinimizer;
import itmo.minimizers.GoldenRatioMinimizer;
import itmo.minimizers.Minimizer;
import itmo.minimizers.ParabolaMinimizer;
import itmo.oracle.CountedOracle;
import itmo.oracle.Oracle;

import java.util.List;

public class Main {

    /**
     * Поменяешь main
     * мать сгорит
     */

    public static final double MINIMUM = 7.58722843081143837616523170635146306196;
    public static final Oracle ORACLE = (x) -> Math.log(x * x) + 1 - Math.sin(x);
    public static final List<Minimizer> MINIMIZERS = List.of(
            new DichotomyMinimizer(),
            new GoldenRatioMinimizer(),
            new FibonacciMinimizer(),
            new ParabolaMinimizer(),
            new BrentMinimizer());

    public static void main(String[] args) {
        for (Minimizer minimizer : MINIMIZERS) {
            testMinimizer(minimizer);
        }
    }

    private static void testMinimizer(Minimizer minimizer) {
        try {
            for (double epsilon = 1E-7; epsilon >= 1E-7; epsilon /= 10) {
                CountedOracle countedOracle = new CountedOracle(ORACLE);
                Interval interval = minimizer.minimize(countedOracle, epsilon, 6, 10);
                var intervals = minimizer.getLastIntervalList();
                System.out.printf("Found interval: %s, asked the Oracle: %d times, it took %d iterations\n",
                        interval,
                        countedOracle.getTimesUsed(),
                        intervals.size());
            }
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
            e.printStackTrace();
        }
    }
}
