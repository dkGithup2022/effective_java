package code_34_3;

public class Main {
	public static void main(String[] args) {
		for (Planet p : Planet.values())
			System.out.printf("%s 에서의 무게는 %f이다. \n", p, p.surfaceWeight(1.0));
	}
}
