package itmo.minimizers;

import itmo.Interval;
import itmo.Oracle;

import java.util.*;

public class BrentMinimizer implements Minimizer {

    private static final double K = (3 - Math.pow(5, 0.5)) / 2;

    @Override
    public Interval minimize(Oracle oracle, double epsilon, double a, double b) {
        double x, w, v;
        w = v = x = (a + b) / 2;
        double fx, fw, fv;
        fw = fv = fx = oracle.askValue(x);
        double g, d, e;
        d = e = b - a;
        int iterations = 0;
        while (b - a > epsilon) {
            g = e;
            e = d;
            double u;
            if (x != w && x != v && w != v
                    && fx != fw && fx != fv && fw != fv) {
                double[][] temp = new double[][]{{x, fx}, {v, fv}, {w, fw}};
                Arrays.sort(temp, Comparator.comparingDouble(o -> o[0]));
                u = temp[1][0] -
                                (Math.pow((temp[1][0] - temp[0][0]), 2) * (temp[1][1] - temp[2][1]) - Math.pow((temp[1][0] - temp[2][0]), 2) * (temp[1][1] - temp[0][1]))
                                / (2 * ((temp[1][0] - temp[0][0]) * (temp[1][1] - temp[2][1]) - (temp[1][0] - temp[2][0]) * (temp[1][1] - temp[0][1])));
                if (u > a + epsilon && u < b - epsilon && Math.abs(u - x) < g / 2) {
                    d = Math.abs(u - x);
                }
            } else {
                if (x < (a + b) / 2) {
                    u = x + K * (b - x);
                    d = b - x;
                } else {
                    u = x - K * (x - a);
                    d = x - a;
                }
            }
            double fu = oracle.askValue(u);
            if (fu <= fx) {
                if (u >= x) {
                    a = x;
                } else {
                    b = x;
                }
                v = w;
                fv = fw;
                w = x;
                fw = fx;
                x = u;
                fx = fu;
            } else {
                if (u >= x) {
                    b = u;
                } else {
                    a = u;
                }
                if (fu <= fw || w == x) {
                    v = w;
                    fv = fw;
                    w = u;
                    fw = fu;
                } else if (fu <= fv || v == x || v == w) {
                    v = u;
                    fv = fu;
                }
            }
            iterations++;
        }

        System.out.format("Brent took %d iterations\n", iterations);
        return new Interval(a, b);
    }

    @Override
    public String toString() {
        return "Brent Minimizer";
    }
}