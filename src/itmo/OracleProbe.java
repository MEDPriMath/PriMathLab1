package itmo;

public class OracleProbe {
    private final Oracle oracle;
    private double x;
    private double value;

    public OracleProbe(Oracle oracle) {
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
        if (this.oracle != other.oracle)
            throw new IllegalStateException("Cannot clone OracleProbe from probe with another oracle");
        this.x = other.getX();
        this.value = other.getValue();
    }

    public double getX() {
        return x;
    }

    public double getValue() {
        return value;
    }
}
