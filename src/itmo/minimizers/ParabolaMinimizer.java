package itmo.minimizers;

import itmo.Interval;
import itmo.Oracle;

public class ParabolaMinimizer implements Minimizer {
    @Override
    public Interval minimize(Oracle oracle, double epsilon, double left, double right) {
        double fLeft = oracle.askValue(left);
        double fRight = oracle.askValue(right);
        int iteration = 0;

        while (right - left > epsilon) {
            double mid = (left + right) / 2;
            double fMid = oracle.askValue(mid);
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
            } else if (fu > fMid) {
                right = u;
            } else {
                left = mid;
                right = u;
            }
            iteration++;
        }
        System.out.format("Parabola took %d iterations\n", iteration);
        return new Interval(left, right);
    }
}
