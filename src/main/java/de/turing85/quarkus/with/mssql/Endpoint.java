package de.turing85.quarkus.with.mssql;

import io.agroal.api.AgroalDataSource;
import lombok.RequiredArgsConstructor;
import org.jboss.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Path("")
@RequiredArgsConstructor
public class Endpoint {
  private final Logger logger;
  private final AgroalDataSource dataSource;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public int testDatabase() throws SQLException {
    try(
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT 1;")) {
      resultSet.next();
      return resultSet.getInt(1);
    } catch (SQLException e) {
      logger.error("Error", e);
      throw e;
    }
  }
}
