package tech.linqu.spring.cloud.starter.utilities.jooq;

import java.sql.Connection;
import java.sql.SQLException;
import org.jooq.ConnectionProvider;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DefaultConfiguration;

/**
 * Configuration for JOOQ.
 */
public class JooqConfig {

    private JooqConfig() {
    }

    /**
     * Create a {@link DefaultConfiguration}.
     *
     * @param connectionProvider {@link ConnectionProvider}
     * @return {@link DefaultConfiguration}
     */
    public static DefaultConfiguration configuration(ConnectionProvider connectionProvider) {
        System.getProperties().setProperty("org.jooq.no-logo", "true");
        DefaultConfiguration configuration = new DefaultConfiguration();
        configuration.setSettings(new Settings()
            .withRenderCatalog(false)
            .withRenderSchema(false)
        );
        if (connectionProvider == null) {
            return configuration;
        }
        configuration.set(connectionProvider);
        try (Connection connection = connectionProvider.acquire()) {
            if (connection == null) {
                throw new RuntimeException("Cannot connect to database");
            }
            String databaseName = connection.getMetaData().getDatabaseProductName();
            if ("MySQL".equals(databaseName)) {
                configuration.setSQLDialect(SQLDialect.MYSQL);
            } else if ("H2".equals(databaseName)) {
                configuration.setSQLDialect(SQLDialect.H2);
            }
            return configuration;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
