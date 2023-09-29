package code_34_4;

public class Main {
	public static void main(String[] args) {
		double one = 1.0;
		double two = 2.0;

		for(Operation op : Operation.values())
			System.out.println(op.apply(one,two));
	}
}
