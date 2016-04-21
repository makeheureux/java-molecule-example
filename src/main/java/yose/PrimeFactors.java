package yose;

import java.util.Arrays;

public class PrimeFactors {

	public static int[] factorOfTwo(int n) {
		int x = n;
		int i = 0;
		while (x > 1) {
			i++;
			x = x / 2;
		}

		int[] result = new int[i];
		Arrays.fill(result, 2);
		return result;
	}

}
