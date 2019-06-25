package example;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;

import com.github.davidmoten.bigsorter.LineDelimiter;
import com.github.davidmoten.bigsorter.Reader;
import com.github.davidmoten.bigsorter.Serializer;
import com.github.davidmoten.bigsorter.Sorter;

public class App2 {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Serializer<String> serializer = Serializer.linesUtf8(LineDelimiter.LINE_FEED);
        File output = new File("target/data2.sorted.txt");
        Sorter //
                .serializer(serializer) //
                .comparator(Comparator.naturalOrder()) //
                .input(new File("src/test/resources/data2.txt")) //
                .output(output) //
                .loggerStdOut() //
                .sort();

        // now count duplicates in output
        try (InputStream in = new BufferedInputStream(new FileInputStream(output));
                Reader<String> r = serializer.createReader(in)) {
            long dups = 0;
            String last = null;
            String string;
            while ((string = r.read()) != null) {
                if (last != null && last.equals(string)) {
                    dups++;
                }
                last = string;
            }
            System.out.println("duplicateLines=" + dups);
        }
    }

}
