package third_lesson.passwor_validator;

import java.util.regex.Pattern;
import third_lesson.passwor_validator.exception.WrongLoginException;
import third_lesson.passwor_validator.exception.WrongPasswordException;

public class PasswordValidator {

  private static final Pattern VALID_SYMBOL = Pattern.compile("^[a-zA-Z_0-9]+$");

  public static void main(String[] args) {
    System.out.println(validator("gggkkkkk9kkkkkkkk_D", "dfsd", "dfsd"));
  }

  public static boolean validator(String login, String password, String confirmPassword) {
    try {
      loginValidator(login);
      passwordValidator(password, confirmPassword);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public static void loginValidator(String login) throws WrongLoginException {
    if (!VALID_SYMBOL.matcher(login).matches()) {
      throw new WrongLoginException("Логин содержит недопустимые символы");
    }
    if (login.length() >= 20) {
      throw new WrongLoginException("Логин слишком длинный");
    }
  }

  public static void passwordValidator(String password, String confirmPassword) throws WrongPasswordException {
    if (!VALID_SYMBOL.matcher(password).matches()) {
      throw new WrongPasswordException("Пароль содержит недопустимые символы");
    }
    if (password.length() >= 20) {
      throw new WrongPasswordException("Пароль слишком длинный");
    }
    if (!password.equals(confirmPassword)) {
      throw new WrongPasswordException("Пароль и подтверждение не совпадают");
    }
  }

}
