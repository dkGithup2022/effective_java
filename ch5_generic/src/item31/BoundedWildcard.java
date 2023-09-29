package item31;

import java.util.ArrayList;
import java.util.List;

public class BoundedWildcard {
	public static void main(String[] args) {
		List<String> list = new ArrayList<>(List.of("hello", "world"));
		Swap.swap(list, 0, 1);
		System.out.println(list);
	}
}


/*
 * 한정적 와잉ㄹ드카드를 사용해 api 유연성을 높여라 .
 *
 * 1. 메서드 선언에 타입 매개변수가 한번만 나오면 와일드 카드로 대체하라
 * -> 한정적 타입이라면 한정적 와일드카드로
 * -> 비한정적 타입이라면 비한정적 와일드 카드로 ,
 *
 * 주의점은 비한전적와일드 카드로 정의한 타입에는 null 을 제외한 아무것도 넣지 못함.
 * ? 하나는 그냥 불특정한 타입이라는 의미이다.
 *
 * Required type: capture of ?
 * Provided: capture of ?
 *
 * 라고 나옴 .
 *
 * 책에서 helper 성 메서드가 있긴 한데,
 * */

class Swap {

	public static <E> void swap(List<E> list, int i, int j) {
		list.set(i, list.set(j, list.get(i)));
	}

	public static void typeStrictedSwap(List<?> list, int i, int j) {
		//	list.set(i, list.set(j, list.get(i)));
	}
}