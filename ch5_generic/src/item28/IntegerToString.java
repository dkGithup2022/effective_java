package item28;

import java.util.ArrayList;
import java.util.List;

public class IntegerToString {
	public static void main(String[] args) {
		Object[] anything = new String[10];  //1
		anything[0] = 1; // 컴파일러가 못잡는 버그 //2
		// (1), (2) 를 각각 보면 문법적 하자가 없음 . 런타임에 exception 결정됨.
		// 배열을 쓰는 것을 지양해야 하는 경우임.
		// 배열을 쓰면 성능에선 이득을 보지만, 이런 제너릭을 적용하는 것이 컴파일 타임에 에러가 안잡힐 수 있음
		//


//		List<String> names = new ArrayList<>();

//		List<Object> objects = names;

		//List<String> 과 List<Object> 는 다름.
		// 상위, 하위 개념 아님 ( 어레이는 상하위 개념임 )


		/*
		* 제너릭은 런타임중에 없는 정보임 .
		* 하위 버전과 호완 때문에 .
		*
		* 컴파일을 할때,
		* */
	}
}
