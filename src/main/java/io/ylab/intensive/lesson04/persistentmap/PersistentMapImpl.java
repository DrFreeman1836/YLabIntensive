package io.ylab.intensive.lesson04.persistentmap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 * Класс, методы которого надо реализовать
 */
public class PersistentMapImpl implements PersistentMap {

  private DataSource dataSource;

  private String currentMap;

  private static final String QUERY_INSERT =
      "insert into persistent_map (map_name, key, value) values (?, ?, ?)";

  private static final String KEYS_SELECT =
      "select key from persistent_map where map_name = ?";

  private static final String GET_SELECT =
      "select value from persistent_map where map_name = ? and key = ?";

  private static final String QUERY_CLEAR =
      "delete from persistent_map where map_name = ?";

  private static final String QUERY_REMOVE =
      "delete from persistent_map where map_name = ? and key = ?";

  public PersistentMapImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public void init(String name) {
    currentMap = name;
  }

  @Override
  public boolean containsKey(String key) throws SQLException {
    boolean exist;
    try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_SELECT)) {
      preparedStatement.setString(1, currentMap);
      preparedStatement.setString(2, key);
      ResultSet rs = preparedStatement.executeQuery();
      exist = rs.next();
      rs.close();
    }
    return exist;
  }

  @Override
  public List<String> getKeys() throws SQLException {
    List<String> listKeys = new ArrayList<>();
    try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(KEYS_SELECT)) {

      preparedStatement.setString(1, currentMap);
      ResultSet rs = preparedStatement.executeQuery();
      while (rs.next()) {
        listKeys.add(rs.getString(1));
      }
      rs.close();

    }
    return listKeys;
  }

  @Override
  public String get(String key) throws SQLException {
    String value = null;
    try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_SELECT)) {
      preparedStatement.setString(1, currentMap);
      preparedStatement.setString(2, key);
      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        value = rs.getString(1);
      }
      rs.close();
    }
    return value;
  }

  @Override
  public void remove(String key) throws SQLException {
    try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_REMOVE)) {
      preparedStatement.setString(1, currentMap);
      preparedStatement.setString(2, key);
      preparedStatement.execute();
    }
  }

  @Override
  public void put(String key, String value) throws SQLException {
    try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT)) {
      if (containsKey(key)) {
        remove(key);
      }
      preparedStatement.setString(1, currentMap);
      preparedStatement.setString(2, key);
      preparedStatement.setString(3, value);
      preparedStatement.executeUpdate();
    }
  }

  @Override
  public void clear() throws SQLException {
    try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_CLEAR)) {
        preparedStatement.setString(1, currentMap);
        preparedStatement.execute();
    }
  }
}
