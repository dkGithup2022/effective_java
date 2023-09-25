package immutable;

public class immutable {
	public static void main(String[] args) {
		Complex c1 = new Complex(1,3);
		Complex c2 = new Complex(3,1);
		Complex c3 = c1.plus(c2);
		Complex c4 = c1.minus(c2);

		System.out.println("C1 Address: "  + c1);
		System.out.println("C2 Address: "  + c2);
		System.out.println("C3 Address: "  + c3);
		System.out.println("C4 Address: "  + c4);

	}

	private static class Complex {
		private final double re;
		private final double im;

		public Complex(double re, double im) {
			this.re = re;
			this.im = im;
		}

		public Complex plus(Complex c) {
			return new Complex(re + c.re, im + c.im);
		}

		public Complex minus(Complex c) {
			return new Complex(re - c.re, im - c.im);
		}
	}
}
