package itmo.oracle;

public class OracleProbe {
    private final Oracle oracle;
    private double x;
    private double value;

    public OracleProbe(Oracle oracle) {
        if (oracle == null) {
            throw new NullPointerException();
        }
        this.oracle = oracle;
    }

    public OracleProbe(Oracle oracle, double x) {
        this.oracle = oracle;
        this.x = x;
        this.value = oracle.askValue(x);
    }

    public void makeProbe(double x) {
        this.x = x;
        this.value = oracle.askValue(x);
    }

    public void set(OracleProbe other) {
        if (!this.oracle.equals(other.oracle))
            throw new IllegalStateException("Cannot clone OracleProbe from probe with another oracle");
        this.x = other.x;
        this.value = other.value;
    }

    public void swap(OracleProbe other) {
        if (!this.oracle.equals(other.oracle))
            throw new IllegalStateException("Cannot swap OracleProbes with different oracles");

        double tmp = this.x;
        this.x = other.getX();
        other.x = tmp;

        tmp = this.value;
        this.value = other.value;
        other.value = tmp;
    }

    public double getX() {
        return x;
    }

    public double getValue() {
        return value;
    }
}
