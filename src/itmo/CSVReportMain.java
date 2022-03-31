package itmo;

import itmo.minimizers.BrentMinimizer;
import itmo.minimizers.DichotomyMinimizer;
import itmo.minimizers.FibonacciMinimizer;
import itmo.minimizers.GoldenRatioMinimizer;
import itmo.minimizers.Minimizer;
import itmo.minimizers.ParabolaMinimizer;

import javax.sound.midi.spi.MidiFileWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CSVReportMain {

    public static final double TRUE_MINIMUM = 7.58722843081143837616523170635146306196;
    private static final Oracle oracle = (x) -> Math.log(x * x) + 1 - Math.sin(x);

    private static final Minimizer dichotomyMinimizer = new DichotomyMinimizer();
    private static final Minimizer goldenRatioMinimizer = new GoldenRatioMinimizer();
    private static final Minimizer fibonacciMinimizer = new FibonacciMinimizer();
    private static final Minimizer parabolaMinimizer = new ParabolaMinimizer();
    private static final Minimizer brentMinimizer = new BrentMinimizer();

    private static final List<Minimizer> minimizers = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        minimizers.add(dichotomyMinimizer);
        minimizers.add(goldenRatioMinimizer);
        minimizers.add(fibonacciMinimizer);
        minimizers.add(parabolaMinimizer);
        minimizers.add(brentMinimizer);

        new File("report").mkdir();
        minimizers.forEach(minimizer -> {
            try {
                minimizer.minimize(oracle, 1E-7, 6, 10);
                List<Interval> lastIntervalList = minimizer.getLastIntervalList();
                Path.of("report", minimizer.getClass().getSimpleName()).toFile().mkdir();

                FileWriter lengthFile = new FileWriter(Path.of("report", minimizer.getClass().getSimpleName(), "len.csv").toFile());
                StringBuilder lengthStringBuilder = new StringBuilder();

                FileWriter aAndBFile = new FileWriter(Path.of("report", minimizer.getClass().getSimpleName(), "aAndB.csv").toFile());
                StringBuilder aAndBStringBuilder = new StringBuilder();

                for (int i = 1; i <= lastIntervalList.size(); i++){
                    Interval interval = lastIntervalList.get(i - 1);
                    lengthStringBuilder.append(i).append(";").append(interval.length()).append("\n");
                    aAndBStringBuilder.append(i).append(";").append(interval.a).append(";").append(interval.b).append("\n");
                }

                lengthFile.write(lengthStringBuilder.toString().replace('.',','));
                lengthFile.close();

                aAndBFile.write(aAndBStringBuilder.toString().replace('.',','));
                aAndBFile.close();
            } catch (Exception e){
                e.getMessage();
            }
        });

    }
}
