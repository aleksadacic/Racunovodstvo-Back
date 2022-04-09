package rs.raf.demo.reports;

import com.itextpdf.text.DocumentException;

public interface Reports {
    byte[] getReport() throws DocumentException;
}
