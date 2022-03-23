import java.util.Arrays;
import java.util.function.Function;

public class Main {

    public static void main(String[] args) {
        for (double epsilon = 1; epsilon > 1E-7; epsilon /= 10) {
            double[] range = dichotomy(Main::f, epsilon, 6, 10);
            System.out.println(Arrays.toString(range));
        }
    }

    public static double f(double x) {
        return Math.log(x * x) + 1 - Math.sin(x);
    }

    public static double[] dichotomy(Function<Double, Double> oracle, double epsilon, double a, double b) {
        int iterations = 0;
        while (b - a > epsilon) {
            iterations++;
            var x1 = (a + b) / 2 - epsilon * 0.4;
            var x2 = (a + b) / 2 + epsilon * 0.4;
            var f1 = oracle.apply(x1);
            var f2 = oracle.apply(x2);
            if (f1.equals(f2)) {
                return new double[]{x1, x2};
            } else if (f1 < f2) {
                b = x2;
            } else {
                a = x1;
            }
        }
        System.out.printf("Dichotomy took %d iterations and asked the Oracle %d times\n", iterations, iterations * 2);
        return new double[]{a, b};
    }

}
