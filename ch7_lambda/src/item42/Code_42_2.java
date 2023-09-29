package item42;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Code_42_2 {
	public static void main(String[] args) {
		// 이건 되지만
		List<String> list = new ArrayList<>(List.of("Hello", " ", "world"));
		Collections.sort(list, (e1,e2) -> e1.compareTo(e2));

		//이건 안된다
		//List list2 = new ArrayList<>(List.of("Hello", " ", "world"));
		//Collections.sort(list2, (e1,e2) -> e1.compareTo(e2));
	}


}
