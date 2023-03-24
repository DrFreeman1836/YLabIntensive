package third_lesson.org_structure;

import java.io.File;
import java.io.IOException;

public class Test {

  public static void main(String[] args) {
    OrgStructureParser parser = new OrgStructureParserImpl();
    try {
      Employee employee = parser.parseStructure(new File("resources/company.csv"));
      System.out.println(employee);
    } catch (IOException e) {
      System.out.println("Файл не найден");
    }
  }

}
