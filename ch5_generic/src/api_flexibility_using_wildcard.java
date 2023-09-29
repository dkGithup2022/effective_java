import java.util.Collection;
import java.util.EmptyStackException;
import java.util.List;

public class api_flexibility_using_wildcard {

	public static void main(String[] args) {
		Stack<Number> numberStack = new Stack<>(List.of());
		Integer a = 1;
		List<Integer> ints = List.of(1, 2, 3, 4, 5);

		numberStack.add(a);
		numberStack.addAll(ints);

		while (numberStack.size != 0) {
			System.out.println("E : " + numberStack.pop());
		}

	}
}

class Stack<E> {
	E[] array;
	int size;

	static final int DEFAULT_SIZE = 32;

	@SuppressWarnings("checked")
	public Stack(Collection<? extends E> collection) {
		array = (E[])new Object[DEFAULT_SIZE >= collection.size() ? DEFAULT_SIZE : collection.size()];
		size = 0;

		this.addAll(collection);
	}

	public void add(E e) {
		array[size++] = e;
	}

	public void addAll(Collection<? extends E> collection) {
		for (E e : collection)
			this.add(e);
	}

	public E pop() {
		if (size < 0)
			throw new EmptyStackException();

		E e = this.array[size];
		this.array[size--] = null;

		return e;
	}

}