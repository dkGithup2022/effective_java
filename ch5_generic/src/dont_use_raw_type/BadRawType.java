package dont_use_raw_type;

import java.util.ArrayList;
import java.util.List;

public class BadRawType {
	public static void main(String[] args) {
		//rawGeneric();
		unsafeCase();
		//safeCase();
	}
/*
컴파일 에러를 발생 시키는 type safe 한 예시 .
	public static void safeCase(){
		List<String> stringList = new ArrayList<>();
		safeAdd(stringList, "aa");
		safeAdd(stringList, 3L);
		stringList.forEach(
			e -> {
				System.out.println("this is " + e);
			}
		);
	}

	public static void safeAdd(List<Object> list, Object o){
		list.add(o);
	}

 */
	public static void unsafeCase() {
		List<String> stringList = new ArrayList<>();
		unsafeAdd(stringList, "aa");
		unsafeAdd(stringList, 3L);
		stringList.forEach(
			e -> {
				System.out.println("this is " + e);
			}
		);

	}

	public static void unsafeAdd(List list, Object o) {
		list.add(o);
	}

	public static void rawGeneric() {
		RawGeneric e = new RawGeneric<>();
		e.e = "aa";

		System.out.println("#1 e.e :" + e.e);

		e.e = 3;
		System.out.println("#2 e.e :" + e.e);

		e.e = 3L;
		System.out.println("#3 e.e :" + e.e);
	}

}

class RawGeneric<E> {
	E e;
}
