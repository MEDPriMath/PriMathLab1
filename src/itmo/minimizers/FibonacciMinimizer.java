package itmo.minimizers;

import itmo.Interval;
import itmo.oracle.Oracle;

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
            ++n;
        }

        double x1 = a + ((double) fibonacci(n) / (double) fibonacci(n + 2)) * (b - a);
        double x2 = b - x1 + a;

        if (x2 < x1) {
            double t = x1;
            x1 = x2;
            x2 = t;
        }

        double f1 = oracle.askValue(x1);
        double f2 = oracle.askValue(x2);

        reportInterval(a, b);
        for (int i = 0; i < n; i++) {
            if (f1 < f2) {
                b = x2;
                x2 = a + b - x1;
                f2 = oracle.askValue(x2);
                if (x2 < x1) {
                    double t = x1;
                    x1 = x2;
                    x2 = t;
                    t = f1;
                    f1 = f2;
                    f2 = t;
                }
            } else {
                a = x1;
                x1 = a + b - x2;
                f1 = oracle.askValue(x1);
                if (x2 < x1) {
                    double t = x1;
                    x1 = x2;
                    x2 = t;
                    t = f1;
                    f1 = f2;
                    f2 = t;
                }
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
