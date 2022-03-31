package itmo.minimizers;

import itmo.Interval;
import itmo.Oracle;

import java.util.ArrayList;
import java.util.List;

public abstract class MinimizerBase implements Minimizer {

    private List<Interval> lastIntervals;

    abstract Interval calcMinimize(Oracle oracle, double epsilon, double a, double b);

    @Override
    public final Interval minimize(Oracle oracle, double epsilon, double a, double b) {
        lastIntervals = new ArrayList<>();
        return calcMinimize(oracle, epsilon, a, b);
    }

    @Override
    public final List<Interval> getLastIntervalList() {
        return new ArrayList<>(lastIntervals);
    }

    protected void reportInterval(double a, double b) {
        lastIntervals.add(new Interval(a, b));
    }

    protected Interval getLastInterval() {
        return lastIntervals.get(lastIntervals.size() - 1);
    }
}
