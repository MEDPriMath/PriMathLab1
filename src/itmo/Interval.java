package itmo;

import java.util.Objects;

public class Interval {
    public final double a, b;

    public Interval(double a, double b) {
        if (a > b)
            throw new IllegalArgumentException("'a' must be less than 'b'");
        this.a = a;
        this.b = b;
    }

    public double length() {
        return this.b - this.a;
    }

    public boolean contains(double x) {
        return a <= x && x <= b;
    }

    @Override
    public String toString() {
        return String.format("(%e; %e)", a, b);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interval interval = (Interval) o;
        return Double.compare(interval.a, a) == 0 && Double.compare(interval.b, b) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }
}
