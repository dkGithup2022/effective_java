package go_to_generic_method;

import java.util.HashSet;
import java.util.Set;
import java.util.function.UnaryOperator;

public class generic_method {
	public static void main(String[] args) {

		Set<String> s1 = Set.of("aa", "bb");
		Set<String> s2 = Set.of("bb", "cc");
		Set<String> s3 = union(s1, s2);
		System.out.println(s3);

		/* 안되는 예시 : 컴파일 에러*/
		Set<String> s4 = Set.of("aa", "bb");
		Set<Integer> s5 = Set.of(1, 2);
		//Set<String> s6 = union(s4, s5);

		UnaryOperator<String> identifyString = GenericSingletonFactory.identifyFunction();
		UnaryOperator<Integer> identifyInteger = GenericSingletonFactory.identifyFunction();

		Integer a = identifyInteger.apply(3);
		Integer b = identifyInteger.apply(4);
		String c = identifyString.apply("aa");
		System.out.println("a : " + a);
		System.out.println("c : " + c);

	}

	/*
	 *  제너릭 메서드의 예시  (1)
	 *  리턴 타입 전에 사용할 타입을 먼저 정의 한다.
	 * 스코프 내에서 E 는 모두 같은 타입이여야 한다.
	 * */
	public static <E> Set<E> union(Set<E> s1, Set<E> s2) {
		Set<E> set = new HashSet<>(s1);
		set.addAll(s2);
		return set;
	}

}

/*

프로듀서 -> 제너릭 값에 대입 되는 값
컨슈머 -> 제너릭 값을 대입할 값

producer extends  / consumer super
pecs 라고 핟 ㅏ.

* */

/*
 * 제너릭 메서드 예시 (2)
 * 제너릭 싱글턴 팩토리
 * -> 제너릭으로 타입을 지정 가능한 객체를 만들어두고, 반환시에 형변환 해서 쓰라는 뜻.
 * */

class GenericSingletonFactory {
	private static UnaryOperator<Object> IDENTIFY_FN = (t) -> t;

	public static <T> UnaryOperator<T> identifyFunction() {
		return (UnaryOperator<T>)IDENTIFY_FN;
	}
}
