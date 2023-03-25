package io.ylab.intensive.second_lesson.complex_numbers;

public class ComplexNumber {
  private Double real;
  private Double imaginary;

  ComplexNumber(Double real) {
    this.real = real;
    imaginary = 0.0;
  }

  ComplexNumber(Double real, Double imaginary) {
    this.real = real;
    this.imaginary = imaginary;
  }

  public ComplexNumber add(ComplexNumber summand) {
    Double sumReal = real + summand.getReal();
    Double sumImage = imaginary + summand.getImage();
    return new ComplexNumber(sumReal, sumImage);
  }

  public ComplexNumber diff(ComplexNumber subtrahend) {
    Double diffReal = real - subtrahend.getReal();
    Double diffImage = imaginary - subtrahend.getImage();
    return new ComplexNumber(diffReal, diffImage);
  }

  public ComplexNumber mul(ComplexNumber multiplier) {
    Double multiplierReal = real * multiplier.getReal() - imaginary * multiplier.getImage();
    Double multiplierImage = imaginary * multiplier.getReal() + real * multiplier.getImage();
    return new ComplexNumber(multiplierReal, multiplierImage);
  }

  public Double module() {
    return Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2));
  }

  public double getReal() {
    return real;
  }

  public double getImage() {
    return imaginary;
  }

  @Override
  public String toString() {
    return String.format("%s %s %s%s"
        , real
        , imaginary < 0 ? "-" : imaginary == 0 ? "" : "+"
        , imaginary == 1 || imaginary == 0 ? "" : imaginary < 0 ? imaginary * -1 : imaginary
        , imaginary == 0 ? "" : "i");
  }
}
