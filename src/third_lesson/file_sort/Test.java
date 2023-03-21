package third_lesson.file_sort;

import java.io.File;
import java.io.IOException;

public class Test {

  public static void main(String[] args) throws IOException {
    File dataFile = new Generator().generate("data.txt", 6);
    //File dataFile = new File("data.txt");

//    Files.readAllLines(Path.of("data.txt")).forEach(f -> {
//      System.out.println(f);
//    });
//    Files.lines(Path.of("data.txt")).forEach(f -> {
//      System.out.println(f);
//    });

    System.out.println(new Validator(dataFile).isSorted());
    File sortedFile = new Sorter().sortFile(dataFile);
    System.out.println(new Validator(sortedFile).isSorted());
  }


}
