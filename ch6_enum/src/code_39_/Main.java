package code_39_;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
	public static void main(String... args) throws
		ClassNotFoundException,
		InvocationTargetException,
		IllegalAccessException {
		int tests = 0;
		int passed = 0;
		Class<?> testClass = Class.forName("code_39_.Track");
		for (Method m : testClass.getDeclaredMethods()) {

			if (!m.isAnnotationPresent(Test39.class))
				continue;

			System.out.println("detected method : " + m.getName());
			tests++;
			try {
				m.invoke(null);
				passed++;
			} catch (InvocationTargetException exception) {
				Throwable exc = exception.getCause();
				System.out.println(m + " 실패 : " + exc);
			} catch (Exception e) {
				System.out.println("잘못 사용한 @Test39" + m);
			}
		}
	}

}

class Track {
	@Test39
	public static void m1() {
	}

	;

	@Test39
	public static void m2() {
	}

	;

	@Test39
	public static void m3() {
		throw  new RuntimeException("FAIL ONE");
	}

	;
}