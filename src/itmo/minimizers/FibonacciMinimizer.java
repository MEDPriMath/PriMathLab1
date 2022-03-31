package itmo.minimizers;

import itmo.Interval;
import itmo.Oracle;

import java.util.Vector;

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

    private static final Vector<Long> memory = new Vector<>();

    /**
     * Return fibonacci n number
     * n = 0; fib = 1
     * n = 1; fib = 1
     * n = 2; fib = 2
     * n = 3; fib = 3
     * n = 4; fib = 5
     * ...
     * @param n number of Fibonacci number
     * @return Fibonacci number
     */
    private long fibonacci(int n){
        if (n < 0)
            throw new ArithmeticException("Incorrect n for Fibonacci number");

        if (memory.size() > n && memory.get(n) != null)
            return memory.get(n);

        if (memory.size() < n + 1)
            memory.setSize(n + 1);

        if (n == 0 || n == 1){
            memory.set(n, 1L);
            return 1;
        }

        memory.set(n, fibonacci(n - 1) + fibonacci(n - 2));
        return memory.get(n);
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
}
