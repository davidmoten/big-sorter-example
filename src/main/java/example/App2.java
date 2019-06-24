package example;

import java.io.File;
import java.util.Comparator;

import com.github.davidmoten.bigsorter.LineDelimiter;
import com.github.davidmoten.bigsorter.Serializer;
import com.github.davidmoten.bigsorter.Sorter;

public class App2 {

    public static void main(String[] args) {
        Sorter //
                .serializer(Serializer.linesUtf8(LineDelimiter.LINE_FEED)) //
                .comparator(Comparator.naturalOrder()) //
                .input(new File("src/test/resources/data2.txt")) //
                .output(new File("target/data2.sorted.txt")) //
                .loggerStdOut() //
                .sort();
    }

}
