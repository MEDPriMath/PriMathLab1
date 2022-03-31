package itmo;

public class TestCase {
    private final Oracle oracle;
    private final double a;
    private final double b;
    private final double epsilon;
    private final String description;

    public TestCase(Oracle oracle, double a, double b, double epsilon, String description){
        this.oracle = oracle;
        this.a = a;
        this.b = b;
        this.epsilon = epsilon;
        this.description = description;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public Oracle getOracle() {
        return oracle;
    }

    public double getEpsilon() {
        return epsilon;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
