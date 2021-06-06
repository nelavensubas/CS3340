/*
 * Assignment 1: Question 7c
 */

public class asn1_c implements Comparable<asn1_c> {

	private String value;

	/*
	 * Constructor to create the object with an int or string.
	 * 
	 * @param value the number used to represent numerical value that might not fit
	 * within a primitive data type
	 */
	asn1_c(int value) {
		this.value = Integer.toString(value);
	}

	asn1_c(String value) {
		this.value = value;
	}

	/*
	 * Getter method for getting the value from the object.
	 * 
	 * @return the value from the object
	 */
	public String getValue() {
		return value;
	}

	/*
	 * Setter method for setting the value from the object. Can set the value with a
	 * string or int.
	 * 
	 * @param value the new value to set for the object.
	 */
	public void setValue(String value) {
		this.value = value;
	}

	public void setValue(int value) {
		this.value = Integer.toString(value);
	}

	/*
	 * Get an array of chars from the string value.
	 * 
	 * @return an array of chars from string value
	 */
	public char[] chars() {
		return this.value.toCharArray();
	}

	/*
	 * Get the length of the string.
	 * 
	 * @return an int for the length of a string
	 */
	public int length() {
		return value.length();
	}

	/*
	 * Compare the value from the object to another value.
	 * 
	 * @param x a numerical value
	 * 
	 * @return -1 if this < x, 0 if this == x, 1 if this > x
	 */
	@Override
	public int compareTo(asn1_c x) {
		if (this.length() > x.length())
			return 1;
		if (this.length() < x.length())
			return -1;

		char[] thisChars = this.chars();
		char[] xChars = x.chars();

		for (int i = 0; i < this.length(); i++) {
			int xInt = Character.getNumericValue(xChars[i]);
			int thisInt = Character.getNumericValue(thisChars[i]);

			if (thisInt > xInt)
				return 1;
			if (thisInt < xInt)
				return -1;
		}

		return 0;
	}

	/*
	 * Get two numbers as strings of the same length (adds zeroes to front if short
	 * than the other)
	 * 
	 * @param x the first number
	 * 
	 * @param y the second number
	 * 
	 * @return an array with 2 elements with zeroes added to the front of either
	 * number if it is shorter than the other
	 */
	private static String[] equalLengths(asn1_c x, asn1_c y) {
		String yVal = y.getValue();
		String xVal = x.getValue();

		if (x.length() > y.length())
			yVal = new String(new char[x.length() - y.length()]).replace("\u0000", "0") + y.getValue();
		else if (x.length() < y.length())
			xVal = new String(new char[y.length() - x.length()]).replace("\u0000", "0") + x.getValue();

		return new String[] { xVal, yVal };
	}

	/*
	 * Create the addition operation where this.value += x.value
	 * 
	 * @param x a numerical value
	 */
	public void plus(asn1_c x) {
		String[] xy = equalLengths(x, this);
		char[] xChars = xy[0].toCharArray();
		char[] yChars = xy[1].toCharArray();

		int overflow = 0; // number being carried over when sum goes over 10

		StringBuilder solution = new StringBuilder();
		for (int i = xChars.length - 1; i >= 0; i--) {
			int xInt = Character.getNumericValue(xChars[i]);
			int yInt = Character.getNumericValue(yChars[i]);
			int digitSum = xInt + yInt + overflow;

			solution.append(digitSum % 10);
			overflow = digitSum / 10;
		}
		// append the number if overlow is nonzero (it can only be 1 or 0)
		// then reverse the string builder because they are added backwards
		this.value = (overflow == 0 ? "" : "1") + solution.reverse().toString();
	}

	/*
	 * Create the subtraction operation where this.value -= x.value
	 * 
	 * @param x a numerical value
	 */
	public void minus(asn1_c x) {
		int thisGThanX = this.compareTo(x);

		if (thisGThanX == 0) // numbers are equal, difference is zero
			this.setValue("0");
		else if (thisGThanX < 0) // diff would be negative
			this.setValue("ERROR, negative not implemented");
		else { // x and 'this' values in an array
			String[] xThis = equalLengths(x, this);
			char[] xChars = xThis[0].toCharArray();
			char[] thisChars = xThis[1].toCharArray();

			int overflow = 0;
			StringBuilder difference = new StringBuilder();
			for (int i = xChars.length - 1; i >= 0; i--) // subtraction happens in reverse order
			{
				int xInt = Character.getNumericValue(xChars[i]);
				int thisInt = Character.getNumericValue(thisChars[i]) + overflow;
				overflow = 0; // either 0 or -1, -1 when you need to take a 1 from the next 10s place up

				if (thisInt < xInt) // when you can't subtract, take from next highest digit
				{
					overflow = -1;
					thisInt += 10;
				}
				// we know that with the overflow added, we will always be able to subtract
				difference.append(thisInt - xInt);
			}

			// trim zeroes
			// note the string builder is in reverse order, so leading zeroes are now at the
			// end
			while (difference.charAt(difference.length() - 1) == '0')
				difference.deleteCharAt(difference.length() - 1);

			this.setValue(difference.reverse().toString());
		}
	}

