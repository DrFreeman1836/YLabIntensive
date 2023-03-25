package io.ylab.intensive.first_lesson;

import java.util.Scanner;

public class Pell {

  public static void main(String[] args) {
    try (Scanner scanner = new Scanner(System.in)) {
      int n = scanner.nextInt();
      System.out.println(getPellNumber(n));
    }

  }

  private static int getPellNumber(int serialNumber) {
    int[] massPell = new int[31];
    massPell[0] = 0;
    massPell[1] = 1;
    for (int i = 2; i <= 30; i++) {
      massPell[i] = 2 * massPell[i-1] + massPell[i-2];
    }
    return massPell[serialNumber];
  }

}