package itmo.minimizers;

import itmo.Interval;
import itmo.Oracle;

import java.util.ArrayList;

public class FibonacciMinimizer implements Minimizer {

    private static final ArrayList<Long> memory = new ArrayList<>();

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

        while (memory.size() < n + 1)
            memory.add(null);

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
}
