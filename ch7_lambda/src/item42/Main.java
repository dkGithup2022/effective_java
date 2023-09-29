package item42;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {
	public static void main(String[] args) {

		List<Integer> list1 = new ArrayList<>(
			List.of(1, 2, 3, 4, 5, 1, 2, 5, 2, 5)
		);

		List<Integer> list2 = new ArrayList<>(list1);
		List<Integer> list3 = new ArrayList<>(list1);

		oldPassion(list1);
		System.out.println(list1);

		currentPassion(list2);
		System.out.println(list2);

		// 3번 방법
		Collections.sort(list3, Integer::compareTo);
		System.out.println(list3);

	}

	public static void oldPassion(List<Integer> list) {

		Collections.sort(list, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		});

	}

	public static void currentPassion(List<Integer> list) {
		// Collections.sort(list3, Integer::compareTo); // 혹은 이것
		Collections.sort(list, (e1, e2) -> e1.compareTo(e2));
	}

	public static void currentPassion2(List<Integer> list) {
		Collections.sort(list, (Integer e1, Integer e2) -> e1.compareTo(e2));
	}


}
