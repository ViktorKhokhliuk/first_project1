package epam.project.app.infra.config.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import epam.project.app.infra.config.app.PropertyLoader;
import lombok.extern.log4j.Log4j2;

import javax.sql.DataSource;

@Log4j2
public class ConfigDataSource {

    public DataSource createDataSource(PropertyLoader properties) {
        log.info("Start config DataSource");
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(properties.getConfigs().get("jdbc_url"));
        config.setUsername(properties.getConfigs().get("user_name"));
        log.info("Set userName - " + properties.getConfigs().get("user_name"));
        config.setPassword(properties.getConfigs().get("password"));
        log.info("Set userPassword - " + properties.getConfigs().get("password"));

        return new HikariDataSource(config);
    }

}
