package tech.linqu.spring.cloud.starter.tests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

class JdbcUtilsTest {

    @Test
    void shouldOpenConnectionSuccess() {
        try (MockedStatic<DriverManager> manager = mockStatic(DriverManager.class)) {
            Connection connection = mock(Connection.class);
            manager.when(() -> DriverManager.getConnection(any(), any(), any()))
                .thenReturn(connection);
            assertEquals(connection, JdbcUtils.openConnection(null, null, null));
        }
    }

    @Test
    void shouldOpenConnectionThrowException() {
        try (MockedStatic<DriverManager> manager = mockStatic(DriverManager.class)) {
            SQLException ex = new SQLException();
            manager.when(() -> DriverManager.getConnection(any(), any(), any())).thenThrow(ex);
            assertThrows(IllegalArgumentException.class,
                () -> JdbcUtils.openConnection(null, null, null), "Could not open JDBC Connection");
        }
    }

    @Test
    void shouldCloseConnectionSuccess() throws SQLException {
        assertDoesNotThrow(() -> JdbcUtils.closeConnection(null));

        Connection connection = mock(Connection.class);
        assertDoesNotThrow(() -> JdbcUtils.closeConnection(connection));

        doThrow(new SQLException()).when(connection).close();
        assertDoesNotThrow(() -> JdbcUtils.closeConnection(connection));

        doThrow(new RuntimeException()).when(connection).close();
        assertDoesNotThrow(() -> JdbcUtils.closeConnection(connection));
    }

    @Test
    void shouldCloseStatementSuccess() throws SQLException {
        assertDoesNotThrow(() -> JdbcUtils.closeStatement(null));

        Statement statement = mock(Statement.class);
        assertDoesNotThrow(() -> JdbcUtils.closeStatement(statement));

        doThrow(new SQLException()).when(statement).close();
        assertDoesNotThrow(() -> JdbcUtils.closeStatement(statement));

        doThrow(new RuntimeException()).when(statement).close();
        assertDoesNotThrow(() -> JdbcUtils.closeStatement(statement));
    }

    @Test
    void shouldCreateDatabaseSuccess() throws SQLException {
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(any())).thenReturn(mock(PreparedStatement.class));
        assertDoesNotThrow(() -> JdbcUtils.createDatabase(connection, "test"));
    }

    @Test
    void shouldDropDatabaseSuccess() throws SQLException {
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        when(connection.prepareStatement(any())).thenReturn(statement);
        when(statement.executeUpdate()).thenThrow(new SQLException());
        assertDoesNotThrow(() -> JdbcUtils.dropDatabase(connection, "test"));
    }
}
