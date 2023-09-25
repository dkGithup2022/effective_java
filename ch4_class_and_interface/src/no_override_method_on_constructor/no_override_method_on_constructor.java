package no_override_method_on_constructor;

import java.time.Instant;

public class no_override_method_on_constructor {
	public static void main(String[] args) {
		Sub sub = new Sub();
		sub.overrideMe();
		/*
		null
		2023-09-25T11:57:23.330612Z

		순서는 아래와 같다 .
		1. super 생성자 호출
		2. sub 생성자 호출
			-> Instant 객체 생성
		->따라서 super 생성자에선 overrideMe() 가 null 을 출력.
		*/

	}
}

class Super {
	public Super() {
		overrideMe();
	}

	public void overrideMe() {
	}
}

class Sub extends Super {
	private final Instant instant;

	Sub() {
		this.instant = Instant.now();
	}

	@Override
	public void overrideMe() {
		System.out.println(instant);
	}
}
