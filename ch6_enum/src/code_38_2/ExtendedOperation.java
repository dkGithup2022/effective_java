package code_38_2;

public enum ExtendedOperation implements Operation {
	MULTI("*") {
		@Override
		public double apply(double x, double y) {
			return x * y;
		}
	}, DIVIDE("/") {
		@Override
		public double apply(double x, double y) {
			return x / y;
		}
	};

	private final String symbol;

	ExtendedOperation(String symbol) {
		this.symbol = symbol;
	}

	;

	@Override
	public abstract double apply(double x, double y);
}
