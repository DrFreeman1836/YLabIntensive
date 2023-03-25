package io.ylab.intensive.second_lesson.stats_accumulator;

public class StatsAccumulatorTest {

  public static void main(String[] args) {
    StatsAccumulator accumulator = new StatsAccumulatorImpl();
    accumulator.add(2);
    accumulator.add(1);
    accumulator.add(1);
    System.out.printf("max - %s  min - %s%n", accumulator.getMax(), accumulator.getMin());
    System.out.printf("count - %s  avg - %s", accumulator.getCount(), accumulator.getAvg());
  }

}
