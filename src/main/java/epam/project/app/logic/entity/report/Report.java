package epam.project.app.logic.entity.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report implements Comparable<Report> {
    private Long id;
    private String title;
    private String path;
    private String status;
    private String info;
    private String date;
    private String type;
    private Long clientId;


    @Override
    public int compareTo(Report report) {
        return this.getDate().compareTo(report.getDate());
    }
}
