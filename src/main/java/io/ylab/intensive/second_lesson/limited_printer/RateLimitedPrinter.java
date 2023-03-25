package io.ylab.intensive.second_lesson.limited_printer;

public class RateLimitedPrinter {

  private int interval = 0;

  private long lastOutput = 0;

  public RateLimitedPrinter(int interval) {
    this.interval = interval;
  }

  public void print(String message) {
    long currentTime = System.currentTimeMillis();
    if (currentTime - lastOutput > interval) {
      System.out.println(message);
      lastOutput = currentTime;
    }
  }
}
