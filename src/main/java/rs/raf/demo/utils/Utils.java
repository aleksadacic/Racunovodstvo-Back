package rs.raf.demo.utils;

import java.util.List;

public class Utils {

    private Utils() {}

    /**
     * @param args Argumenti za sumiranje.
     * @return Suma Double-ova, s tim sto null vrednosti u listi preskace.
     */
    public static Double sum(List<Double> args) {
        return args.stream().reduce(0.0, Double::sum);
    }

    public static class Strings {
        private Strings() {}
        public static boolean isEmpty(String str) {
            return str == null || str.length() == 0;
        }
    }
}
