package tech.linqu.spring.cloud.starter.tests;

import java.sql.Connection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.core.env.Environment;

/**
 * Listener for data initialization from data provider.
 */
@Slf4j
public class DataProviderInitializerListener implements ApplicationListener<ApplicationEvent> {

    /**
     * On received event.
     *
     * @param event {@link ApplicationEvent}
     */
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationEnvironmentPreparedEvent) {
            RandomDatabasePropertySource
                .addToEnvironment(((ApplicationEnvironmentPreparedEvent) event).getEnvironment());
        }
        if (event instanceof ApplicationPreparedEvent) {
            create(((ApplicationPreparedEvent) event).getApplicationContext());
        }
        if (event instanceof ContextClosedEvent) {
            drop(((ContextClosedEvent) event).getApplicationContext());
        }
        if (event instanceof ApplicationFailedEvent) {
            drop(((ApplicationFailedEvent) event).getApplicationContext());
        }
    }

    private void create(ApplicationContext context) {
        if (checkRandomDatabase(context)) {
            String database = context.getEnvironment().getProperty("random.database");
            log.info("Create random database: [{}]", database);
            JdbcUtils.createDatabase(getConnection(context), database);
        }
    }

    private void drop(ApplicationContext context) {
        if (checkRandomDatabase(context)) {
            String database = context.getEnvironment().getProperty("random.database");
            log.info("Drop random database: [{}]", database);
            JdbcUtils.dropDatabase(getConnection(context), database);
        }
    }

    private Connection getConnection(ApplicationContext context) {
        Environment environment = context.getEnvironment();
        String database = environment.getProperty("random.database");
        String username = environment.getProperty("spring.datasource.username");
        String password = environment.getProperty("spring.datasource.password");
        String url = environment.getProperty("spring.datasource.url");
        url = url.replace(database, "");
        url = url.startsWith("jdbc") ? url : "jdbc:" + url;
        return JdbcUtils.openConnection(url, username, password);
    }

    private boolean checkRandomDatabase(ApplicationContext context) {
        Environment environment = context.getEnvironment();
        String database = environment.getProperty("random.database");
        String url = environment.getProperty("spring.datasource.url");
        if (database == null || url == null) {
            return false;
        }
        return url.contains(database);
    }
}
