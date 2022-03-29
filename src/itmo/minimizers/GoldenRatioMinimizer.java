package itmo.minimizers;

import itmo.Interval;
import itmo.Oracle;

public class GoldenRatioMinimizer implements Minimizer {

    private static final double RATIO = 0.38196601125d;

    @Override
    public Interval minimize(Oracle oracle, double epsilon, double a, double b) {
        var x1 = a + (b - a) * RATIO;
        var x2 = b - (b - a) * RATIO;
        var f1 = oracle.askValue(x1);
        var f2 = oracle.askValue(x2);

        int iterations = 0;
        while (b - a > epsilon) {
            iterations++;
            if (f1 < f2) {
                b = x2;
                x2 = x1;
                f2 = f1;
                x1 = a + (b - a) * RATIO;
                f1 = oracle.askValue(x1);
            } else {
                a = x1;
                x1 = x2;
                f1 = f2;
                x2 = b - (b - a) * RATIO;
                f2 = oracle.askValue(x2);
            }
        }
        System.out.printf("Golden Ration took %d iterations\n", iterations);
        return new Interval(a, b);
    }
}
