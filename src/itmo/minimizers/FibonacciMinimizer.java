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
        while (fibonacci(n) <= Math.floor((b - a) / epsilon)){
            ++n;
        }

        Point x1 = new Point(a + ((double) fibonacci(n - 2) / fibonacci(n)) * (b - a), oracle);
        Point x2 = new Point(b - x1.getX() + a, oracle);

        if (x2.getX() < x1.getX()){
            Point t = x1;
            x1 = x2;
            x2 = t;
        }

        for (int i = 0; i < n; ++i){

            if (x1.getValue() < x2.getValue()){
                b = x2.getX();
                x2.setX(b - x1.getX() + a);
                if (x2.getX() < x1.getX()) {
                    Point t = x1;
                    x1 = x2;
                    x2 = t;
                }
            } else {
                a = x1.getX();
                x1.setX(b - x2.getX() + a);
                if (x2.getX() < x1.getX()) {
                    Point t = x1;
                    x1 = x2;
                    x2 = t;
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
