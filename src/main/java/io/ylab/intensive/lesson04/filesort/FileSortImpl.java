package io.ylab.intensive.lesson04.filesort;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class FileSortImpl implements FileSorter {

  private DataSource dataSource;

  private static final String QUERY_INSERT = "insert into numbers (val) values(?)";

  private static final String QUERY_SELECT = "select * from numbers order by val desc";

  public FileSortImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public File sort(File data) {
    File result = new File("result");
    try {
      List<Long> listLong = Files.readAllLines(data.toPath()).stream().map(Long::valueOf).toList();
      saveData(listLong);
      List<Long> resultList = getSortedList();
      writeToFile(resultList, result);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  private List<Long> getSortedList() throws SQLException {
    List<Long> listLong = new ArrayList<>();
    try (Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(QUERY_SELECT)) {
      while (rs.next()) {
        listLong.add(rs.getLong(1));
      }
    }
    return listLong;
  }

  private void writeToFile(List<Long> listLong, File file) throws FileNotFoundException {
    try (PrintWriter writer = new PrintWriter(file)) {
      listLong.forEach(writer::println);
      writer.flush();
    }
  }

  private void saveData(List<Long> listLong) throws SQLException {
    try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT)) {

      for (Long value : listLong) {
        preparedStatement.setLong(1, value);
        preparedStatement.addBatch();
      }
      preparedStatement.executeBatch();

    }
  }

}
