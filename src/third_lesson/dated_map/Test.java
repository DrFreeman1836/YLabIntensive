package third_lesson.dated_map;

public class Test {

  public static void main(String[] args) {
    DatedMapImpl map = new DatedMapImpl();
    map.put("hi", "buy");
    map.put("hi2", "buy3");
    map.remove("hi2");
    System.out.println(map.getKeyLastInsertionDate("hi2"));
  }

}
