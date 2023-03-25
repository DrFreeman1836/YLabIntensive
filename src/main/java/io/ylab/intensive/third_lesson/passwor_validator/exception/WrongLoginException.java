package io.ylab.intensive.third_lesson.passwor_validator.exception;

public class WrongLoginException extends Exception {

  public WrongLoginException() {
  }

  public WrongLoginException(String message) {
    super(message);
  }
}
