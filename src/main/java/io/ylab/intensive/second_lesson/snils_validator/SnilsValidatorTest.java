package io.ylab.intensive.second_lesson.snils_validator;

public class SnilsValidatorTest {

  public static void main(String[] args) {
    SnilsValidator validator = new SnilsValidatorImpl();
    System.out.println(validator.validate("01468870570"));
    System.out.println(validator.validate("90114404441"));
  }

}
