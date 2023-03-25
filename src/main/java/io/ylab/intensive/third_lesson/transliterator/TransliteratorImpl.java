package io.ylab.intensive.third_lesson.transliterator;

import java.util.HashMap;
import java.util.regex.Pattern;

public class TransliteratorImpl implements Transliterator {

  private HashMap<String, String> alphabet;

  private final Pattern SYMBOL = Pattern.compile("[А-Я]");

  public TransliteratorImpl() {
    alphabet = new HashMap<>();
    alphabet.put("А", "A");
    alphabet.put("Б", "B");
    alphabet.put("В", "V");
    alphabet.put("Г", "G");
    alphabet.put("Д", "D");
    alphabet.put("Е", "E");
    alphabet.put("Ё", "E");
    alphabet.put("Ж", "ZH");
    alphabet.put("З", "Z");
    alphabet.put("И", "I");
    alphabet.put("Й", "I");
    alphabet.put("К", "K");
    alphabet.put("Л", "L");
    alphabet.put("М", "M");
    alphabet.put("Н", "N");
    alphabet.put("О", "O");
    alphabet.put("П", "P");
    alphabet.put("Р", "R");
    alphabet.put("С", "S");
    alphabet.put("Т", "T");
    alphabet.put("У", "U");
    alphabet.put("Ф", "F");
    alphabet.put("Х", "KH");
    alphabet.put("Ц", "TS");
    alphabet.put("Ч", "CH");
    alphabet.put("Ш", "SH");
    alphabet.put("Щ", "SHCH");
    alphabet.put("Ы", "Y");
    alphabet.put("Ь", "");
    alphabet.put("Ъ", "IE");
    alphabet.put("Э", "E");
    alphabet.put("Ю", "IU");
    alphabet.put("Я", "IA");
  }

  @Override
  public String transliterate(String source) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < source.length(); i++) {
      char c = source.charAt(i);
      String symbol = String.valueOf(c);
      if (SYMBOL.matcher(symbol).matches()) {
        sb.append(alphabet.get(symbol));
      } else {
        sb.append(symbol);
      }
    }
    return sb.toString();
  }
}
