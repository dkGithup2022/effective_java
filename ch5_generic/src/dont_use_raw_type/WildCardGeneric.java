package dont_use_raw_type;

import java.util.ArrayList;
import java.util.List;

public class WildCardGeneric {
	public static void main(String[] args) {
		wildcardCase();
	}

	public static void wildcardCase() {

		List<String> list = new ArrayList<>();
		wildcardAdd(list, "hello");
		wildcardAdd(list, "world");
		wildcardRead(list);

	}
	// 쓰기 요청시엔 자기  + 부모 파트만 ( 왜냐면 자식 필드를 모르니까)
	// 따라서 super
	public static void wildcardAdd(List<? super String> list, final String o) {
		list.add(o);
	}

	// 읽기는 하위도 가능하다고 함 ... 따라서 extends
	public static void wildcardRead(List<? extends Object> list) {
		list.stream().forEach(e -> System.out.println(e));
	}
}


