public class Driver {
	public static void main(String [] args) {
		double coeff1[] = {-2, 10, -4, 3};
		double coeff2[] = {2, -10, 4, -3};
		double coeff3[] = {-3, 4, 5, 6};
		int exp1[] = {1,2,3,4};
		
		Polynomial p1 = new Polynomial();
		Polynomial p2 = new Polynomial(coeff1, exp1);
		Polynomial p3 = new Polynomial(coeff2, exp1);
		Polynomial p4 = new Polynomial(coeff3, exp1);
		
		Polynomial result1 = p2.add(p3);
		System.out.println(result1.coefficients + " " + result1.exponents);
		System.out.println();
		Polynomial result2 = p2.add(p4);
		for(int i = 0; i < result2.coefficients.length; i++) {
			System.out.println(result2.coefficients[i] + " " + result2.exponents[i]);
		}
		System.out.println();
		
		Polynomial result3 = p2.multiply(p3);
		for(int i = 0; i < result3.coefficients.length; i++) {
			System.out.println(result3.coefficients[i] + " " + result3.exponents[i]);
		}
		System.out.println();
		
		System.out.println(p1.evaluate(1));
		System.out.println();
		
		p2.saveToFile("test.txt");
		System.out.println("done");

	}
}