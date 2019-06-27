package example;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Comparator;

import com.github.davidmoten.bigsorter.LineDelimiter;
import com.github.davidmoten.bigsorter.Reader;
import com.github.davidmoten.bigsorter.Serializer;
import com.github.davidmoten.bigsorter.Sorter;
import com.github.davidmoten.bigsorter.Writer;

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

        File outputWithCounts = new File("target/data2.sorted.with.counts.txt");

        // now count occurrences in output
        try (InputStream in = new BufferedInputStream(new FileInputStream(output));
                OutputStream out = new BufferedOutputStream(new FileOutputStream(outputWithCounts));
                Reader<String> r = serializer.createReader(in);
                Writer<String> w = serializer.createWriter(out)) {
            long dups = 0;
            String last = null;
            String string;
            while ((string = r.read()) != null) {
                if (last != null) {
                    if (!last.equals(string)) {
                        w.write(dups + " " + last);
                        dups = 0;
                    }
                }
                dups++;
                last = string;
            }
            w.write(dups + " " + last);
        }

    }

}
