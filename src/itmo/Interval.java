package itmo;

public class Interval {
    private Double minimum = null;
    private Double maximum = null;

    public Interval(double minimum, double maximum){
        setMinimum(minimum);
        setMaximum(maximum);
    }

    public void setMinimum(double minimum) {
        if (maximum != null && minimum > maximum)
            throw new ArithmeticException("min greater than max");
        this.minimum = minimum;
    }

    public void setMaximum(double maximum) {
        if (minimum != null && minimum > maximum)
            throw new ArithmeticException("min greater than max");
        this.maximum = maximum;
    }

    public double getMinimum() {
        return minimum;
    }

    public double getMaximum() {
        return maximum;
    }

    public double length(){
        return this.maximum - this.minimum;
    }

    @Override
    public String toString() {
        return "(" + minimum + "; " + maximum + ")";
    }
}
