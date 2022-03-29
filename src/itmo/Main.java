package itmo;

import itmo.minimizers.BrentMinimizer;
import itmo.minimizers.DichotomyMinimizer;
import itmo.minimizers.FibonacciMinimizer;
import itmo.minimizers.GoldenRatioMinimizer;
import itmo.minimizers.Minimizer;
import itmo.minimizers.ParabolaMinimizer;

import java.util.Arrays;

public class Main {

    /**
     * Поменяешь main
     * мать сгорит
     */

    private static final Oracle oracle = (x) -> Math.log(x * x) + 1 - Math.sin(x);

    private static final Minimizer dichotomyMinimizer = new DichotomyMinimizer();
    private static final Minimizer goldenRatioMinimizer = new GoldenRatioMinimizer();
    private static final Minimizer fibonacciMinimizer = new FibonacciMinimizer();
    private static final Minimizer parabolaMinimizer = new ParabolaMinimizer();
    private static final Minimizer brentMinimizer = new BrentMinimizer();

    public static void main(String[] args) {
        for (double epsilon = 1; epsilon > 1E-7; epsilon /= 10) {
            Interval interval = dichotomyMinimizer.minimize(oracle, epsilon, 6, 10);
            System.out.println(interval);
        }
    }
}
