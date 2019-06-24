package example;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import com.github.davidmoten.bigsorter.Serializer;
import com.github.davidmoten.bigsorter.Sorter;

public class App {

    public static void main(String[] args) {
        Serializer<CSVRecord> serializer = Serializer.csv(
                CSVFormat.DEFAULT.withRecordSeparator("\n") //
                        .withFirstRecordAsHeader(), //
                StandardCharsets.UTF_8);

        Comparator<CSVRecord> comparator = (x, y) -> {
            int a = Integer.parseInt(x.get(1));
            int b = Integer.parseInt(y.get(1));
            return Integer.compare(a, b);
        };

        Sorter //
                .serializer(serializer) //
                .comparator(comparator) //
                .input(new File("src/test/resources/data.txt")) //
                .output(new File("target/data.sorted.txt")) //
                .loggerStdOut() //
                .sort();
    }

}
