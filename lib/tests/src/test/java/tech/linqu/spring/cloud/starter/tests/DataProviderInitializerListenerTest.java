package tech.linqu.spring.cloud.starter.tests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;

class DataProviderInitializerListenerTest {

    @Test
    void shouldPublishEnvironmentPreparedEventSuccess() {
        ApplicationEnvironmentPreparedEvent event = mock(ApplicationEnvironmentPreparedEvent.class);
        ConfigurableEnvironment environment = mock(ConfigurableEnvironment.class);
        when(event.getEnvironment()).thenReturn(environment);
        MutablePropertySources sources = mock(MutablePropertySources.class);
        when(environment.getPropertySources()).thenReturn(sources);

        DataProviderInitializerListener listener = new DataProviderInitializerListener();
        listener.onApplicationEvent(event);
    }

    @Test
    void shouldPublishPreparedEventSuccess() {
        ApplicationPreparedEvent event = mock(ApplicationPreparedEvent.class);
        ConfigurableApplicationContext context = mock(ConfigurableApplicationContext.class);
        when(event.getApplicationContext()).thenReturn(context);
        ConfigurableEnvironment environment = mock(ConfigurableEnvironment.class);
        when(context.getEnvironment()).thenReturn(environment);
        DataProviderInitializerListener listener = new DataProviderInitializerListener();
        assertDoesNotThrow(() -> listener.onApplicationEvent(event));

        when(environment.getProperty("random.database")).thenReturn("test-database");
        assertDoesNotThrow(() -> listener.onApplicationEvent(event));

        when(environment.getProperty("random.database")).thenReturn(null);
        when(environment.getProperty("spring.datasource.url"))
            .thenReturn("h2:mem:///${test-database}");
        assertDoesNotThrow(() -> listener.onApplicationEvent(event));
    }

    @Test
    void givenRandomDatabase_whenPublishPreparedEvent_shouldPublishSuccess() {
        ApplicationPreparedEvent event = mock(ApplicationPreparedEvent.class);
        ConfigurableApplicationContext context = mock(ConfigurableApplicationContext.class);
        when(event.getApplicationContext()).thenReturn(context);
        ConfigurableEnvironment environment = mock(ConfigurableEnvironment.class);
        when(context.getEnvironment()).thenReturn(environment);
        when(environment.getProperty("random.database")).thenReturn("test-database");
        DataProviderInitializerListener listener = new DataProviderInitializerListener();
        try (MockedStatic<JdbcUtils> jdbcUtils = mockStatic(JdbcUtils.class)) {
            assertNotNull(jdbcUtils);
            when(environment.getProperty("spring.datasource.url"))
                .thenReturn("h2:mem:///${test-database}");
            listener.onApplicationEvent(event);

            when(environment.getProperty("spring.datasource.url"))
                .thenReturn("jdbc:h2:mem:///${test-database}");
            listener.onApplicationEvent(event);
        }
    }

    @Test
    void shouldPublishContextClosedEventSuccess() {
        ContextClosedEvent event = mock(ContextClosedEvent.class);
        ConfigurableApplicationContext context = mock(ConfigurableApplicationContext.class);
        when(event.getApplicationContext()).thenReturn(context);
        ConfigurableEnvironment environment = mock(ConfigurableEnvironment.class);
        when(context.getEnvironment()).thenReturn(environment);
        DataProviderInitializerListener listener = new DataProviderInitializerListener();
        listener.onApplicationEvent(event);
    }

    @Test
    void shouldPublishFailedEventSuccess() {
        ApplicationFailedEvent event = mock(ApplicationFailedEvent.class);
        ConfigurableApplicationContext context = mock(ConfigurableApplicationContext.class);
        when(event.getApplicationContext()).thenReturn(context);
        ConfigurableEnvironment environment = mock(ConfigurableEnvironment.class);
        when(context.getEnvironment()).thenReturn(environment);
        DataProviderInitializerListener listener = new DataProviderInitializerListener();
        listener.onApplicationEvent(event);
    }

    @Test
    void givenRandomDatabase_whenPublishFailedEvent_shouldPublishSuccess() {
        ApplicationFailedEvent event = mock(ApplicationFailedEvent.class);
        ConfigurableApplicationContext context = mock(ConfigurableApplicationContext.class);
        when(event.getApplicationContext()).thenReturn(context);
        ConfigurableEnvironment environment = mock(ConfigurableEnvironment.class);
        when(context.getEnvironment()).thenReturn(environment);
        when(environment.getProperty("random.database")).thenReturn("test-database");
        DataProviderInitializerListener listener = new DataProviderInitializerListener();
        try (MockedStatic<JdbcUtils> jdbcUtils = mockStatic(JdbcUtils.class)) {
            assertNotNull(jdbcUtils);
            when(environment.getProperty("spring.datasource.url"))
                .thenReturn("h2:mem:///${test-database}");
            listener.onApplicationEvent(event);
        }
    }
}
