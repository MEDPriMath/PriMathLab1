package itmo.minimizers;

import itmo.Interval;
import itmo.oracle.Oracle;
import itmo.oracle.OracleProbe;

public class ParabolaMinimizer extends MinimizerBase {
    private static double parabolaVertexX(double x1, double y1, double x2, double y2, double x3, double y3) {
        double a = (x1 * (y2 - y3) - x2 * (y1 - y3) + x3 * (y1 - y2))
                / (x2 - x1) / (x3 * (x3 - x1 - x2) + x1 * x2);
        double b = (y2 - y1) / (x2 - x1) - a * (x1 + x2);
        return -b / 2 / a;
    }

    @Override
    public Interval calcMinimize(Oracle oracle, double epsilon, double a, double b) {
        var p1 = new OracleProbe(oracle, a);
        var p2 = new OracleProbe(oracle, (a + b) / 2);
        var p3 = new OracleProbe(oracle, b);

        var u = new OracleProbe(oracle);

        reportInterval(p1.getX(), p3.getX());
        while (getLastInterval().length() > epsilon) {
            u.makeProbe(parabolaVertexX(
                    p1.getX(), p1.getValue(), p2.getX(), p2.getValue(), p3.getX(), p3.getValue()
            ));
            if (u.getX() < p1.getX() || p3.getX() < u.getX()) {
                throw new IllegalStateException("Meme happened");
            }

            if (u.getX() < p2.getX())
                u.swap(p2);

            if (p2.getValue() > u.getValue()) {
                p1.set(p2);
                p2.set(u);
            } else if (p2.getValue() < u.getValue()) {
                p3.set(u);
            } else {
                p1.set(p2);
                p3.set(u);
            }
            reportInterval(p1.getX(), p3.getX());
        }

        return getLastInterval();
    }

    @Override
    public String toString() {
        return "Parabola Minimizer";
    }
}
