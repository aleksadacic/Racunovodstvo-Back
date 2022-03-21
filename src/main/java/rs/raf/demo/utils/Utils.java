package rs.raf.demo.utils;

import java.text.DecimalFormat;
import java.util.List;

public class Utils {

    /**
     * @param args
     * @return Suma Double-ova, s tim sto null vrednosti u listi preskace.
     */
    public static Double sum(List<Double> args) {
        return args.stream().reduce(0.0, Double::sum);
    }
}
