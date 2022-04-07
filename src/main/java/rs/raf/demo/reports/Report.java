package rs.raf.demo.reports;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Report {
    protected String title;
    protected String author;
    protected String footer;

    public Report(String title, String author, String footer) {
        setTitle(title);
        setAuthor(author);
        setFooter(footer);
    }
}
