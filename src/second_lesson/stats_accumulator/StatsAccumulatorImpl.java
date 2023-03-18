package second_lesson.stats_accumulator;

public class StatsAccumulatorImpl implements StatsAccumulator {

  private int countNumber = 0;
  private Integer maxValue;
  private Integer minValue;
  private Double avg = 0.0;
  private int sum = 0;
  @Override
  public void add(int value) {
    countNumber++;
    setMinMaxValue(value);
    setAvg(value);
  }

  private void setAvg(int value) {
    sum += value;
    System.out.println(sum + " " + countNumber);
    avg = (double) sum / (double) countNumber;
  }

  private void setMinMaxValue(int value) {
    if (minValue == null) {
      minValue = value;
      maxValue = value;
    } else {
      maxValue = value > maxValue ? value : maxValue;
      minValue = value < minValue ? value : minValue;
    }
  }

  @Override
  public int getMin() {
    return minValue;
  }

  @Override
  public int getMax() {
    return maxValue;
  }

  @Override
  public int getCount() {
    return countNumber;
  }

  @Override
  public Double getAvg() {
    return avg;
  }
}
