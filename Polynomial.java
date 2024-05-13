public class Polynomial {
	// fields
	double coefficients[];
	
	
	// constructor
	public Polynomial() {
		coefficients = new double[1];
	}
	
	public Polynomial(double coefficients[]) {
		this.coefficients = coefficients;
	}
	
	
	// methods
	public Polynomial add(Polynomial a) {
		double sumOfPolynomials[] = new double[Math.max(this.coefficients.length, a.coefficients.length)];
		
		for(int i = 0; i < Math.max(this.coefficients.length, a.coefficients.length); i++) {
			if(i < a.coefficients.length) {
				sumOfPolynomials[i] += a.coefficients[i];
			}
			if(i < this.coefficients.length) {
				sumOfPolynomials[i] += this.coefficients[i];
			}
		}
		
		return new Polynomial(sumOfPolynomials);
	}
	
	public double evaluate(double x) {
		double count = 0;
		for(int i = 0; i < coefficients.length; i++) {
			count += (coefficients[i] * Math.pow(x, i));
		}
		return count;
	}
	
	public boolean hasRoot(double x) {
		return (evaluate(x) == 0);
	}
}