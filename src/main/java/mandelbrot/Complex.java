package mandelbrot;

import java.util.Objects;

/**
 * A class to represent complex numbers and their arithmetic.
 * <p>
 * Complex numbers are immutable.
 */
public class Complex {

  /**
   * Zero as a complex number
   */
  public static final Complex ZERO = new Complex(0, 0);

  /**
   * One as a complex number
   */
  public static final Complex ONE = new Complex(1, 0);

  /**
   * The complex number whose square is -1
   */
  public static final Complex I = new Complex(0, 1);

  /**
   * The real part of a complex number.
   */
  private final double real;

  /**
   * The imaginary part of a complex number.
   */
  private final double imaginary;


  /**
   * Creates a complex number given the real and the imaginary components
   *
   * @param real      real component
   * @param imaginary imaginary component
   */
  public Complex (double real, double imaginary) {
    this.real = real;
    this.imaginary = imaginary;
  }

  /**
   * Creates complex numbers corresponding to rotations
   *
   * @param radians the angle of the rotation (counterclockwise) in radians
   * @return a complex number, whose multiplication corresponds to a rotation by the given angle.
   */
  public static Complex rotation (double radians) {
    return new Complex(Math.cos(radians), Math.sin(radians));
  }

  /**
   * Creates a complex number with null imaginary part
   *
   * @param real the real component
   * @return the complex <code>real + 0 i</code>
   */
  public static Complex real (double real) {
    return new Complex(real, 0);
  }

  public double getReal () {
    return real;
  }

  public double getImaginary () {
    return imaginary;
  }

  /**
   * Addition of two complex numbers
   *
   * @param addend a complex
   * @return the complex {@code this + addend}
   */
  public Complex add (Complex addend) {
    return new Complex(this.real + addend.real,
      this.imaginary + addend.imaginary);
  }

  /**
   * The negation of a complex number
   *
   * @return A complex <code>c</code> such that <code>this + c = 0</code>
   */
  public Complex negate () {
    return new Complex(-this.real, -this.imaginary);
  }

  /**
   * The conjugate of a complex number
   *
   * @return A complex <code>c</code> such that <code>this * c = ||this|| ** 2</code>
   */
  public Complex conjugate () {
    return new Complex(this.real, -this.imaginary);
  }

  /**
   * Subtraction of two complex numbers
   *
   * @param subtrahend the complex to be subtracted from <code>this</code>
   * @return the complex number <code>this - subtrahend</code>
   */
  public Complex subtract (Complex subtrahend) {
    return new Complex(this.real - subtrahend.real, this.imaginary - subtrahend.imaginary);
  }

  /**
   * Multiplication of two complex numbers
   *
   * @param factor the complex number to multiply to <code>this</code>
   * @return the complex number {@code this * factor}
   */
  public Complex multiply (Complex factor) {
    return new Complex(
      this.real * factor.real - this.imaginary * factor.imaginary,
      this.real * factor.imaginary + this.imaginary * factor.real
    );
  }

  /**
   * Squared modulus of a complex number
   *
   * @return <code>||this|| ** 2</code>
   */
  public double squaredModulus () {
    return real * real + imaginary * imaginary;
  }

  /**
   * Modulus (distance to zero) of a complex number
   *
   * @return <code>||this||</code>
   */
  public double modulus () {
    return Math.sqrt(squaredModulus());
  }


  /**
   * reciprocal of a complex number
   *
   * @return a complex number <code>c</code> such that <code>this * c = 1</code>
   */
  public Complex reciprocal () {
    if (this.equals(ZERO))
      throw new ArithmeticException("divide by zero");

    return this.conjugate().scale(1 / squaredModulus());
  }

  /**
   * Division of two complex numbers
   *
   * @param divisor the denominator (a complex number)
   * @return the complex number <code>this / divisor</code>
   */
  public Complex divide (Complex divisor) {
    return this.multiply(divisor.reciprocal());
  }


  /**
   * Integral power of a complex number
   *
   * @param p a non-negative integer
   * @return the complex number <code>this ** p</code>
   */
  public Complex pow (int p) {
    if (p == 0)
      return ONE;
    Complex result = (this.multiply(this)).pow(p / 2);
    if (p % 2 == 1)
      result = result.multiply(this);
    return result;
  }

  /**
   * Scalar multiplication of a complex number
   *
   * @param lambda a scalar number
   * @return the complex number <code>lambda * this</code>
   */
  public Complex scale (double lambda) {
    return new Complex(lambda * real, lambda * imaginary);
  }


  @Override
  public boolean equals (Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Complex complex = (Complex) o;
    return Helpers.doubleCompare(complex.real, real) == 0 &&
      Helpers.doubleCompare(complex.imaginary, imaginary) == 0;
  }

  @Override
  public int hashCode () {
    return Objects.hash(real, imaginary);
  }


  @Override
  public String toString () {
    return "Complex{" +
      "real=" + real +
      ", imaginary=" + imaginary +
      '}';
  }
}
