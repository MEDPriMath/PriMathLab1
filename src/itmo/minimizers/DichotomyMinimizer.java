package itmo.minimizers;

import itmo.Interval;
import itmo.Oracle;

public class DichotomyMinimizer implements Minimizer {
    @Override
    public Interval minimize(Oracle oracle, double epsilon, double a, double b) {
        int iterations = 0;
        while (b - a > epsilon) {
            iterations++;
            var x1 = (a + b) / 2 - epsilon * 0.4;
            var x2 = (a + b) / 2 + epsilon * 0.4;
            var f1 = oracle.askValue(x1);
            var f2 = oracle.askValue(x2);
            if (f1 == f2) {
                return new Interval(x1, x2);
            } else if (f1 < f2) {
                b = x2;
            } else {
                a = x1;
            }
        }
        System.out.printf("Dichotomy took %d iterations and asked the Oracle %d times\n", iterations, iterations * 2);
        return new Interval(a, b);
    }
}
