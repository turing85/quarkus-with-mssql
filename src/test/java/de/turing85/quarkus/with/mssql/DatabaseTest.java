package de.turing85.quarkus.with.mssql;

import com.google.common.truth.Truth;
import io.agroal.api.AgroalDataSource;
import io.quarkus.test.junit.QuarkusTest;
import lombok.RequiredArgsConstructor;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@QuarkusTest
@RequiredArgsConstructor
class DatabaseTest {
  private final Logger logger;
  private final AgroalDataSource dataSource;

  @Test
  void testDatabaseConnection() {
    try(
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT 1;")) {
      int counter = 0;
      while (resultSet.next()) {
        ++counter;
        Truth.assertThat(resultSet.getInt(1)).isEqualTo(1);
      }
      Truth.assertThat(counter).isEqualTo(1);
    } catch (SQLException e) {
      logger.error("Error", e);
      throw new RuntimeException(e);
    }
  }
}
