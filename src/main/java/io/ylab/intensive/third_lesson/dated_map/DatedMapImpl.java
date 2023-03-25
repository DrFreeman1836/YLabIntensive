package io.ylab.intensive.third_lesson.dated_map;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class DatedMapImpl implements DatedMap {

  private HashMap<String, String> data;
  private HashMap<String, Date> metaDate;

  public DatedMapImpl() {
    data = new HashMap<>();
    metaDate = new HashMap<>();
  }

  @Override
  public void put(String key, String value) {
    data.put(key, value);
    metaDate.put(key, new Date());
  }

  @Override
  public String get(String key) {
    return data.get(key);
  }

  @Override
  public boolean containsKey(String key) {
    return data.containsKey(key);
  }

  @Override
  public void remove(String key) {
    data.remove(key);
    metaDate.remove(key);
  }

  @Override
  public Set<String> keySet() {
    return data.keySet();
  }

  @Override
  public Date getKeyLastInsertionDate(String key) {
    return metaDate.getOrDefault(key, null);
  }
}
