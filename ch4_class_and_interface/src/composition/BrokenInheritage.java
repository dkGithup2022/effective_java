package composition;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class BrokenInheritage {

	public static void main(String[] args) {
		CountingSet<Integer> set = new CountingSet();

		System.out.println("#1 set count : " + set.getCount()); // 0

		set.add(3);
		System.out.println("#2 set count : " + set.getCount()); // 1

		set.addAll(List.of(1, 2, 3, 4, 5, 6));
		System.out.println("#3 set count : " + set.getCount()); // 13
		System.out.println("ㄴ> Fail : addAll 내부에서 add 를 호출하기 때문 , 깨진 캡슐화 ,.. 외부의 구현에 영향을 받는 부모 로직 ");

	}
}

class CountingSet<E> extends HashSet<E> {
	private int count = 0;

	public int getCount() {
		return count;
	}

	@Override
	public boolean add(E e) {
		count++;
		return super.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		count += c.size();
		return super.addAll(c);
	}
}