	/*
	 * Create the multiplication operation where this.value *= x.value
	 * 
	 * @param x a numerical value
	 */
	public void times(asn1_c x) {
		char[] xChars = x.chars();
		String num = this.getValue();
		this.setValue(0);

		for (int i = 0; i < x.length(); i++) { // for each digit in x, call the multiplyByInt helper
			int digit = Character.getNumericValue(xChars[xChars.length - 1 - i]);
			this.plus(new asn1_c(multiplyByInt(digit, num, i)));
		}
	}

	// Helper method for overloading default arguments.
	private static String multiplyByInt(int x, String num, int powerOf10) {
		return multiplyByInt(x, num, powerOf10, 0, new StringBuilder());
	}

	/*
	 * Divide by and set to new value
	 * 
	 * @param divisor the number to divide by
	 */
	public void divideBy(int divisor) {
		if (divisor == 0)
			this.setValue("undefined");
		else {
			StringBuilder quotient = new StringBuilder();
			int overflow = 0;
			char[] dividend = this.chars();

			for (int i = 0; i < dividend.length; i++) {
				int digit = overflow * 10 + Character.getNumericValue(dividend[i]);
				quotient.append(digit / divisor);
				overflow = digit % divisor;
			}

			// trim zeroes
			while (quotient.charAt(0) == '0')
				quotient.deleteCharAt(0);
			this.setValue(quotient.toString());
		}
	}

	// Pops last digit, multiplies by x, adds it to string builder, sb to string on
	// exit condition
	private static String multiplyByInt(int x, String num, int powerOf10, int overflow, StringBuilder sb) {
		// recursive exit condition will return string from sb, add overflow if nonzero
		if (num.length() == 0) {
			return (overflow == 0 ? "" : Integer.toString(overflow)) + sb.reverse().toString()
					+ new String(new char[powerOf10]).replace("\u0000", "0");
		}

		// add last digit * x to string builder
		final char digit = num.toCharArray()[num.length() - 1];
		final int product = Character.getNumericValue(digit) * x + overflow;
		sb.append(product % 10);

		// num loses last char, overflow calculated from product
		return multiplyByInt(x, num.substring(0, num.length() - 1), powerOf10, product / 10, sb);
	}

	/*
	 * Calculate the n-th Fibonacci number.
	 * 
	 * @param n the number to calculate
	 * 
	 * @return the n-th Fibonacci number
	 */
	public static asn1_c fibonacciOnTime(int n) {
		if (n <= 1) {
			return new asn1_c(n);
		} else if (n == 0) {
			return new asn1_c(0);
		} else if (n == 1) {
			return new asn1_c(1);
		}

		asn1_c[][] A = new asn1_c[][] { { new asn1_c(1), new asn1_c(1) }, { new asn1_c(1), new asn1_c(0) } };
		matrixPower(A, n - 1);

		return A[0][0];
	}

	/*
	 * Calculate the power of a given matrix.
	 * 
	 * @param A the matrix
	 * 
	 * @param N the power which will be N-1, where N is the Nth Fibonacci number
	 */
	public static void matrixPower(asn1_c[][] A, int N) {
		if (N <= 1) {
			return;
		}

		matrixPower(A, N / 2);
		multiplyMatrix(A, A);

		asn1_c[][] B = new asn1_c[][] { { new asn1_c(1), new asn1_c(1) }, { new asn1_c(1), new asn1_c(0) } };

		if (N % 2 != 0) {
			multiplyMatrix(A, B);
		}
	}

	/*
	 * Multiply 2 matrices.
	 * 
	 * @param A the first matrix
	 * 
	 * @param B the second matrix
	 */
	public static void multiplyMatrix(asn1_c[][] A, asn1_c[][] B) {
		asn1_c x1 = new asn1_c(A[0][0].getValue());
		x1.times(B[0][0]);
		asn1_c x2 = new asn1_c(A[0][1].getValue());
		x2.times(B[1][0]);
		x1.plus(x2);

		asn1_c y1 = new asn1_c(A[0][0].getValue());
		y1.times(B[0][1]);
		asn1_c y2 = new asn1_c(A[0][1].getValue());
		y2.times(B[1][1]);
		y1.plus(y2);

		asn1_c z1 = new asn1_c(A[1][0].getValue());
		z1.times(B[0][0]);
		asn1_c z2 = new asn1_c(A[1][1].getValue());
		z2.times(B[1][0]);
		z1.plus(z2);

		asn1_c w1 = new asn1_c(A[1][0].getValue());
		w1.times(B[0][1]);
		asn1_c w2 = new asn1_c(A[1][1].getValue());
		w2.times(B[1][1]);
		w1.plus(w2);

		A[0][0] = x1;
		A[0][1] = y1;
		A[1][0] = z1;
		A[1][1] = w1;
	}

	public static void main(String[] args) {
		for (int i = 0; i <= 25; i++) {
			System.out.println(fibonacciOnTime(i * 20).getValue());
		}
	}
}