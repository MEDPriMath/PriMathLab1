package itmo.minimizers;

import itmo.Interval;
import itmo.oracle.Oracle;
import itmo.oracle.OracleProbe;

import java.util.ArrayList;
import java.util.List;

public class FibonacciMinimizer extends MinimizerBase {

    private static final ArrayList<Long> fibMeme = new ArrayList<>(List.of(1L, 1L));

    /**
     * Return nth fibonacci number
     * n = 0; fib = 1
     * n = 1; fib = 1
     * n = 2; fib = 2
     * ...
     *
     * @param n Fibonacci number index
     * @return nth Fibonacci number
     */
    protected static long fibonacci(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Incorrect n for Fibonacci number");
        }

        for (int i = fibMeme.size(); i <= n; i++) {
            fibMeme.add(fibMeme.get(i - 2) + fibMeme.get(i - 1));
        }

        return fibMeme.get(n);
    }

    @Override
    Interval calcMinimize(Oracle oracle, double epsilon, double a, double b) {
        int n = 0;
        while (fibonacci(n) <= (b - a) / epsilon) {
            n++;
        }

        var p1 = new OracleProbe(oracle, a + ((double) fibonacci(n) / (double) fibonacci(n + 2)) * (b - a));
        var p2 = new OracleProbe(oracle, a + (b - p1.getX()));

        reportInterval(a, b);
        for (int i = 0; i < n; i++) {
            if (p1.getValue() < p2.getValue()) {
                b = p2.getX();
                p2.makeProbe(a + b - p1.getX());
            } else {
                a = p1.getX();
                p1.makeProbe(a + b - p2.getX());
            }
            if (p2.getX() < p1.getX()) {
                p1.swap(p2);
            }
            reportInterval(a, b);
        }

        return getLastInterval();
    }

    @Override
    public String toString() {
        return "Fibonacci Minimizer";
    }
}
