package third_lesson.file_sort;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Sorter {

  private final Integer SIZE_PART = 2;

  private List<String> listParts = new ArrayList<>();

  public File sortFile(File dataFile) throws IOException {
    splitFile(dataFile);
    File result = new File("result.txt");


    return mergeTwoPart(new File(listParts.get(0)), new File(listParts.get(1)));
  }

  private File mergeTwoPart(File file1, File file2) throws IOException {
    File semiResult = new File("semi_result.txt");
    PrintWriter writer = new PrintWriter(semiResult);
    Scanner scanner1 = new Scanner(new FileInputStream(file1));
    Scanner scanner2 = new Scanner(new FileInputStream(file2));

    while (scanner1.hasNextLong()) {
      Long value1 = scanner1.nextLong();

      while (scanner2.hasNextLong()) {
        Long value2 = scanner2.nextLong();
        if (value1 >= value2) {
          writer.println(value2);
          continue;
        }
        writer.println(value1);
        break;
      }

      if (!scanner2.hasNextLong()) {
       writer.println(value1);
      }
    }

    writer.flush();
    writer.close();
    scanner2.close();
    scanner1.close();

    return semiResult;
  }

  private void splitFile(File dataFile) throws IOException {
    try (Scanner scanner = new Scanner(new FileInputStream(dataFile))) {
      int i = 0;
      int part = 0;
      List<Long> partData = new ArrayList<>();
      while (scanner.hasNextLong()) {
        if (i == 0) {
          partData = new ArrayList<>();
        }
        if (i < SIZE_PART) {
          partData.add(scanner.nextLong());
          i++;
        }
        if (i == SIZE_PART || !scanner.hasNextLong()) {
          writePartToFile(partData, String.valueOf(part));
          part++;
          i = 0;
        }
      }
    }
  }

  private void writePartToFile(List<Long> partData, String name) throws IOException {
    Collections.sort(partData);
    File file = new File(name + ".txt");
    FileWriter writer = new FileWriter(file.getName());
    partData.stream().forEach(v -> {
      try {
        writer.write(v + System.lineSeparator());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    listParts.add(file.getPath());
    writer.flush();
    writer.close();
  }

}

