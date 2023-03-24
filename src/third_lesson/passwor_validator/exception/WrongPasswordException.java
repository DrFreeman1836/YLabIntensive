package third_lesson.passwor_validator.exception;

public class WrongPasswordException extends Exception {

  public WrongPasswordException() {
  }

  public WrongPasswordException(String message) {
    super(message);
  }
}
