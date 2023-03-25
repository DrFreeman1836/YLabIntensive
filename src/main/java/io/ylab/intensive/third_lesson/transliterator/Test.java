package io.ylab.intensive.third_lesson.transliterator;

public class Test {

  public static void main(String[] args) {
    Transliterator transliterator = new TransliteratorImpl();
    System.out.println(transliterator.transliterate("HEllo, My ДРУГ Вася"));
  }

}
