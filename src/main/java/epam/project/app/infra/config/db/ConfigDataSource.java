package epam.project.app.infra.config.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import epam.project.app.infra.config.app.PropertyLoader;

import javax.sql.DataSource;

public class ConfigDataSource {

    public DataSource createDataSource(PropertyLoader properties) {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(properties.getConfigs().get("jdbc_url"));
        config.setUsername(properties.getConfigs().get("user_name"));
        config.setPassword(properties.getConfigs().get("password"));

        return new HikariDataSource(config);
    }

}
