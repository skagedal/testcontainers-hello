package se.kry.testcontainershello;

import java.sql.DriverManager;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    @Test
    void testcontainers_mysql() {
        // Start a container
        try (final var container = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.31"))) {
            container.start();

            String query = "SELECT version()";

            final var connection = DriverManager.getConnection(container.getJdbcUrl(), container.getUsername(), container.getPassword());
            final var statement = connection.createStatement();
            final var resultSet = statement.executeQuery(query);

            assertTrue(resultSet.next());
            final var version = resultSet.getString(1);
            System.out.println("Version is: " + resultSet.getString(1));
            assertEquals("8.0.31", version);
        } catch (Exception exception) {
            System.err.println("We failed:");
            System.err.println(exception.getMessage());
            fail(exception);
        }
    }
}
