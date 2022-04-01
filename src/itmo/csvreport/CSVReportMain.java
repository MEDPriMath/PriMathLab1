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

        for (int j = 1; j <= MINIMIZERS.size(); j++) {
            Minimizer minimizer = MINIMIZERS.get(j - 1);

            List<String> header = csvTable.getHeader();
            header.add(minimizer.getClass().getSimpleName());
            csvTable.setHeader(header);

            List<Interval> lastIntervalList = minimizer.getLastIntervalList();
            Path.of("report", minimizer.getClass().getSimpleName()).toFile().mkdir();

            CSVTable lenTable = new CSVTable(Arrays.asList("index", "length"));
            CSVTable abTable = new CSVTable(Arrays.asList("index", "a", "b"));

            for (int i = 1; i <= lastIntervalList.size(); i++) {
                Interval interval = lastIntervalList.get(i - 1);

                lenTable.addLine(String.valueOf(i), String.valueOf(interval.length()));
                abTable.addLine(String.valueOf(i), String.valueOf(interval.a), String.valueOf(interval.b));


                csvTable.addElements(i - 1, j, String.valueOf(interval.length()));
            }

            FileWriter lengthFile = new FileWriter(Path.of("report", minimizer.getClass().getSimpleName(), "len.csv").toFile());
            FileWriter aAndBFile = new FileWriter(Path.of("report", minimizer.getClass().getSimpleName(), "aAndB.csv").toFile());

            lengthFile.write(lenTable.toCSV());
            lengthFile.close();

            aAndBFile.write(abTable.toCSV());
            aAndBFile.close();
        }

        FileWriter allLen = new FileWriter(Path.of("report","allLen.csv").toFile());
        allLen.write(csvTable.toCSV());
        allLen.close();
    }
}
