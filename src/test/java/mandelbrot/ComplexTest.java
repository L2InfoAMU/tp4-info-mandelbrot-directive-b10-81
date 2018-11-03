package mandelbrot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ComplexTest {

  private final Complex onePlusI = new Complex(1, 1);

  private final Complex minusI = new Complex(0, -1);

  private final Complex minusOne = new Complex(-1, 0);

  private final Complex oneMinusI = new Complex(1, -1);

  private final Complex twoI = new Complex(0, 2);

  private final Complex two = new Complex(2, 0);

  private final double real = -12;

  private final double imaginary = 10;


  @Test
  void testConstructor () {
    assertEquals(0., twoI.real, Helpers.EPSILON);
    assertEquals(2., twoI.imaginary, Helpers.EPSILON);
    assertEquals(1., oneMinusI.real, Helpers.EPSILON);
    assertEquals(-1., oneMinusI.imaginary, Helpers.EPSILON);
    assertEquals(2., two.real, Helpers.EPSILON);
    assertEquals(0., two.imaginary, Helpers.EPSILON);
  }

  @Test
  void testGetReal () {
    assertEquals(2., two.getReal(), Helpers.EPSILON);
    assertEquals(1., oneMinusI.getReal(), Helpers.EPSILON);
    assertEquals(-1., new Complex(-1, 1).getReal(), Helpers.EPSILON);
    assertEquals(real, new Complex(real, imaginary).getReal(), Helpers.EPSILON);
  }

  @Test
  void testGetImaginary () {
    assertEquals(2., twoI.getImaginary(), Helpers.EPSILON);
    assertEquals(1., new Complex(-1, 1).getImaginary(), Helpers.EPSILON);
    assertEquals(-1., oneMinusI.getImaginary(), Helpers.EPSILON);
    assertEquals(imaginary, new Complex(real, imaginary).getImaginary(), Helpers.EPSILON);
  }

  @Test
  void testOne () {
    assertEquals(1., Complex.ONE.getReal());
    assertEquals(0., Complex.ONE.getImaginary());
  }

  @Test
  void testI () {
    assertEquals(0, Complex.I.getReal());
    assertEquals(1, Complex.I.getImaginary());
  }

  @Test
  void testZero () {
    assertEquals(0, Complex.ZERO.getReal());
    assertEquals(0, Complex.ZERO.getImaginary());
  }

  @Test
  void testReal () {
    assertEquals(Complex.ZERO, Complex.real(0));
    assertEquals(minusOne, Complex.real(-1));
    assertEquals(two, Complex.real(2));
    assertEquals(-12, Complex.real(real).getReal(), Helpers.EPSILON);
    assertEquals(0, Complex.real(real).getImaginary(), Helpers.EPSILON);
  }

  @Test
  void testNegate () {
    assertEquals(minusOne, Complex.ONE.negate());
    assertEquals(Complex.I, minusI.negate());
    assertEquals(new Complex(-1, 1), oneMinusI.negate());
    assertEquals(new Complex(real, imaginary), new Complex(-real, -imaginary).negate());
  }

  @Test
  void testReciprocal () {
    assertEquals(Complex.ONE, Complex.ONE.reciprocal());
    assertEquals(Complex.I, minusI.reciprocal());
    assertEquals(new Complex(0.5, 0), two.reciprocal());
    assertEquals(new Complex(0.5, 0.5), oneMinusI.reciprocal());
  }

  @Test
  void testReciprocalOfZero () {
    assertThrows(ArithmeticException.class, () -> Complex.ZERO.reciprocal());
  }

  @Test
  void testAdd () {
    assertEquals(two, Complex.ONE.add(Complex.ONE));
    assertEquals(two, onePlusI.add(oneMinusI));
    assertEquals(Complex.ZERO, Complex.ONE.add(minusOne));
    Complex c = new Complex(real, imaginary);
    assertEquals(new Complex(real + real, imaginary + imaginary), c.add(c));
  }

  @Test
  void testSubstract () {
    assertEquals(minusOne, Complex.ZERO.subtract(Complex.ONE));
    assertEquals(oneMinusI, Complex.ONE.subtract(Complex.I));
    assertEquals(new Complex(real - 1, imaginary - 1),
      new Complex(real, imaginary).subtract(onePlusI));
  }

  @Test
  void testMultiply () {
    assertEquals(minusOne, Complex.I.multiply(Complex.I));
    assertEquals(Complex.ONE, minusOne.multiply(minusOne));
    assertEquals(Complex.ONE, Complex.I.multiply(minusI));
    assertEquals(Complex.ZERO, Complex.ZERO.multiply(new Complex(real, imaginary)));
    assertEquals(Complex.ZERO, new Complex(real, imaginary).multiply(Complex.ZERO));
  }

  @Test
  void testSquaredModulus () {
    assertEquals(0, Complex.ZERO.squaredModulus());
    assertEquals(1, Complex.ONE.squaredModulus());
    assertEquals(1, Complex.I.squaredModulus());
    assertEquals(1, minusI.squaredModulus());
    assertEquals(4, two.squaredModulus());
    assertEquals(0.25, new Complex(0.5, 0).squaredModulus());
    Complex complex = new Complex(real, imaginary);
    Complex complexByConjugate = complex.multiply(complex.conjugate());
    assertEquals(complexByConjugate.getReal() + complexByConjugate.getImaginary(), complex.squaredModulus());
  }

  @Test
  void testModulus () {
    assertEquals(0, Complex.ZERO.modulus());
    assertEquals(1, Complex.ONE.modulus());
    assertEquals(1, Complex.I.modulus());
    assertEquals(1, minusI.modulus());
    assertEquals(2, two.modulus());
    assertEquals(Math.sqrt(2), onePlusI.modulus());
    assertEquals(0.5, new Complex(0.5, 0).modulus());
    Complex complex = new Complex(real, imaginary);
    assertEquals(Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2)),
      complex.modulus());
  }

  @Test
  void testDivide () {
    assertEquals(onePlusI, onePlusI.divide(Complex.ONE));
    assertEquals(new Complex(0.5, 0), Complex.ONE.divide(two));
    assertEquals(minusI, oneMinusI.divide(onePlusI));
  }

  @Test
  void testDivideByZero () {
    assertThrows(ArithmeticException.class, () -> Complex.ONE.divide(Complex.ZERO));
  }

  @Test
  void testPow () {
    Complex complex = new Complex(real, imaginary);

    assertEquals(two, two.pow(1));
    assertEquals(Complex.ONE, Complex.ZERO.pow(0));
    assertEquals(Complex.ONE, complex.pow(0));
    assertEquals(complex, complex.pow(1));

    complex = onePlusI.pow(4);
    assertEquals(Math.pow(Math.sqrt(2), 4), complex.modulus(), Helpers.EPSILON);
    assertEquals(-1, complex.getReal() / complex.modulus(), Helpers.EPSILON);
    assertEquals(0, complex.getImaginary() / complex.modulus(), Helpers.EPSILON);

    for (int i = 0; i < 20; i++) {
      switch (i % 4) {
        case 0 : assertEquals(Complex.ONE, Complex.I.pow(i)); break;
        case 1 : assertEquals(Complex.I, Complex.I.pow(i)); break;
        case 2 : assertEquals(minusOne, Complex.I.pow(i)); break;
        case 3 : assertEquals(minusI, Complex.I.pow(i)); break;
      }
    }
  }

  @Test
  void testScale () {
    assertEquals(two, Complex.ONE.scale(2));
    assertEquals(two, two.scale(1));
    assertEquals(twoI, Complex.I.scale(2));
    assertEquals(Complex.ONE, two.scale(0.5));
    assertEquals(minusI, Complex.I.scale(-1));
    assertEquals(Complex.ZERO, new Complex(real, imaginary).scale(0));
    assertEquals(new Complex(real, real), onePlusI.scale(real));
  }

  @Test
  void testEquals () {
    Complex complex = new Complex(real, imaginary);

    assertTrue(complex.equals(complex));
    assertFalse(complex.equals(null));
    assertFalse(complex.equals(new String("obviously not a complex")));
    //less obvious
    assertFalse(Complex.ONE.equals(1));
    // ok then
    assertTrue(Complex.ONE.equals(Complex.real(1)));

    assertTrue(complex.equals(new Complex(real, imaginary)));
    assertFalse(complex.equals(new Complex(imaginary, real)));
    assertFalse(complex.equals(new Complex(real, real)));
    assertFalse(complex.equals(new Complex(imaginary, imaginary)));
  }

  @Test
  void testConjugate () {
    assertEquals(Complex.ZERO, Complex.ZERO.conjugate());
    assertEquals(Complex.ONE, Complex.ONE.conjugate());
    assertEquals(onePlusI, oneMinusI.conjugate());
    assertEquals(new Complex(real, -imaginary), new Complex(real, imaginary).conjugate());
  }

  @Test
  void testRotation () {
    assertEquals(Complex.I, Complex.rotation(Math.PI / 2));
    assertEquals(minusI, Complex.rotation(-Math.PI / 2));
    assertEquals(Complex.ONE, Complex.rotation(0));
    assertEquals(new Complex(Math.sqrt(2) / 2., Math.sqrt(2) / 2.),
      Complex.rotation(Math.PI / 4));
    assertEquals(new Complex(1. / 2., Math.sqrt(3) / 2.),
      Complex.rotation(Math.PI / 3));
  }

  @Test
  void testToString () {
    assertEquals("Complex{real=1.0, imaginary=-1.0}", oneMinusI.toString());
    assertEquals("Complex{real=" + real + ", imaginary=" + imaginary + "}", new Complex(real, imaginary).toString());
  }

  @Test
  void testHashCode () {
    Complex c1 = new Complex(real, imaginary);
    Complex c2 = new Complex(real, imaginary);
    assertEquals(c1.hashCode(), c2.hashCode());
  }
}
