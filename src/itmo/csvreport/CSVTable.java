package itmo.csvreport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVTable {
    private final List<List<String>> lines = new ArrayList<>();
    private char delimiter = ';';
    private List<String> header = new ArrayList<>();

    public CSVTable(){
    }

    public CSVTable(List<String> header){
        this.header.addAll(header);
    }

    public void addLine(String... strings){
        if (strings.length > header.size())
            throw new ArrayIndexOutOfBoundsException();

        lines.add(Arrays.asList(strings));
    }

    public void setLine(int index, String... strings){
        if (strings.length > header.size())
            throw new ArrayIndexOutOfBoundsException();

        while (lines.size() <= index)
            lines.add(null);
        lines.set(index, Arrays.asList(strings));
    }

    public void setElement(int indexRow, int indexCol, String string){
        while (lines.size() <= indexRow)
            lines.add(null);

        if (indexCol >= header.size())
            throw new ArrayIndexOutOfBoundsException();

        List<String> newLine = new ArrayList<>(lines.get(indexRow));
        while (newLine.size() <= indexCol)
            newLine.add(null);

        newLine.set(indexCol, string);
        lines.set(indexRow, newLine);
    }

    public void setDelimiter(char delimiter) {
        this.delimiter = delimiter;
    }

    public void setHeader(List<String> header) {
        this.header = header;
    }

    public List<String> getHeader() {
        return header;
    }

    public String toCSV(){
        StringBuilder stringBuilder = new StringBuilder();
        header.forEach(head -> stringBuilder.append(head).append(delimiter));
        stringBuilder.append("\n");

        lines.forEach(line -> {
            int width = header.size();
            for (String value : line) {
                if (value == null)
                    stringBuilder.append(delimiter);
                else
                    stringBuilder.append(value).append(delimiter);
                width--;
            }
            stringBuilder.append(String.valueOf(delimiter).repeat(Math.max(0, width)));
            stringBuilder.append("\n");
        });

        return stringBuilder.toString().replace('.',',').replaceAll(";\n","\n");
    }
}
