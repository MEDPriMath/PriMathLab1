package itmo.minimizers;

import itmo.Interval;
import itmo.Oracle;

import java.util.Vector;

public class FibonacciMinimizer implements Minimizer {

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
        while (fibonacci(n) < Math.floor((b - a) / epsilon)){
            ++n;
        }

        double x1 = a + ((double) fibonacci(n - 2) / fibonacci(n)) * (b - a);
        double x2 = b - x1 + a;


        if (x2 < x1){
            double t = x1;
            x1 = x2;
            x2 = t;
        }

        boolean x1Bool = false;
        boolean x2Bool = false;

        double x1Val = oracle.askValue(x1);
        double x2Val = oracle.askValue(x2);

        for (int i = 0; i < n; ++i){

            if (x1Bool)
                x1Val = oracle.askValue(x1);
            if (x2Bool)
                x2Val = oracle.askValue(x2);

            if (x1Val < x2Val){
                b = x2;
                x2 = b - x1 + a;
                if (x2 < x1){
                    double t = x1;
                    x1 = x2;
                    x2 = t;

                    t = x1Val;
                    x1Val = x2Val;
                    x2Val = t;
                    x1Bool = true;
                    x2Bool = false;
                } else {
                    x1Bool = false;
                    x2Bool = true;
                }
            } else {
                a = x1;
                x1 = b - x2 + a;
                if (x2 < x1){
                    double t = x1;
                    x1 = x2;
                    x2 = t;

                    t = x1Val;
                    x1Val = x2Val;
                    x2Val = t;
                    x1Bool = false;
                    x2Bool = true;
                } else {
                    x1Bool = true;
                    x2Bool = false;
                }
            }
        }

        return new Interval(a, b);
    }

    public static void main(String[] args){
        FibonacciMinimizer fibonacciMinimizer = new FibonacciMinimizer();
        //fibonacciMinimizer.minimize(null, 1, 1, 7);
        System.out.println(fibonacciMinimizer.fibonacci(50));
    }
}
