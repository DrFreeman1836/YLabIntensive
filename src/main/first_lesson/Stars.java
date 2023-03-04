package main.first_lesson;

import java.util.Scanner;

public class Stars {

  public static void main(String[] args) {
    try (Scanner scanner = new Scanner(System.in)) {
      int n = scanner.nextInt();
      int m = scanner.nextInt();
      String template = scanner.next();
      StringBuilder sb = new StringBuilder();
      for (int i = 1; i <= n; i++) {
        for (int k = 1; k <= m; k++) {
          sb.append(k < m ? template + " " : template);
        }
        sb.append(i < n ? "\n" : "");
      }
      System.out.println(sb.toString());
    }
  }

}
