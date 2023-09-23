package goodEqualsMethod;

import java.util.Objects;

public class GoodEqualsMethod {
	public static void main(String[] args) {
		String s = "tmp";
		SensitiveString ss = new SensitiveString("tmp");

		System.out.println(s.equals(ss)); // false
		System.out.println(ss.equals(s)); // true

		//

		Point p1 = new Point(1,1);
		ColorPoint p2 = new ColorPoint(1,1, ColorPoint.COLOR.BLUE);
		ColorPoint p3 = new ColorPoint(1,1, ColorPoint.COLOR.RED);

		System.out.println(" 깨진 대칭성 ");
		System.out.println(p1.equals(p2)); 	// false
		System.out.println(p2.equals_broken_symmetry(p1)); // true


		System.out.println(" 깨진 추이성 ");

		System.out.println(p2.equals_broken_transitivity(p1)); // T
		System.out.println(p3.equals_broken_transitivity(p1)); // T
		System.out.println(p3.equals_broken_transitivity(p2)); // F
	}
}

class SensitiveString {
	private final String s;

	public SensitiveString(String s) {
		this.s = Objects.requireNonNull(s);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SensitiveString)
			return ((String)s).equals(obj);
		if (obj instanceof String)
			return s.equals(obj);
		return super.equals(obj);
	}
}

//

class Point {
	public int x, y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Point))
			return false;

		if (((Point)obj).x == x && ((Point)obj).y == y)
			return true;
		return false;
	}
}

class ColorPoint extends Point {

	public enum COLOR {RED, GREEN, BLUE}

	;

	private COLOR color;

	public ColorPoint(int x, int y, COLOR color) {
		super(x, y);
		this.color = color;
	}

	public boolean equals_broken_symmetry(Object obj) {
		if (!(obj instanceof ColorPoint))
			return false;

		return super.equals(obj) && ((ColorPoint)obj).color == color;
	}

	/*
	*
	* */
	public boolean equals_broken_transitivity(Object obj) {
		if (!(obj instanceof Point))
			return false;

		if (!(obj instanceof ColorPoint))
			return obj.equals(this);

		return super.equals(obj) && ((ColorPoint)obj).color == color;
	}

	/*
	 * 아래 같은 함수를 써버리면 아래의 상속받는 모든 함수들이 super.equals() 를 했을때 깨질수도 있다.
	 * final 인 경우를 제외하면 쓰면 안됨 .
	 * */
	public boolean equals_broken_liskov(Object obj) {
		if (obj == null || obj.getClass() != this.getClass())
			return false;

		return super.equals(obj) && ((ColorPoint)obj).color == this.color;
	}
}