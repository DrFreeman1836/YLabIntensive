package io.ylab.intensive.lesson04.movie;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;
import javax.sql.DataSource;

public class MovieLoaderImpl implements MovieLoader {

  private DataSource dataSource;

  private static final String INSERT_QUERY =
      "insert into movie (year, length, title, subject, actors, actress, director, popularity, awards)"
          + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

  public MovieLoaderImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public void loadData(File file) {

    try {
      Scanner scanner = new Scanner(file);
      scanner.nextLine();
      scanner.nextLine();
      while (scanner.hasNext()) {
        String line = scanner.nextLine();
        saveMovie(readMovie(line));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  private Movie readMovie(String line) {
    String[] data = line.split(";");
    Movie movie = new Movie();
    movie.setYear(data[0].isEmpty() ? null : Integer.valueOf(data[0]));
    movie.setLength(data[1].isEmpty() ? null : Integer.valueOf(data[1]));
    movie.setTitle(data[2].isEmpty() ? null : data[2]);
    movie.setSubject(data[3].isEmpty() ? null : data[3]);
    movie.setActors(data[4].isEmpty() ? null : data[4]);
    movie.setActress(data[5].isEmpty() ? null : data[5]);
    movie.setDirector(data[6].isEmpty() ? null : data[6]);
    movie.setPopularity(data[7].isEmpty() ? null : Integer.valueOf(data[7]));
    movie.setAwards(data[8].isEmpty() ? null : Boolean.valueOf(data[8]));
    return movie;
  }

  private void saveMovie(Movie movie) throws SQLException {
    try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {

      setValue(1, movie.getYear(), preparedStatement);
      setValue(2, movie.getLength(), preparedStatement);
      setValue(3, movie.getTitle(), preparedStatement);
      setValue(4, movie.getSubject(), preparedStatement);
      setValue(5, movie.getActors(), preparedStatement);
      setValue(6, movie.getActress(), preparedStatement);
      setValue(7, movie.getDirector(), preparedStatement);
      setValue(8, movie.getPopularity(), preparedStatement);
      setValue(9, movie.getAwards(), preparedStatement);

      preparedStatement.executeUpdate();
    }
  }

  private void setValue(int index, Integer value, PreparedStatement preparedStatement) throws SQLException {
    if (value != null) {
      preparedStatement.setObject(index, value);
    } else {
      preparedStatement.setNull(index, Types.INTEGER);
    }
  }

  private void setValue(int index, String value, PreparedStatement preparedStatement) throws SQLException {
    if (value != null) {
      preparedStatement.setObject(index, value);
    } else {
      preparedStatement.setNull(index, Types.VARCHAR);
    }
  }

  private void setValue(int index, Boolean value, PreparedStatement preparedStatement) throws SQLException {
    if (value != null) {
      preparedStatement.setObject(index, value);
    } else {
      preparedStatement.setNull(index, Types.BOOLEAN);
    }
  }


}
