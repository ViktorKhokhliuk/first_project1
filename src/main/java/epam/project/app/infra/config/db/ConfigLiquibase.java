package epam.project.app.infra.config.db;

import epam.project.app.infra.config.app.PropertyLoader;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.sql.Connection;

@RequiredArgsConstructor
public class ConfigLiquibase {
    private final DataSource dataSource;

    @SneakyThrows
    public void updateDatabase(PropertyLoader propertyLoader) {
        String change_log_file = propertyLoader.getConfigs().get("change_log_file");
        try (Connection connection = dataSource.getConnection()) {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new liquibase.Liquibase(change_log_file, new ClassLoaderResourceAccessor(), database);
            liquibase.update(new Contexts(), new LabelExpression());
        }
    }
}
