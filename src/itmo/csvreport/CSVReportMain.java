package itmo.csvreport;

import itmo.Interval;
import itmo.Main;
import itmo.minimizers.Minimizer;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static itmo.Main.MINIMIZERS;

public class CSVReportMain {

    public static void main(String[] args) throws Exception {
        new File("report").mkdir();

        CSVTable csvTable = new CSVTable(Arrays.asList("index"));
        MINIMIZERS.forEach(minimizer -> minimizer.minimize(Main.ORACLE, 1E-7, 6, 10));
        int maxIter = MINIMIZERS.stream().max(Comparator.comparingInt(minimizer -> minimizer.getLastIntervalList().size()))
                .get().getLastIntervalList().size();

        for (int i = 1; i <= maxIter; i++)
            csvTable.addLine(String.valueOf(i));

        for (Minimizer minimizer : MINIMIZERS) {
            List<String> header = csvTable.getHeader();
            header.add(minimizer.getClass().getSimpleName());
            csvTable.setHeader(header);

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

                csvTable.addElements(i - 1, String.valueOf(interval.length()));
            }

            lengthFile.write(lengthStringBuilder.toString().replace('.', ','));
            lengthFile.close();

            aAndBFile.write(aAndBStringBuilder.toString().replace('.', ','));
            aAndBFile.close();
        }

        FileWriter allLen = new FileWriter(Path.of("report","allLen.csv").toFile());
        allLen.write(csvTable.toCSV());
        allLen.close();
    }
}
