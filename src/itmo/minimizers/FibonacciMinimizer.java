package itmo.minimizers;

import itmo.Interval;
import itmo.Oracle;

import java.util.ArrayList;
import java.util.List;

public class FibonacciMinimizer implements Minimizer {

    /**
     * This class makes process of swap and calculating easier
     */
    private class Point{
        private Double x;
        private Double value;
        private final Oracle oracle;

        public Point(Double x, Double value, Oracle oracle){
            this.x = x;
            this.value = value;
            this.oracle = oracle;
        }

        public Point(double x, Oracle oracle){
            this.x = x;
            this.oracle = oracle;
        }

        public Double getX() {
            return x;
        }

        public Double getValue() {
            if (value == null)
                value = oracle.askValue(x);
            return value;
        }

        public void setX(double x){
            this.x = x;
            value = null;
        }
    }

    @Override
    public Interval minimize(Oracle oracle, double epsilon, double a, double b) {
        int n = 0;
        while (fibonacci(n) <= (b - a) / epsilon){
            ++n;
        }

        double x1 = a + ((double) fibonacci(n) / (double) fibonacci(n + 2)) * (b - a);
        double x2 = b - x1 + a;

        if (x2 < x1){
            double t = x1;
            x1 = x2;
            x2 = t;
        }

        double f1 = oracle.askValue(x1);
        double f2 = oracle.askValue(x2);

        for (int i = 0; i < n; i++){
            if (f1 < f2) {
                b = x2;
                x2 = a + b - x1;
                f2 = oracle.askValue(x2);
                if (x2 < x1){
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
                if (x2 < x1){
                    double t = x1;
                    x1 = x2;
                    x2 = t;
                    t = f1;
                    f1 = f2;
                    f2 = t;
                }
            }
        }

        System.out.printf("Fibonacci took %d iterations\n", n);

        return new Interval(a, b);
    }

    private static final ArrayList<Integer> fibMeme = new ArrayList<>(List.of(1, 1));
    /**
     * Return nth fibonacci number
     * n = 0; fib = 1
     * n = 1; fib = 1
     * n = 2; fib = 2
     * ...
     * @param n Fibonacci number index
     * @return nth Fibonacci number
     */
    private static long fibonacci(int n){
        if (n < 0) {
            throw new IllegalArgumentException("Incorrect n for Fibonacci number");
        }

        for (int i = fibMeme.size(); i <= n; i++) {
            fibMeme.add(fibMeme.get(i - 2) + fibMeme.get(i - 1));
        }

        return fibMeme.get(n);
    }
}
