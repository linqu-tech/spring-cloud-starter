package tech.linqu.spring.cloud.starter.tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import lombok.extern.slf4j.Slf4j;

/**
 * Utility for jdbc connection.
 */
@Slf4j
public class JdbcUtils {

    private JdbcUtils() {
    }

    /**
     * Open a connection.
     *
     * @param url      jdbc url
     * @param username database usernmae
     * @param password database password
     * @return {@link Connection}
     */
    public static Connection openConnection(String url, String username, String password) {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            log.error("Could not open JDBC Connection", ex);
            throw new IllegalArgumentException("Could not open JDBC Connection");
        }
    }

    /**
     * Close a connection.
     *
     * @param con {@link Connection}
     */
    public static void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                log.debug("Could not close JDBC Connection", ex);
            } catch (Throwable ex) {
                log.debug("Unexpected exception on closing JDBC Connection", ex);
            }
        }
    }

    /**
     * Close a statement.
     *
     * @param stmt {@link Statement}
     */
    public static void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                log.trace("Could not close JDBC Statement", ex);
            } catch (Throwable ex) {
                log.trace("Unexpected exception on closing JDBC Statement", ex);
            }
        }
    }

    /**
     * Create a database.
     *
     * @param connection {@link Connection}
     * @param database   database name
     */
    public static void createDatabase(Connection connection, String database) {
        String createDdl = String.format("CREATE DATABASE `%s`", database);
        executeUpdate(connection, createDdl);
    }

    /**
     * Drop a database.
     *
     * @param connection {@link Connection}
     * @param database   database name
     */
    public static void dropDatabase(Connection connection, String database) {
        String dropDdl = String.format("DROP DATABASE `%s`", database);
        executeUpdate(connection, dropDdl);
    }

    private static void executeUpdate(Connection connection, String sql) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException ex) {
            log.trace("Could not execute JDBC Statement", ex);
        } finally {
            closeConnection(connection);
            closeStatement(statement);
        }
    }
}
