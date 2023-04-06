package io.ylab.intensive.lesson05.messagefilter.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DbClient {

  private final Connection connection;

  private static final String ddl = ""
      + "create table censorship (\n"
      + "word varchar\n"
      + ")";

  @Autowired
  public DbClient(DataSource dataSource) throws SQLException {
    this.connection = dataSource.getConnection();
  }

  @PostConstruct
  private void init() {
    try {
      DatabaseMetaData databaseMetaData = connection.getMetaData();
      ResultSet rs = databaseMetaData.getTables(null, null,
          "censorship", new String[]{"TABLE"});
      if (!rs.next()) {
        createTable();
      }
      fillTable();
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  @PreDestroy
  private void close() throws SQLException {
    connection.close();
  }

  public Connection getConnection() {
    return connection;
  }

  private void createTable() throws SQLException {
    try (Statement statement = connection.createStatement()) {
      statement.execute(ddl);
    }
  }

  private static final String SQL_INSERT = "insert into censorship (word) values(?)";
  private void fillTable() throws SQLException {
    clearTable();
    try (Scanner scanner = new Scanner(new FileInputStream("censorship.txt"));
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT)) {
      while (scanner.hasNext()) {
        preparedStatement.setString(1, scanner.nextLine());
        preparedStatement.addBatch();
      }
      preparedStatement.executeBatch();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private static final String SQL_DELETE = "delete from censorship";
  private void clearTable() {
    try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE)) {
      preparedStatement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
