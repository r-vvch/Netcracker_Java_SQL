package ru.skillbench.tasks.basics.math;

import java.util.Arrays;

/**
 * GOALS<ul>
 * <li>To learn OOP basics: basic principles of writing a class, getters/setters, <code>this</code> keyword.</li>
 * <li>To get familiar with some methods of {@link Object} and with {@link Comparable} interface.</li>
 * <li>To be able to use some java.lang and java.itil API: {@link String}, {@link Double}, {@link Arrays}.</li>
 * </ul>
 * <p/>
 * TASK<br/>
 * Implement a class storing a single complex number with basic mathematical operations on it.<br/>
 * Instances of the class must be comparable, cloneable and representable as String (in the common format).
 * <p/>
 * REQUIREMENTS<ul>
 * <li>The complex number consists of two components: real part (re) and imaginary part (im).
 *   They should be stored as private fields (members of the class) of type double or Double.
 *   After instantiation of the number with default constructor both re and im must be equal to 0.</li>
 * <li>Every math operation on the number (add, negate, multiply methods) should change the state of this object 
 *   not creating a new object (this requirement is intended to save memory and to lower calculation time). 
 *   For a caller's convenience, all these methods (math operations) return this object itself.</li>
 * <li>The common requirement for any task (to check it online) is to declare your class as follows:<br/>
 * <code>public class ComplexNumberImpl implements ComplexNumber {  } </code></li>
 * </ul>
 * <p/>
 * NOTES<ul>
 * <li>When coded with the methods of this interface, formulas like z = x-y^2 could look like this:<br/>
 *  <code>ComplexNumber z = x.add(y.copy().multiply(y).negate());</code><br/>
 *  where <code>x</code>, <code>y</code> are objects of type ComplexNumber.</li>
 * <li>The task can be solved without any knowledge of Exceptions, but if you know that topic 
 *  explicit generation of {@link NumberFormatException} in {@link #set(String)} method is recommended.</li>
 * <li>It's recommended (but not required) to have the following set of constructors:<br/>
 *   <code>public ComplexNumberImpl(double re, double im);</code><br/>
 *   <code>public ComplexNumberImpl(String value);</code><br/>
 *   <code>public ComplexNumberImpl();</code> (this no-argument constructor is required by the checking system).</li>
 * <li>If you don't understand the mathematical meaning of the complex number you can read e.g. 
 *  <a href="http://en.wikipedia.org/wiki/Complex_number">Complex Number at Wikipedia</a>.</li>
 * <li>A professional Java implementation of this concept can be found here:
 *  <a href="http://commons.apache.org/proper/commons-math/userguide/complex.html">Complex in Apache Commons Math</a>.</li>
 *  </ul>
 * 
 * @author Alexey Evdokimov
 */
public interface ComplexNumber extends Comparable<ComplexNumber>, Cloneable {
//Getters and setters
	/**
	 * @return real part of this complex number
	 */
	double getRe();
	/**
	 * @return imaginary part of this complex number
	 */
	double getIm();
	/**
	 * @return true if this complex number has real part only (otherwise false)
	 */
	boolean isReal();
	/**
	 * Sets both real and imaginary part of this number.
	 * @param re
	 * @param im
	 */
	void set(double re, double im);
	
