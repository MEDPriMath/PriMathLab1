package itmo.minimizers;

import itmo.Interval;
import itmo.Oracle;
import itmo.OracleProbe;

import java.util.ArrayList;
import java.util.List;

public class DichotomyMinimizer implements Minimizer {
    private List<Interval> intervalList = new ArrayList<>();

    @Override
    public Interval minimize(Oracle oracle, double epsilon, double a, double b) {
        intervalList.clear();
        var p1 = new OracleProbe(oracle);
        var p2 = new OracleProbe(oracle);

        int iterations = 0;
        while (b - a > epsilon) {
            reportInterval(a, b);
            iterations++;
            p1.makeProbe((a + b) / 2 - epsilon * 0.4);
            p2.makeProbe((a + b) / 2 + epsilon * 0.4);
            if (p1.getValue() == p2.getValue()) {
                reportInterval(p1.getX() , p2.getX());
                return new Interval(p1.getX(), p2.getX());
            } else if (p1.getValue() < p2.getValue()) {
                b = p2.getX();
            } else {
                a = p1.getX();
            }
        }
        reportInterval(a , b);

        System.out.printf("Dichotomy took %d iterations\n", iterations);
        return new Interval(a, b);
    }

    private void reportInterval(double a, double b) {
        intervalList.add(new Interval(a, b));
    }

    public List<Interval> getLastIntervalList() {
        return intervalList;
    }

    @Override
    public String toString() {
        return "Dichotomy Minimizer";
    }
}
