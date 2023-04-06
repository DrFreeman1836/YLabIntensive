package io.ylab.intensive.lesson05.eventsourcing.db.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DbClient {

  private final Connection connection;

  private static final String ddl = ""
      + "create table if not exists person (\n"
      + "person_id bigint primary key,\n"
      + "first_name varchar,\n"
      + "last_name varchar,\n"
      + "middle_name varchar\n"
      + ")";

  @Autowired
  public DbClient(DataSource dataSource) throws SQLException {
    this.connection = dataSource.getConnection();
  }

  @PostConstruct
  private void init() {
    createTable();
  }

  @PreDestroy
  private void close() throws SQLException {
    connection.close();
  }

  public Connection getConnection() {
    return connection;
  }

  private void createTable() {
    try (Statement statement = connection.createStatement()) {
      statement.execute(ddl);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }





}