	/**
	 * Parses the given string value and sets the real and imaginary parts of this number accordingly.<br/>
	 * The string format is "re+imi", where re and im are numbers (floating point or integer) and 'i' is a special symbol
	 *  denoting imaginary part (if present, it's always the last character in the string).<br/>
	 * Both '+' and '-' symbols can be the first characters of re and im; but '*' symbol is NOT allowed.<br/>
	 * Correct examples: "-5+2i", "1+i", "+4-i", "i", "-3i", "3". Incorrect examples: "1+2*i", "2+2", "j".<br/>
	 * Note: explicit exception generation is an OPTIONAL requirement for this task, 
	 *   but NumberFormatException can be thrown by {@link Double#parseDouble(String)}).<br/>
	 * Note: it is not reasonable to use regex while implementing this method: the parsing logic is too complicated.
	 * @param value
	 * @throws NumberFormatException if the given string value is incorrect
	 */
	void set(String value) throws NumberFormatException;

//Methods of java.lang.Object and similar methods
	/**
	 * Creates and returns a copy of this object: <code>x.copy().equals(x)</code> but <code>x.copy()!=x</code>.
	 * @see #clone()
	 * @return the copy of this number
	 */
	ComplexNumber copy();
	/**
	 * Creates and returns a copy of this object: the same as {@link #copy()}.<br/>
	 * Note: when implemented in your class, this method overrides the {@link Object#clone()} method but changes 
	 * the visibility and the return type of the Object's method: the visibility modifier is changed 
	 * from protected to public, and the return type is narrowed from Object to ComplexNumber (see also 
	 * <a href="http://docs.oracle.com/javase/specs/jls/se7/html/jls-8.html#d5e11368">covariant types example from JLS</a>).
	 * @see Object#clone()
	 * @return the copy of this number
	 */
	ComplexNumber clone() throws CloneNotSupportedException;
	
	/**
	 * Returns a string representation of this number, which must be compatible with {@link #set(String)}:
	 * for any ComplexNumber x, the code <code>x.set(x.toString());</code> must not change x.<br/>
	 * For example: 12.5-1.0i or 0.0 or 0.3333333333333333i<br/>
	 * If the imaginary part of the number is 0, only "re" must be returned (where re is the real part).<br/>
	 * If the real part of the number is 0 and the imaginary part is not 0, 
	 *  "imi" must be returned (where im is the imaginary part).<br/>
	 * Both re and im must be converted to string "as is" - without truncation of last digits, 
	 * i.e. the number of characters in their string representation is not limited 
	 *   (it is determined by {@link Double#toString(double)}).
	 * @see Object#toString()
	 */
	String toString();
	/**
	 * Checks whether some other object is "equal to" this number.
	 * @param other Any implementation of {@link ComplexNumber} interface (may not )
	 * @see Object#equals(Object)
	 */
	boolean equals(Object other);
	
//Ordering methods
	/**
	 * Compares this number with the other number by the absolute values of the numbers: 
	 * x < y if and only if |x| < |y| where |x| denotes absolute value (modulus) of x.<br/>
	 * Can also compare the square of the absolute value which is defined as the sum 
	 * of the real part squared and the imaginary part squared: |re+imi|^2 = re^2 + im^2.
	 * @see Comparable#compareTo(Object)
	 * @param other the object to be compared with this object.
	 * @return a negative integer, zero, or a positive integer as this object
	 *   is less than, equal to, or greater than the given object.
	 */
	int compareTo(ComplexNumber other);
	/**
	 * Sorts the given array in ascending order according to the comparison rule defined in 
	 *   {@link #compareTo(ComplexNumber)}.<br/>
	 * It's strongly recommended to use {@link Arrays} utility class here
	 *   (and do not transform the given array to a double[] array).<br/>
	 * Note: this method could be static: it does not use this instance of the ComplexNumber.
	 *    Nevertheless, it is not static because static methods can't be overridden.
	 * @param array an array to sort
	 */
	void sort(ComplexNumber[] array);
	
//Mathematical operations
	/**
	 * Changes the sign of this number. Both real and imaginary parts change their sign here.
	 * @return this number (the result of negation)
	 */
	ComplexNumber negate();
	/**
	 * Adds the given complex number arg2 to this number. Both real and imaginary parts are added.
	 * @param arg2 the second operand of the operation
	 * @return this number (the sum)
	 */
	ComplexNumber add(ComplexNumber arg2);
	/**
	 * Multiplies this number by the given complex number arg2. If this number is a+bi and arg2 is c+di then
	 * the result of their multiplication is (a*c-b*d)+(b*c+a*d)i<br/>
	 * The method should work correctly even if arg2==this. 
	 * @param arg2 the second operand of the operation
	 * @return this number (the result of multiplication)
	 */
	ComplexNumber multiply(ComplexNumber arg2);
}
