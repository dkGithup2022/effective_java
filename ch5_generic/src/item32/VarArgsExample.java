package item32;

import java.util.ArrayList;
import java.util.List;

public class VarArgsExample {

	public static void main(String[] args) {
		dangerous();

	}

	private static void dangerous(List<String>... args) {
		List<Integer> intList = List.of(42);
		Object[] objects = args;
		objects[0] = intList;    // heap pollution
		String s = args[0].get(0); // class cast exception
	}

	@SafeVarargs
	static <T> List<T> flatten(List<? extends T>... lists) {
		List<T> result = new ArrayList<>();
		for (List<? extends T> list : lists) {
			result.addAll(list);
		}
		return result;
	}
	/*
	제너릭 컬랙션에 대한 배열을 가지는건 아래와 같은 이유로 금기된다.
	그래서 원래는 문법적으로 제공하지 않으나 가변 인수에 제너릭 컬렉션을 받으면 아래와 같은 문제가 생기기도 한다 .

	이런 상황을 의도적으로 피하는 것이 상책이고

	그래도 써야한다면 @SafeVarargs 라는 어노테이션을 달고 함수를 작성할 수도 있음 .
	단 이런 어노테이션을 쓴다면 검사를 진짜 잘해야됨 .

	그리고 차라리 var args 를 받지 말고 List<List<? extends T>> 를 인자로 받는 것이 best practice 이다 .

	List<T> [] ts = ~~;
	Object[] os = ts;

	os.[0] = object.of("anyThing");
	return (T) os[0].get(0);
	ㄴ> 각 줄엔 문제가 없어 빌드타임엔 패스 되지만 런타입 캐스트 익셉션 발생 .

	* */
}

