package itmo.minimizers;

import itmo.Interval;
import itmo.Oracle;
import itmo.OracleProbe;

public class GoldenRatioMinimizer implements Minimizer {

    private static final double RATIO = 0.38196601125d;

    @Override
    public Interval minimize(Oracle oracle, double epsilon, double a, double b) {
        var p1 = new OracleProbe(oracle, a + (b - a) * RATIO);
        var p2 = new OracleProbe(oracle, b - (b - a) * RATIO);

        int iterations = 0;
        while (b - a > epsilon) {
            iterations++;
            if (p1.getValue() < p2.getValue()) {
                b = p2.getX();
                p2.set(p1);
                p1.makeProbe(a + (b - a) * RATIO);
            } else {
                a = p1.getX();
                p1.set(p2);
                p2.makeProbe(b - (b - a) * RATIO);
            }
        }
        System.out.printf("Golden Ration took %d iterations\n", iterations);
        return new Interval(a, b);
    }

    @Override
    public String toString() {
        return "Golden Ratio Minimizer";
    }
}
