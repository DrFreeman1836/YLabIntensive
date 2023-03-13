package second_lesson.complex_numbers;

public class ComplexNumberTest {

  public static void main(String[] args) {
    ComplexNumber complex1 = new ComplexNumber(2.0, 3.0);
    ComplexNumber complex2 = new ComplexNumber(5.0, -7.0);
    System.out.println(complex1.mul(complex2));

    ComplexNumber complex3 = new ComplexNumber(2.0, -1.0);
    ComplexNumber complex4 = new ComplexNumber(5.0, 9.0);
    System.out.println(complex3.add(complex4));

    ComplexNumber complex5 = new ComplexNumber(2.0);
    ComplexNumber complex6 = new ComplexNumber(5.0, 1.0);
    System.out.println(complex5.mul(complex6));

    ComplexNumber complex7 = new ComplexNumber(2.0, -4.0);
    ComplexNumber complex8 = new ComplexNumber(5.0, 4.0);
    System.out.println(complex7.diff(complex8));

    System.out.println(complex1.module());

  }

}