package itmo.oracle;

public class CountedOracle implements Oracle {

    private final Oracle oracle;
    private int timesUsed = 0;

    public CountedOracle(Oracle oracle) {
        this.oracle = oracle;
    }

    @Override
    public double askValue(double x) {
        timesUsed++;
        return oracle.askValue(x);
    }

    public int getTimesUsed() {
        return timesUsed;
    }
}
