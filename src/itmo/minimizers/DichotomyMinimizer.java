package itmo.minimizers;

import itmo.Interval;
import itmo.Oracle;
import itmo.OracleProbe;

public class DichotomyMinimizer implements Minimizer {
    @Override
    public Interval minimize(Oracle oracle, double epsilon, double a, double b) {
        var p1 = new OracleProbe(oracle);
        var p2 = new OracleProbe(oracle);

        int iterations = 0;
        while (b - a > epsilon) {
            iterations++;
            p1.makeProbe((a + b) / 2 - epsilon * 0.4);
            p2.makeProbe((a + b) / 2 + epsilon * 0.4);
            if (p1.getValue() == p2.getValue()) {
                return new Interval(p1.getX(), p2.getX());
            } else if (p1.getValue() < p2.getValue()) {
                b = p2.getX();
            } else {
                a = p1.getX();
            }
        }
        System.out.printf("Dichotomy took %d iterations\n", iterations);
        return new Interval(a, b);
    }
}
