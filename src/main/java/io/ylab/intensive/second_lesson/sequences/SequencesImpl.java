package io.ylab.intensive.second_lesson.sequences;

public class SequencesImpl implements Sequences {

  @Override
  public void a(int n) {
    long startValue = 2;
    for (int i = 0; i < n; i++) {
      System.out.println(startValue);
      startValue += 2;
    }
  }

  @Override
  public void b(int n) {
    long startValue = 1;
    for (int i = 0; i < n; i++) {
      System.out.println(startValue);
      startValue += 2;
    }
  }

  @Override
  public void c(int n) {
    long startValue = 1;
    int inc = 3;
    for (int i = 0; i < n; i++) {
      System.out.println(startValue);
      startValue += inc;
      inc += 2;
    }
  }

  @Override
  public void d(int n) {
    for (int i = 1; i <= n; i++) {
      System.out.println((int) Math.pow(i, 3));
    }
  }

  @Override
  public void e(int n) {
    long startValue = 1;
    int inc = -2;
    for (int i = 0; i < n; i++) {
      System.out.println(startValue);
      startValue += inc;
      inc *= -1;
    }
  }

  @Override
  public void f(int n) {
    for (int i = 1; i <= n; i++) {
      System.out.println(i % 2 == 0 ? i * -1 : i);
    }
  }

  @Override
  public void g(int n) {
    long startValue = 1;
    int inc = 3;
    for (int i = 1; i <= n; i++) {
      System.out.println(i % 2 == 0 ? startValue * -1 : startValue);
      startValue += inc;
      inc += 2;
    }
  }

  @Override
  public void h(int n) {
    long startValue = 1;
    for (int i = 1; i <= n; i++) {
      System.out.println(i % 2 == 0 ? 0 : startValue++);
    }
  }

  @Override
  public void i(int n) {
    long startValue = 1;
    int inc = 2;
    for (int i = 0; i < n; i++) {
      System.out.println(startValue);
      startValue *= inc;
      inc++;
    }
  }

  @Override
  public void j(int n) {
    int[] fibo = new int[n];
    fibo[0] = 0;
    fibo[1] = 1;
    for (int i = 1; i < n; i++) {
      int value1 = fibo[i-1];
      int value2 = fibo[i < 2 ? 1 : i-2];
      fibo[i] = value1 + value2;
      System.out.println(fibo[i]);
    }
  }
}
