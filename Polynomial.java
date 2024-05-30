import java.util.*;
import java.io.*;

public class Polynomial {
	// fields
	double coefficients[];
	int exponents[];
	
	
	// constructor
	public Polynomial() {
		coefficients = null;
		exponents = null;
	}
	
	public Polynomial(double coefficients[], int exponents[]) {
		this.coefficients = coefficients;
		this.exponents = exponents;	
	}
	
	public Polynomial(File file) {
		try {
			Scanner s = new Scanner(file);
			
			String terms[] = s.nextLine().split("(?=-)|\\+");
			
			double coefficients[] = new double[terms.length];
			int exponents[] = new int[terms.length];
			
			for(int i = 0; i < terms.length; i++) {
//				System.out.println(terms[i]);
				int x = terms[i].indexOf('x');
				if(x == -1) {
					coefficients[i] = Double.parseDouble(terms[i]);
					exponents[i] = 0;
				}
				else {
					coefficients[i] = Double.parseDouble(terms[i].substring(0, x));
					exponents[i] = Integer.parseInt(terms[i].substring(x + 1));
				}
				
				this.coefficients = coefficients;
				this.exponents = exponents;
				
				s.close();
			}	
		}
		catch(IOException e) {
			System.out.println(e);
		}
	}
	
	// methods
	public Polynomial add(Polynomial a) {
		if(a.coefficients == null && this.coefficients == null) return new Polynomial();
		else if(a.coefficients == null) return new Polynomial(this.coefficients, this.exponents);
		else if(this.coefficients == null) return new Polynomial(a.coefficients, a.exponents);
		
		int num = 0;
		ArrayList<Integer> trackExp = new ArrayList<Integer>();
		
		for(int i = 0; i < Math.max(this.exponents.length, a.exponents.length); i++) {
			if(i < a.exponents.length && !trackExp.contains(a.exponents[i])) {
				num++;
				trackExp.add(a.exponents[i]);
			}
			if(i < this.exponents.length && !trackExp.contains(this.exponents[i])) {
				num++;
				trackExp.add(this.exponents[i]);
			}
		}
		
		double[] coefficients1 = new double[num];
		int[] exponents1 = new int[num];
		Arrays.setAll(exponents1, trackExp::get); //map Arraylist to an array of ints
		int zeroTerms = 0;
		
		for(int i = 0; i < Math.max(this.exponents.length, a.exponents.length); i++) {
			boolean next = false;
			for(int j = 0; j < exponents1.length; j++) {
				if((i < a.exponents.length) && (exponents1[j] == a.exponents[i])) {
					coefficients1[j] += a.coefficients[i];
					exponents1[j] = a.exponents[i];
					next = true;
				}
				if((i < this.exponents.length) && (exponents1[j] == this.exponents[i])) {
					coefficients1[j] += this.coefficients[i];
					if(coefficients1[j] == 0) zeroTerms++;
					exponents1[j] = this.exponents[i];
					next = true;
				}
				if(next) break;
			}
		}
		
//		System.out.println("abc1");
		if(num == zeroTerms) return new Polynomial();
		
		double[] coefficients2 = new double[num - zeroTerms];
		int[] exponents2 = new int[num - zeroTerms];
		int tr = 0;
		
		for(int i = 0; i < num; i++) {
			if(coefficients1[i] != 0) {
				coefficients2[tr] = coefficients1[i];
				exponents2[tr] = exponents1[i];
				tr++;
			}
		}
		
//		System.out.println("abc2");
		return new Polynomial(coefficients2, exponents2);
	}
	
	public double evaluate(double x) {
		if(this.coefficients == null) return 0;
		double count = 0;
		for(int i = 0; i < coefficients.length; i++) {
			count += (coefficients[i] * Math.pow(x, exponents[i]));
		}
		return count;
	}
	
	public boolean hasRoot(double x) {
		if(this.coefficients == null) return false;
		return (evaluate(x) == 0);
	}
	
	public Polynomial multiply(Polynomial a) {
		if(a.coefficients == null && this.coefficients == null) return new Polynomial();
		else if(a.coefficients == null) return new Polynomial(this.coefficients, this.exponents);
		else if(this.coefficients == null) return new Polynomial(a.coefficients, a.exponents);
	
		double coefficients1[] = new double[this.coefficients.length * a.coefficients.length];
		int exponents1[] = new int[this.exponents.length * a.exponents.length];
		
		ArrayList<Integer> trackExp = new ArrayList<Integer>();
			
		int num = 0;
		int tr = 0;
		for(int i = 0; i < this.coefficients.length; i++) {
			for(int j = 0; j < a.coefficients.length; j++) {
				coefficients1[tr] = this.coefficients[i] * a.coefficients[j];
				exponents1[tr] = this.exponents[i] + a.exponents[j];
				if(!trackExp.contains(exponents1[tr])) {
//					System.out.println(exponents1[tr]);
					num++;
					trackExp.add(exponents1[tr]);
				}
				tr++;
			}
		}
		
		double coefficients2[] = new double[num];
		int exponents2[] = new int[num];
		
		Arrays.setAll(exponents2, trackExp::get);
		
		for(int i = 0; i < exponents1.length; i++) {
			for(int j = 0; j < exponents2.length; j++) {
				if(exponents2[j] == exponents1[i]) {
					coefficients2[j] += coefficients1[i];
					break;
				}
			}
		}
		
		return new Polynomial(coefficients2, exponents2);
	}
	
	public void saveToFile(String fileName) {
		try {
			File file = new File(fileName);
			PrintWriter w = new PrintWriter(new FileWriter(file));
			String result = "";
			
			if(this.coefficients != null) {
				for(int i = 0; i < this.coefficients.length; i++) {
					if(i > 0 && this.coefficients[i] >= 0) result = result + "+";
					if(this.exponents[i] != 0) {
						result = result + this.coefficients[i] + "x" + this.exponents[i];
					}
					else {
						result = result + this.coefficients[i];
					}
				}
			}
			w.write(result);
			w.close();
		}
		catch(IOException e) {
			System.out.println(e);
		}
	}
}