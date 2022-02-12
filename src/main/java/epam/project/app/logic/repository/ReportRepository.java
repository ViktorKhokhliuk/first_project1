package epam.project.app.logic.repository;

import epam.project.app.logic.entity.report.Report;
import epam.project.app.logic.entity.report.ReportStatus;
import epam.project.app.logic.entity.dto.ReportCreateDto;
import epam.project.app.logic.entity.dto.ReportUpdateDto;
import epam.project.app.logic.entity.user.Filter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

@RequiredArgsConstructor
public class ReportRepository {
    private final DataSource dataSource;
    private static final String INSERT_REPORT = "INSERT INTO report (name, path, client_id, status, date, type) VALUES (?,?,?,?,?,?);";
    private static final String UPDATE_STATUS_OF_REPORT = "update report set status = ?, date = ? where id = ?;";
    private static final String SELECT_REPORT_BY_CLIENT_ID = "select * from report where client_id = ?;";
    private static final String SELECT_REPORT_BY_DATE = "select * from report where client_id = ? and date = ?;";
    private static final String SELECT_REPORT_BY_STATUS = "select * from report where client_id = ? and status = ?;";
    private static final String SELECT_REPORT_BY_TYPE = "select * from report where client_id = ? and type = ?;";
    private static final String SELECT_REPORT_BY_ID = "select * from report where id = ?;";
    private static final String DELETE_REPORT_BY_ID = "delete from report where id = ?;";
    private static final String DATE = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    @SneakyThrows
    public List<Report> getReportsByParameter(Map<String, String> parameters) {
        Iterator<Map.Entry<String, String>> iterator = parameters.entrySet().iterator();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("select * from report where ");
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            stringBuffer.append(entry.getKey()).append(" = ").append("'").append(entry.getValue()).append("'");
            if (iterator.hasNext()) {
                stringBuffer.append(" and ");
            } else {
                stringBuffer.append(";");
            }
        }
        String sql = stringBuffer.toString();
        List<Report> reports = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String path = resultSet.getString("path");
                String status = resultSet.getString("status");
                String type = resultSet.getString("type");
                String date = resultSet.getString("date");
                long clientId = resultSet.getLong("client_id");
                reports.add(new Report(id, name, path, status, date, type, clientId));
            }
            return reports;
        }
    }

    @SneakyThrows
    public Optional<Report> insertReport(ReportCreateDto dto) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_REPORT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, dto.getName());
            preparedStatement.setString(2, dto.getPath());
            preparedStatement.setLong(3, dto.getClientId());
            preparedStatement.setString(4, ReportStatus.SUBMITTED.getTitle());
            preparedStatement.setString(5, DATE);
            preparedStatement.setString(6, dto.getType());
            preparedStatement.execute();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long id = generatedKeys.getLong(1);
                    return Optional.of(new Report(id, dto.getName(), dto.getPath(), ReportStatus.SUBMITTED.getTitle(), DATE, dto.getType(), dto.getClientId()));
                }

            }
            return Optional.empty();
        }
    }

    public Optional<Report> updateStatusOfReport(ReportUpdateDto dto) {
        Report updatedReport = new Report();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_STATUS_OF_REPORT)) {
            ps.setString(1, dto.getStatus());
            ps.setString(2, DATE);
            ps.setLong(3, dto.getId());
            ps.executeUpdate();
            updatedReport.setId(dto.getId());
            updatedReport.setStatus(dto.getStatus());
            return Optional.of(updatedReport);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @SneakyThrows
    public Optional<Report> getReportById(Long id) {
        Report report = new Report();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_REPORT_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                report.setId(id);
                report.setName(resultSet.getString("name"));
                report.setPath(resultSet.getString("path"));
                report.setStatus(resultSet.getString("status"));
                report.setDate(resultSet.getString("date"));
                report.setType(resultSet.getString("type"));
                report.setClientId(resultSet.getLong("client_id"));
                return Optional.of(report);
            }
            return Optional.empty();
        }
    }

    @SneakyThrows
    public List<Report> getAllReportsByClientId(Long clientId) {
        List<Report> reports = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_REPORT_BY_CLIENT_ID)) {
            preparedStatement.setLong(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String path = resultSet.getString("path");
                String status = resultSet.getString("status");
                String date = resultSet.getString("date");
                String type = resultSet.getString("type");
                reports.add(new Report(id, name, path, status, date, type, clientId));
            }
            return reports;
        }
    }


    @SneakyThrows
    public Optional<Report> deleteReportById(Long id) {
        Optional<Report> report = getReportById(id);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_REPORT_BY_ID)) {
            preparedStatement.setLong(1, id);
            if (preparedStatement.executeUpdate() > 0) {
                return report;
            }
            return Optional.empty();
        }
    }

    private void close(AutoCloseable autoCloseable) {
        if (autoCloseable != null) {
            try {
                autoCloseable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
