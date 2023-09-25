package composition;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/*
 * 위의 예시에서 hashSet 의 addAll() 은 CountingSet 의 add 를 더이상 호출하지 않는다.
 *
 * 로직의 분리가 일어났기 때문에 캡슐화가 깨지지 않는다 .
 *
 * 위와 같은 예시를 위임 이라고 부르고
 * 데코레이터 패턴, 이라고도 한다.
 *
 * 그리고 CountingSet2 는 Set 을 감싸고 있으므로 wrapper 클래스 라고도 한다.
 * */
public class CompositionSet {
	public static void main(String[] args) {
		CountingSet2<Integer> set = new CountingSet2( new HashSet());

		System.out.println("#1 set count : " + set.getCount());

		set.add(3);
		System.out.println("#2 set count : " + set.getCount());

		set.addAll(List.of(1, 2, 3, 4, 5, 6));
		System.out.println("#3 set count : " + set.getCount());
		System.out.println("ㄴ> success : 분리된 hash set 과  countingset ");

	}
}

class CountingSet2<E> extends ForwardingSet<E> {

	int count = 0;

	public int getCount() {
		return count;
	}

	public CountingSet2(Set<E> s) {
		super(s);
	}

	@Override
	public boolean add(E e) {
		count += 1;
		return super.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		count += c.size();
		return super.addAll(c);
	}

}

class ForwardingSet<E> implements Set<E> {
	private final Set<E> s;

	public Set<E> getS() {
		return s;
	}

	public ForwardingSet(Set<E> s) {
		this.s = s;
	}

	@Override
	public int size() {
		return s.size();
	}

	@Override
	public boolean add(E e) {
		return s.add(e);
	}
	@Override
	public boolean addAll(Collection<? extends E> c) {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return s.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return s.contains(o);
	}

	@Override
	public Iterator<E> iterator() {
		return s.iterator();
	}

	@Override
	public Object[] toArray() {
		return s.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return s.toArray(a);
	}


	@Override
	public boolean remove(Object o) {
		return s.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return s.containsAll(c);
	}



	@Override
	public boolean retainAll(Collection<?> c) {
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return false;
	}

	@Override
	public void clear() {

	}

}