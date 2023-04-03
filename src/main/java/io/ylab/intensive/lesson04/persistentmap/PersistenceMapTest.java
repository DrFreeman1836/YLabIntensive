package io.ylab.intensive.lesson04.persistentmap;

import io.ylab.intensive.lesson04.DbUtil;
import java.sql.SQLException;
import javax.sql.DataSource;

public class PersistenceMapTest {
  public static void main(String[] args) throws SQLException {
    DataSource dataSource = initDb();
    PersistentMap persistentMap = new PersistentMapImpl(dataSource);
    persistentMap.init("first");
    persistentMap.put("a", "A");
    persistentMap.put("b", "B");
    persistentMap.put("c", "C");
    System.out.println("value - " + persistentMap.get("b"));
    persistentMap.getKeys().forEach(System.out::println);

    System.out.println();
    System.out.println("second");
    persistentMap.init("second");
    persistentMap.put("1", "one");
    persistentMap.put("2", "two");
    persistentMap.getKeys().forEach(System.out::println);

    persistentMap.remove("1");
    persistentMap.getKeys().forEach(System.out::println);

    persistentMap.put("2", "два");
    System.out.println(persistentMap.get("2"));

    persistentMap.clear();
    persistentMap.getKeys().forEach(System.out::println);
  }
  
  public static DataSource initDb() throws SQLException {
    String createMapTable = "" 
                                + "drop table if exists persistent_map; " 
                                + "CREATE TABLE if not exists persistent_map (\n"
                                + "   map_name varchar,\n"
                                + "   KEY varchar,\n"
                                + "   value varchar\n"
                                + ");";
    DataSource dataSource = DbUtil.buildDataSource();
    DbUtil.applyDdl(createMapTable, dataSource);
    return dataSource;
  }
}
