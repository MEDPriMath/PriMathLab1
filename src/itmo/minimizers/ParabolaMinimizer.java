package itmo.minimizers;

import itmo.Interval;
import itmo.Oracle;

public class ParabolaMinimizer implements Minimizer {
    @Override
    public Interval minimize(Oracle oracle, double epsilon, double left, double right) {
        double fLeft = oracle.askValue(left);
        double fRight = oracle.askValue(right);
        double mid = (left + right) / 2;
        double fMid = oracle.askValue(mid);
        int iteration = 0;

        while (right - left > epsilon) {
            var u = mid -
                    (Math.pow((mid - left), 2) * (fMid - fRight) - Math.pow((mid - right), 2) * (fMid - fLeft))
                    / (2 * ((mid - left) * (fMid - fRight) - (mid - right) * (fMid - fLeft)) );
            if (u > right || u < left) {
                throw new ArithmeticException("Meme happened");
            }
            double fu = oracle.askValue(u);

            if (u < mid) {
                var temp = mid;
                mid = u;
                u = temp;
                temp = fMid;
                fMid = fu;
                fu = temp;
            }

            if (fu < fMid) {
                left = mid;
                fLeft = fMid;
                mid = u;
                fMid = fu;
            } else if (fu > fMid) {
                right = u;
                fRight = fu;
            } else {
                left = mid;
                right = u;
                mid = (left + right) / 2;
                fMid = oracle.askValue(mid);
            }
            iteration++;
        }
        System.out.format("Parabola took %d iterations\n", iteration);
        return new Interval(left, right);
    }
}
