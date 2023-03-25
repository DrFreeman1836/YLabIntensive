package io.ylab.intensive.third_lesson.file_sort;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;

public class Sorter {

  private final Integer SIZE_PART = 1000;

  private Deque<File> listParts = new ArrayDeque<>();

  public File sortFile(File dataFile) throws IOException {
    splitFile(dataFile);

    while (listParts.size() >= 2) {
      File f1 = listParts.getFirst();
      listParts.removeFirst();
      File f2 = listParts.getFirst();
      listParts.removeFirst();
      listParts.addLast(mergeTwoPart(f1, f2, String.valueOf(listParts.size())));
    }

    return listParts.getLast();
  }

  private File mergeTwoPart(File file1, File file2, String name) throws IOException {
    File semiResult = new File(name + "result.txt");
    PrintWriter writer = new PrintWriter(semiResult);
    Scanner scanner1 = new Scanner(new FileInputStream(file1));
    Scanner scanner2 = new Scanner(new FileInputStream(file2));

    boolean v1 = true;
    boolean v2 = true;
    Long value1 = 0L;
    Long value2 = 0L;
    while (scanner1.hasNextLong() || scanner2.hasNextLong()) {
      if (v1)
        value1 = scanner1.hasNextLong() ? scanner1.nextLong() : Long.MAX_VALUE;
      if (v2)
        value2 = scanner2.hasNextLong() ? scanner2.nextLong() : Long.MAX_VALUE;

      if (value1 <= value2) {
        writer.println(value1);
        v2 = false;
        v1 = true;
      } else {
        writer.println(value2);
        v2 = true;
        v1 = false;
      }
    }

    if (v1 && value2 != Long.MAX_VALUE) {
      writer.println(value2);
    }
    if (v2 && value1 != Long.MAX_VALUE) {
      writer.println(value1);
    }

    writer.flush();
    writer.close();
    scanner1.close();
    scanner2.close();
    deletePart(file1, file2);

    return semiResult;
  }

  private void deletePart(File file1, File file2) {
    try {
      Files.delete(file1.toPath());
      Files.delete(file2.toPath());
    } catch (IOException e) {
      e.printStackTrace();
    }
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
    listParts.add(file);
    writer.flush();
    writer.close();
  }

}

