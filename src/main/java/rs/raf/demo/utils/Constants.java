package rs.raf.demo.utils;

import java.util.List;

public class Constants {
    public enum BILANS {
        BRUTO_BILANS,
        BILANS_STANJA,
        BILANS_USPEHA;

        public static final List<String> BILANS_STANJA_VALUES = List.of("0", "1", "2", "3", "4");
        public static final List<String> BILANS_USPEHA_VALUES = List.of("5","6");
    }
}
