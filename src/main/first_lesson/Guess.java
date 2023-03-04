package main.first_lesson;

import java.util.Random;
import java.util.Scanner;

public class Guess {

  public static void main(String[] args) {
    int number = new Random().nextInt(100);
    int maxAttempts = 10;
    System.out.println("Я загадал число. У тебя " + maxAttempts + " попыток угадать.");
    Scanner scanner = new Scanner(System.in);
    for (int i = maxAttempts - 1; i >= 0; i--) {
      int n = scanner.nextInt();
      if (n == number) {
        System.out.printf("Ты угадал с %s попытки!%n", maxAttempts - i);
        return;
      } else if (n > number) {
        System.out.printf("Мое число меньше! У тебя осталось %s попыток%n", i);
      } else {
        System.out.printf("Мое число больше! У тебя осталось %s попыток%n", i);
      }
    }
    System.out.println("Ты не угадал");
  }

}
