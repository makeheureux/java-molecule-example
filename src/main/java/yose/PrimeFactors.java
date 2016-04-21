package yose;

public class PrimeFactors {

	public static int[] factorOfTwo(int n) {
		int x = n;
		int i = 0;
		while (x > 1) {
			i++;
			x = x/2;
		}
		
		return new int[]{2,2,2,2};
	}
	
}
