package dont_use_raw_type;

import java.util.List;
import java.util.Set;

public class UnboundedWildcard {
	public static void main(String[] args) {

		Set s1 = Set.of(1, 2, 3, 4, 5);
		Set s2 = Set.of(2, 4, 7, 8);

		System.out.println(countCommon(s1, s2));
	}

	public void chage(final List<?> list, Object o) {
		//	list.add(o);
	}

	public static int countCommon(Set<?> set1, Set<?> set2) {

		int cnt = 0;
		for (Object o : set1)
			if (set2.contains(o))
				cnt++;
		return cnt;
	}

}
