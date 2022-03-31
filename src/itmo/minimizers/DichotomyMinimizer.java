package itmo.minimizers;

import itmo.Interval;
import itmo.Oracle;
import itmo.OracleProbe;

public class DichotomyMinimizer extends MinimizerBase {

    @Override
    Interval calcMinimize(Oracle oracle, double epsilon, double a, double b) {
        var p1 = new OracleProbe(oracle);
        var p2 = new OracleProbe(oracle);

        reportInterval(a, b);
        while (getLastInterval().length() > epsilon) {
            p1.makeProbe((a + b) / 2 - epsilon * 0.4);
            p2.makeProbe((a + b) / 2 + epsilon * 0.4);
            if (p1.getValue() == p2.getValue()) {
                reportInterval(p1.getX(), p2.getX());
            } else if (p1.getValue() < p2.getValue()) {
                b = p2.getX();
            } else {
                a = p1.getX();
            }
            reportInterval(a, b);
        }

        return getLastInterval();
    }

    @Override
    public String toString() {
        return "Dichotomy Minimizer";
    }
}
