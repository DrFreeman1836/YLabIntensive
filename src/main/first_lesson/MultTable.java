package main.first_lesson;

public class MultTable {

  public static void main(String[] args) {
    for (int i = 1; i <= 9; i++) {
      for (int k = 1; k <= 9; k++) {
        System.out.printf("%s * %s = %s%n", i, k, i * k);
      }
    }
  }

}
