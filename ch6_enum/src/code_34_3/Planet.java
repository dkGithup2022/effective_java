package code_34_3;

public enum Planet {

	MERCURY(3.302e+23, 3.439e6),
	VENUS(4.86e+24, 6.05e6),
	EARTH(5.95e+24, 6.378e6),
	;

	private final double mass;
	private final double radius;
	private final double surfaceGravity;

	/**
	 열거형 생성자와 필드에 대해,
	 1. 열거형 생성자의 호출 시점
	 -> 열거형 상수는 싱글 인스턴스이기 때문에 로드됨과 동시에 초기화 된다.

	 2. 타입의 갯수와 파라미터
	 -> 위처럼 파라미터는 3개, 선언 시 인자는 2개여도 상관 없다. 생성자에서 값을 생성해 줄수도 있음
	 -> 아래가 예시 .
	 */

	Planet(double mass, double radius) {
		this.mass = mass;
		this.radius = radius;
		this.surfaceGravity = G * mass / (radius * radius);
	}

	private static final double G = 6.67E-11;

	public double surfaceWeight(double mass) {
		return mass * surfaceGravity;
	}
}
