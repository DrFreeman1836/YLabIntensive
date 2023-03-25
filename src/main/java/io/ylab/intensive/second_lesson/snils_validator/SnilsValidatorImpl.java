package io.ylab.intensive.second_lesson.snils_validator;

public class SnilsValidatorImpl implements SnilsValidator {

  @Override
  public boolean validate(String snils) {
    if (!snils.matches("\\d{11}")) return false;
    Integer control = Integer.valueOf(snils.substring(9));
    String checkedValue = snils.substring(0, 9);

    int ctrl = 0;
    for (int pos = 1; pos <= checkedValue.length(); pos++) {
      char symbol = checkedValue.charAt(checkedValue.length() - pos);
      ctrl += Character.digit(symbol, 10) * pos;
    }

    if (ctrl == 100)
      ctrl = 0;

    if (ctrl > 100)
      ctrl = ctrl % 101 == 100 ? 0 : ctrl % 101;

    return control.equals(ctrl);
  }
}
