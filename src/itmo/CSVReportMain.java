package itmo;

import itmo.minimizers.Minimizer;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.List;

import static itmo.Main.MINIMIZERS;

public class CSVReportMain {

    public static void main(String[] args) throws Exception {
        new File("report").mkdir();

        for (Minimizer minimizer : MINIMIZERS) {
            minimizer.minimize(Main.ORACLE, 1E-7, 6, 10);
            List<Interval> lastIntervalList = minimizer.getLastIntervalList();
            Path.of("report", minimizer.getClass().getSimpleName()).toFile().mkdir();

            FileWriter lengthFile = new FileWriter(Path.of("report", minimizer.getClass().getSimpleName(), "len.csv").toFile());
            StringBuilder lengthStringBuilder = new StringBuilder();

            FileWriter aAndBFile = new FileWriter(Path.of("report", minimizer.getClass().getSimpleName(), "aAndB.csv").toFile());
            StringBuilder aAndBStringBuilder = new StringBuilder();

            for (int i = 1; i <= lastIntervalList.size(); i++) {
                Interval interval = lastIntervalList.get(i - 1);
                lengthStringBuilder.append(i).append(";").append(interval.length()).append("\n");
                aAndBStringBuilder.append(i).append(";").append(interval.a).append(";").append(interval.b).append("\n");
            }

            lengthFile.write(lengthStringBuilder.toString().replace('.', ','));
            lengthFile.close();

            aAndBFile.write(aAndBStringBuilder.toString().replace('.', ','));
            aAndBFile.close();
        }

    }
}
