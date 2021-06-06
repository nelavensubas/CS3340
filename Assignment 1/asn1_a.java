/*
 * Assignment 1: Question 7a
 */

public class asn1_a {
	/*
	 * A recursive function to compute the F(n) definition given in 7a.
	 * 
	 * @param n	calculates the fibonacci number at position n
	 * @return the fibonacci number
	 */
	public static long fibonacciRecursion(int n) {
		if (n <= 1) {
			return n;
		} else if (n == 0) {
			return 0;
		} else if (n == 1) {
			return 1;
		}
		return fibonacciRecursion(n - 1) + fibonacciRecursion(n - 2);
	}

	public static void main(String[] args) {
		for(int i = 0; i <= 10; i++) {
			System.out.println(fibonacciRecursion(i*5));
		}
	}
}