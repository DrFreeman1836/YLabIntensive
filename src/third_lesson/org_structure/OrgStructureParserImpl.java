package third_lesson.org_structure;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrgStructureParserImpl implements OrgStructureParser {

  private List<Employee> employeeList = new ArrayList<>();

  @Override
  public Employee parseStructure(File csvFile) throws IOException {
    parseEmployeeCsv(csvFile);
    employeeList.forEach(e -> {
      fillBoss(e);
      fillSubordinate(e);
    });

    return employeeList.stream().filter(e -> e.getBossId() == null).findAny().get();
  }

  private void fillBoss(Employee employee) {
    employee.setBoss(findEmployeeById(employee.getBossId()));
  }

  private void fillSubordinate(Employee employee) {
    List<Employee> subordinate = employeeList.stream()
        .filter(e -> e.getBossId() != null)
        .filter(e -> e.getBossId().equals(employee.getId())).toList();
    employee.getSubordinate().addAll(subordinate);
  }

  private Employee findEmployeeById(Long id) {
    return employeeList.stream().filter(e -> e.getId().equals(id)).findAny().orElse(null);
  }

  private void parseEmployeeCsv(File csvFile) {
    try (Scanner scanner = new Scanner(new FileInputStream(csvFile))) {
      boolean next = false;
      while (scanner.hasNextLine()) {
        String[] employeeMeta = scanner.nextLine().split(";");
        if (!next || employeeMeta.length != 4) {
          next = true;
          continue;
        }
        Employee employee = new Employee();
        employee.setId(employeeMeta[0].isEmpty() ? null : Long.valueOf(employeeMeta[0]));
        employee.setBossId(employeeMeta[1].isEmpty() ? null : Long.valueOf(employeeMeta[1]));
        employee.setName(employeeMeta[2]);
        employee.setPosition(employeeMeta[3]);
        employeeList.add(employee);
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

}
