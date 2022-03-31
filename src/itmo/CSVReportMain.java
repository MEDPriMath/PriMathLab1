package itmo;

import itmo.minimizers.BrentMinimizer;
import itmo.minimizers.DichotomyMinimizer;
import itmo.minimizers.FibonacciMinimizer;
import itmo.minimizers.GoldenRatioMinimizer;
import itmo.minimizers.Minimizer;
import itmo.minimizers.ParabolaMinimizer;

public class CSVReportMain {

    public static final double TRUE_MINIMUM = 7.58722843081143837616523170635146306196;
    private static final Oracle oracle = (x) -> Math.log(x * x) + 1 - Math.sin(x);

    private static final DichotomyMinimizer dichotomyMinimizer = new DichotomyMinimizer();
    private static final Minimizer goldenRatioMinimizer = new GoldenRatioMinimizer();
    private static final Minimizer fibonacciMinimizer = new FibonacciMinimizer();
    private static final Minimizer parabolaMinimizer = new ParabolaMinimizer();
    private static final Minimizer brentMinimizer = new BrentMinimizer();

    public static void main(String[] args) {
        dichotomyMinimizer.minimize(oracle, 1E-7, 6, 10);
        dichotomyMinimizer.getLastIntervalList().forEach(interval -> {
                    System.out.println(interval);
                }
        );
    }
}
