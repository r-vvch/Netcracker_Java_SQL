package ru.skillbench.tasks.basics.math;

import java.util.Arrays;

public class ComplexNumberImpl implements ComplexNumber {

    private double real;
    private double imaginary;

    public ComplexNumberImpl() {

    }

    public ComplexNumberImpl(double re, double im) {
        real = re;
        imaginary = im;
    }

    public ComplexNumberImpl(String value) {
        this.set(value);
    }

//Getters and setters
    public double getRe() {
        return real;
    }

    public double getIm() {
        return imaginary;
    }

    public boolean isReal() {
        return imaginary == 0.0;
    }

    public void set(double re, double im) {
        real = re;
        imaginary = im;
    }

    private int indexOfSign(String str) {
        if (str.indexOf('+') != -1) {
            return str.indexOf('+');
        } else {
            return str.indexOf('-');
        }
    }

    private int lastIndexOfSign(String str) {
        if (str.lastIndexOf('+') != -1) {
            return str.lastIndexOf('+');
        } else {
            return str.lastIndexOf('-');
        }
    }

    public void set(String value) throws NumberFormatException {
        int iIndex = value.indexOf('i');
        if (value.indexOf('i') != value.lastIndexOf('i')) {
            throw new NumberFormatException();
        }
        if (iIndex == -1) {
            //        number is real
            real = new Double(value);
            imaginary = 0.0;
        } else if (iIndex == 0) {
            //        number is i
            real = 0.0;
            imaginary = 1.0;
        } else if ((indexOfSign(value) == lastIndexOfSign(value)) & (indexOfSign(value) == -1)) {
            //        number is imaginary
            real = 0;
            imaginary = new Double(value.substring(0, iIndex));
        } else if (indexOfSign(value) == lastIndexOfSign(value)) {
            //        first is positive
            if (iIndex == indexOfSign(value) + 1) {
                //      second is i or -i
                if (value.lastIndexOf('+') != -1) {
                    imaginary = 1;
                } else {
                    imaginary = -1;
                }
            } else {
                //      second is bi
                imaginary = new Double(value.substring(lastIndexOfSign(value), iIndex));
            }
            // done with imaginary
            value = value.substring(0, lastIndexOfSign(value));
            if (value.equals("")) {
                real = 0.0;
            } else {
                real = new Double(value);
            }
        } else if (indexOfSign(value) != lastIndexOfSign(value)) {
            // first is negative
            String realPart = value.substring(0, lastIndexOfSign(value));
            if (realPart.equals("")) {
                real = 0.0;
            } else {
                real = new Double(realPart);
            }
            //  done with real
            if (iIndex == lastIndexOfSign(value) + 1) {
                //  i or -i
                if (value.lastIndexOf('+') != -1) {
                    imaginary = 1;
                } else {
                    imaginary = -1;
                }
            } else {
                // bi
                imaginary = new Double(value.substring(lastIndexOfSign(value), iIndex));
            }
        } else {
            //        wrong input format
                throw new NumberFormatException();
            }
    }

//Methods of java.lang.Object and similar methods
    public ComplexNumber copy() {
        return new ComplexNumberImpl(real, imaginary);
    }

    public ComplexNumber clone() throws CloneNotSupportedException {
        return new ComplexNumberImpl(real, imaginary);
    }

    public String toString() {
        if (real != 0.0 & imaginary < 0.0) {
            return Double.toString(real) + Double.toString(imaginary) + 'i';
        } else if (real != 0.0 & imaginary > 0.0) {
            return Double.toString(real) + '+' + Double.toString(imaginary) + 'i';
        } else if (real != 0.0 & imaginary == 0.0) {
            return Double.toString(real);
        } else {
            return Double.toString(imaginary) + 'i';
        }
    }

    public boolean equals(Object other) {
        if (other instanceof ComplexNumber) {
            return (real == ((ComplexNumber)other).getRe() & imaginary == ((ComplexNumber)other).getIm());
        } else {
            return false;
        }
    }

    public int compareTo(ComplexNumber other) {
        Double mod1 = this.getRe() * this.getRe() + this.getIm() * this.getIm();
        Double mod2 = other.getRe() * other.getRe() + other.getIm() * other.getIm();
        return mod1.compareTo(mod2);
    }

    public void sort(ComplexNumber[] array) {
        Arrays.sort(array, ComplexNumber::compareTo);
    }

//Mathematical operations
    public ComplexNumber negate() {
        real *= -1.0;
        imaginary *= -1.0;
        return this;
    }

    public ComplexNumber add(ComplexNumber arg2) {
        real += arg2.getRe();
        imaginary += arg2.getIm();
        return this;
    }

    public ComplexNumber multiply(ComplexNumber arg2) {
//        this number is a+bi and arg2 is c+di
//        (a*c-b*d)+(b*c+a*d)i
//        a = this.getRe(), b = this.getIm()
//        c = arg2.getRe(), d = arg2.getIm()
        double srcReal = real;
        real = this.getRe() * arg2.getRe() - this.getIm() * arg2.getIm();
        imaginary = this.getIm() * arg2.getRe() + srcReal * arg2.getIm();
        return this;
//        return new ComplexNumberImpl(this.getRe() * arg2.getRe() - this.getIm() * arg2.getIm(),
//                                    this.getIm() * arg2.getRe() + this.getRe() * arg2.getIm());
    }
}
