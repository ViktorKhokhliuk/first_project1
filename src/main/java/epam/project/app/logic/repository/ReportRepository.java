package epam.project.app.logic.repository;

import epam.project.app.logic.entity.report.Report;
import epam.project.app.logic.entity.report.ReportInfo;
import epam.project.app.logic.entity.report.ReportStatus;
import epam.project.app.logic.entity.dto.ReportCreateDto;
import epam.project.app.logic.entity.dto.ReportUpdateDto;
import epam.project.app.logic.entity.user.Client;
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
    private static final String INSERT_REPORT = "INSERT INTO report (title, path, clientId, status, info, date, type) VALUES (?,?,?,?,?,?,?);";
    private static final String UPDATE_STATUS_OF_REPORT = "update report set status = ?, info = ? where id = ?;";
    private static final String SELECT_REPORT_BY_CLIENT_ID = "select * from report where clientId = ? limit ?, 5;";
    private static final String SELECT_REPORT_BY_ID = "select * from report where id = ?;";
    private static final String DELETE_REPORT_BY_ID = "delete from report where id = ?;";
    private static final String SELECT_ALL_REPORTS = "select * from report join client on report.clientId=client.id join user on client.id=user.id limit ?, 5;";
    private static final String SELECT_COUNT_ALL_REPORTS = "select count(*) from report join client on report.clientId=client.id join user on client.id=user.id;";
    private static final String SELECT_COUNT_REPORTS_BY_CLIENT = "select count(*) from report where clientId = ?;";
    private static final String DATE = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    @SneakyThrows
    public List<Report> getClientReportsByFilterParameters(Map<String, String> parameters, int index) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("select * ").append(getSqlQueryClientReportsByParameter(parameters)).append(" limit ?, 5;");
        String sql = stringBuffer.toString();
        List<Report> reports = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, index);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    String title = resultSet.getString("title");
                    String path = resultSet.getString("path");
                    String status = resultSet.getString("status");
                    String info = resultSet.getString("info");
                    String type = resultSet.getString("type");
                    String date = resultSet.getString("date");
                    long clientId = resultSet.getLong("clientId");
                    reports.add(new Report(id, title, path, status, info, date, type, clientId));
                }
            }
        }
        return reports;
    }

    @SneakyThrows
    public Optional<Report> insertReport(ReportCreateDto dto) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_REPORT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, dto.getTitle());
            preparedStatement.setString(2, dto.getPath());
            preparedStatement.setLong(3, dto.getClientId());
            preparedStatement.setString(4, ReportStatus.SUBMITTED.getTitle());
            preparedStatement.setString(5, ReportInfo.PROCESS.getTitle());
            preparedStatement.setString(6, DATE);
            preparedStatement.setString(7, dto.getType());
            preparedStatement.execute();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    long id = resultSet.getLong(1);
                    return Optional.of(new Report(id, dto.getTitle(), dto.getPath(), ReportStatus.SUBMITTED.getTitle(),ReportInfo.PROCESS.getTitle(), DATE, dto.getType(), dto.getClientId()));
                }
            }
        }
        return Optional.empty();
    }

    @SneakyThrows
    public Optional<Report> updateStatusOfReport(ReportUpdateDto dto) {
        Report updatedReport = getReportById(dto.getId()).get();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STATUS_OF_REPORT)) {
            preparedStatement.setString(1, dto.getStatus());
            preparedStatement.setString(2, dto.getInfo());
            preparedStatement.setLong(3, dto.getId());
            if (preparedStatement.executeUpdate() > 0) {
                updatedReport.setStatus(dto.getStatus());
                updatedReport.setInfo(dto.getInfo());
                return Optional.of(updatedReport);
            }
        }
        return Optional.empty();
    }

    @SneakyThrows
    public Optional<Report> getReportById(Long id) {
        Report report = new Report();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_REPORT_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    report.setId(id);
                    report.setTitle(resultSet.getString("title"));
                    report.setPath(resultSet.getString("path"));
                    report.setStatus(resultSet.getString("status"));
                    report.setInfo(resultSet.getString("info"));
                    report.setDate(resultSet.getString("date"));
                    report.setType(resultSet.getString("type"));
                    report.setClientId(resultSet.getLong("clientId"));
                    return Optional.of(report);
                }
            }
        }
        return Optional.empty();
    }

    @SneakyThrows
    public List<Report> getAllReportsByClientId(Long clientId,int index) {
        List<Report> reports = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_REPORT_BY_CLIENT_ID)) {
            preparedStatement.setLong(1, clientId);
            preparedStatement.setInt(2, index);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    String title = resultSet.getString("title");
                    String path = resultSet.getString("path");
                    String status = resultSet.getString("status");
                    String info = resultSet.getString("info");
                    String date = resultSet.getString("date");
                    String type = resultSet.getString("type");
                    reports.add(new Report(id, title, path, status, info, date, type, clientId));
                }
            }
        }
        return reports;
    }

    @SneakyThrows
    public  Map<List<Report>,Map<Long,Client>> getAllReports(int index) {
        Map<List<Report>,Map<Long,Client>> map = new LinkedHashMap<>();
        Map<Long,Client> clientMap = new LinkedHashMap<>();
        List<Report> reports = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_REPORTS)) {
            preparedStatement.setInt(1, index);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    long reportId = resultSet.getLong("id");
                    long clientId = resultSet.getLong("clientId");
                    String title = resultSet.getString("title");
                    String path = resultSet.getString("path");
                    String status = resultSet.getString("status");
                    String info = resultSet.getString("info");
                    String date = resultSet.getString("date");
                    String type = resultSet.getString("type");
                    String login = resultSet.getString("login");
                    String name = resultSet.getString("name");
                    String surname = resultSet.getString("surname");
                    String itn = resultSet.getString("itn");
                    Client client = new Client(name, surname, itn);
                    client.setId(clientId);
                    client.setLogin(login);
                    clientMap.put(clientId, client);
                    reports.add(new Report(reportId, title, path, status, info, date, type, clientId));
                }
            }
        }
        map.put(reports,clientMap);
        return map;
    }

    @SneakyThrows
    public  Map<List<Report>,Map<Long,Client>> getAllReportsByFilterParameters(Map<String, String> parameters,int index) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("select * ").append(getSqlQueryAllReportsByParameter(parameters)).append(" limit ?, 5;");
        String sql = stringBuffer.toString();
        Map<List<Report>,Map<Long,Client>> map = new LinkedHashMap<>();
        Map<Long,Client> clientMap = new HashMap<>();
        List<Report> reports = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, index);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    long reportId = resultSet.getLong("id");
                    long clientId = resultSet.getLong("clientId");
                    String title = resultSet.getString("title");
                    String path = resultSet.getString("path");
                    String status = resultSet.getString("status");
                    String info = resultSet.getString("info");
                    String date = resultSet.getString("date");
                    String type = resultSet.getString("type");
                    String login = resultSet.getString("login");
                    String clientName = resultSet.getString("name");
                    String surname = resultSet.getString("surname");
                    String itn = resultSet.getString("itn");
                    Client client = new Client(clientName, surname, itn);
                    client.setId(clientId);
                    client.setLogin(login);
                    clientMap.put(clientId, client);
                    reports.add(new Report(reportId, title, path, status, info, date, type, clientId));
                }
            }
        }
        map.put(reports,clientMap);
        return map;
    }

    @SneakyThrows
    public Optional<Report> updateStatusOfReportAfterEdit(Long id) {
        Report updatedReport = getReportById(id).get();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STATUS_OF_REPORT)) {
            preparedStatement.setString(1, ReportStatus.EDITED.getTitle());
            preparedStatement.setString(2, ReportInfo.EDIT.getTitle());
            preparedStatement.setLong(3, id);
            if (preparedStatement.executeUpdate() > 0) {
                updatedReport.setStatus(ReportStatus.EDITED.getTitle());
                updatedReport.setInfo(ReportInfo.EDIT.getTitle());
                return Optional.of(updatedReport);
            }
        }
        return Optional.empty();
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
        }
        return Optional.empty();
    }

    @SneakyThrows
    public double getCountOfPageForAllReports() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_COUNT_ALL_REPORTS)) {
            if (resultSet.next())
                return resultSet.getDouble(1);
        }
        return 0;
    }

    @SneakyThrows
    public double getCountOfPageForFilterReports(Map<String, String> parameters) {
        String sql = "select count(*) " + getSqlQueryAllReportsByParameter(parameters) + ";";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next())
                return resultSet.getDouble(1);
        }
        return 0;
    }

    @SneakyThrows
    public double getCountOfPageForAllClientReports(Long clientId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COUNT_REPORTS_BY_CLIENT)) {
            preparedStatement.setLong(1, clientId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next())
                    return resultSet.getDouble(1);
            }
        }
        return 0;
    }

    @SneakyThrows
    public double getCountOfPageForFilterClientReports(Map<String, String> parameters) {
        String sql = "select count(*) " + getSqlQueryClientReportsByParameter(parameters) + ";";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
                if (resultSet.next())
                    return resultSet.getDouble(1);
            }
        return 0;
    }


    private String getSqlQueryClientReportsByParameter(Map<String, String> parameters) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("from report");
        if (!parameters.isEmpty()) {
            Iterator<Map.Entry<String, String>> iterator = parameters.entrySet().iterator();
            stringBuffer.append(" where ");
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                stringBuffer.append(entry.getKey()).append(" = ").append("'").append(entry.getValue()).append("'");
                if (iterator.hasNext()) {
                    stringBuffer.append(" and ");
                }
            }
        }
        return stringBuffer.toString();
    }

    private String getSqlQueryAllReportsByParameter(Map<String, String> parameters) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("from report join client on report.clientId=client.id join user on client.id=user.id");
        if (!parameters.isEmpty()) {
            stringBuffer.append(" where ");
            Iterator<Map.Entry<String, String>> iterator = parameters.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                if (entry.getKey().equals("status")||entry.getKey().equals("date")||entry.getKey().equals("type")){
                    stringBuffer.append("report.");
                } else {
                    stringBuffer.append("client.");
                }
                stringBuffer.append(entry.getKey()).append(" = ").append("'").append(entry.getValue()).append("'");
                if (iterator.hasNext()) {
                    stringBuffer.append(" and ");
                }
            }
        }
        return stringBuffer.toString();
    }
}
