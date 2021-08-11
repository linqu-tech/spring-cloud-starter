package tech.linqu.spring.cloud.starter.utilities.jooq;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import org.jooq.Configuration;
import org.jooq.ConnectionProvider;
import org.jooq.SQLDialect;
import org.jooq.impl.NoConnectionProvider;
import org.junit.jupiter.api.Test;

class JooqConfigTest {

    @Test
    void shouldConfigSuccess() {
        Configuration configuration = JooqConfig.configuration(null);
        assertTrue(configuration.connectionProvider() instanceof NoConnectionProvider);
    }

    @Test
    void shouldConfigWithConnectionProviderSuccess() throws SQLException {
        ConnectionProvider provider = mock(ConnectionProvider.class);
        Connection connection = mock(Connection.class);
        when(provider.acquire()).thenReturn(connection);
        DatabaseMetaData metaData = mock(DatabaseMetaData.class);
        when(connection.getMetaData()).thenReturn(metaData);

        when(metaData.getDatabaseProductName()).thenReturn("MySQL");
        Configuration configuration = JooqConfig.configuration(provider);
        assertEquals(SQLDialect.MYSQL, configuration.dialect());

        when(metaData.getDatabaseProductName()).thenReturn("H2");
        configuration = JooqConfig.configuration(provider);
        assertEquals(SQLDialect.H2, configuration.dialect());

        when(metaData.getDatabaseProductName()).thenReturn("OTHER");
        configuration = JooqConfig.configuration(provider);
        assertEquals(SQLDialect.DEFAULT, configuration.dialect());
    }

    @Test
    void shouldThrowExceptionWhenNoConnection() {
        ConnectionProvider provider = mock(ConnectionProvider.class);
        when(provider.acquire()).thenReturn(null);
        assertThrows(RuntimeException.class, () -> JooqConfig.configuration(provider));
    }

    @Test
    void shouldThrowExceptionWhenAcquireConnectionError() throws SQLException {
        ConnectionProvider provider = mock(ConnectionProvider.class);
        Connection connection = mock(Connection.class);
        when(provider.acquire()).thenReturn(connection);
        when(connection.getMetaData()).thenThrow(new SQLException());
        assertThrows(RuntimeException.class, () -> JooqConfig.configuration(provider));
    }
}
