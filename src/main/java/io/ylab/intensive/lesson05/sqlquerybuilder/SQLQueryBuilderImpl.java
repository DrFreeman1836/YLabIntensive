package io.ylab.intensive.lesson05.sqlquerybuilder;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SQLQueryBuilderImpl implements SQLQueryBuilder {

  private final DataSource dataSource;

  @Autowired
  public SQLQueryBuilderImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public String queryForTable(String tableName) throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      DatabaseMetaData databaseMetaData = connection.getMetaData();
      ResultSet rs = databaseMetaData.getColumns(null, null, tableName, null);
      List<String> listColumns = new ArrayList<>();
      while (rs.next()) {
        listColumns.add(rs.getString(4));
      }
      rs.close();
      return formQuery(listColumns, tableName);
    }
  }

  private String formQuery(List<String> listColumns, String table) {
    if (listColumns.isEmpty()) return null;
    StringBuilder sb = new StringBuilder("SELECT ");
    Iterator<String> it = listColumns.iterator();
    while (it.hasNext()) {
      sb.append(it.next());
      if (it.hasNext()) sb.append(", ");
    }
    sb.append(" FROM ").append(table);
    return sb.toString();
  }

  @Override
  public List<String> getTables() throws SQLException {
    List<String> listTables = new ArrayList<>();
    try (Connection connection = dataSource.getConnection()) {
      DatabaseMetaData databaseMetaData = connection.getMetaData();
      ResultSet rs = databaseMetaData.getTables(null, null, "", new String[]{"TABLE"});
      while (rs.next()) {
        listTables.add(rs.getString("TABLE_NAME"));
      }
      rs.close();
    }
    return listTables;
  }
}
