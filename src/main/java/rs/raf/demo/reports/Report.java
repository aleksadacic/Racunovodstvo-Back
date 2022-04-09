package rs.raf.demo.reports;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Report implements Reports {
    protected String title;
    protected String author;
    protected String footer;

    protected Report(String title, String author, String footer) {
        setTitle(title);
        setAuthor(author);
        setFooter(footer);
    }
}
