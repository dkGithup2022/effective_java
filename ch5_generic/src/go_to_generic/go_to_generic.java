package go_to_generic;

import java.util.Arrays;
import java.util.EmptyStackException;

/* item 29 : 이왕이면 제네릭 타입으로 만들라 */
public class go_to_generic {
	public static void main(String[] args) {

		/* 컴파일 에러
		ObjectStack os = new ObjectStack();
		os.push("a");
		os.push("b");
		os.push("c");
		while(os.isEmpty())
			os.pop().toUpperCase();
		 */

		/*
		* 제너릭에서 배열을 써야 할 때는 두가지 주의 해야 한다.
		* 1. 제너릭은 직접 배열로 못만든다 .
		* 아래처럼 만들어줘야함.
		*
		* 	public Stack () {
		elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];
	}

* 		* 2. 리턴 에도 타입 변환이 필요... 근본적으로는 Object[] 배열이 형변환 된것이기 때문 .
*
* public E pop() {
		if (size == 0)
			throw new EmptyStackException();
		@SuppressWarnings("unchecked") E result = (E) elements[--size];
		elements[size] = null;
		return result;
	}
		* */

		Stack<String> s = new Stack<String>();

		s.push("a");
		s.push("b");
		while (s.isEmpty())
			System.out.println(s.pop().toUpperCase());


		/*
		*  NumStack -> 한정정 타입 매개변수
		*
		* Stack<E extends Number > 는 반환하면 Number 의 배열로 바뀜 .
		* 따라서
		* */


	}
}

class ObjectStack {
	private Object[] elements;
	private int size = 0;
	private static final int DEFAULT_INITIAL_CAPACITY = 16;

	public ObjectStack() {
		elements = new Object[DEFAULT_INITIAL_CAPACITY];
	}

	public void push(Object e) {
		ensureCapacity();
		elements[size++] = e;
	}

	public Object pop() {
		if (size == 0)
			throw new EmptyStackException();
		Object result = elements[--size];
		elements[size] = null;
		return result;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	private void ensureCapacity() {
		if (elements.length == size)
			elements = Arrays.copyOf(elements, 2 * size + 1);
	}
}

class Stack<E> {
	private E[] elements;

	private int size = 0;
	private static final int DEFAULT_INITIAL_CAPACITY = 16;

	@SuppressWarnings("unchecked")
	public Stack() {
		elements = (E[])new Object[DEFAULT_INITIAL_CAPACITY];
	}

	public void push(E e) {
		ensureCapacity();
		elements[size++] = e;
	}

	public E pop() {
		if (size == 0)
			throw new EmptyStackException();
		@SuppressWarnings("unchecked") E result = (E)elements[--size];
		elements[size] = null;
		return result;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	private void ensureCapacity() {
		if (elements.length == size)
			elements = Arrays.copyOf(elements, 2 * size + 1);
	}

}

class NumStack<E extends Number> {
	private E[] elements;
	// 바이트 코드를 보면 위의 정의가 Number 의 배열로 선언됨 .
	 // // declaration: elements extends E[]
	//private [Ljava/lang/Number; elements

	private int size = 0;
	private static final int DEFAULT_INITIAL_CAPACITY = 16;

	@SuppressWarnings("unchecked")
	public NumStack() {
		elements = (E[])new Object[DEFAULT_INITIAL_CAPACITY];
	}

	public void push(E e) {
		ensureCapacity();
		elements[size++] = e;
	}

	public E pop() {
		if (size == 0)
			throw new EmptyStackException();
		@SuppressWarnings("unchecked") E result = (E)elements[--size];
		elements[size] = null;
		return result;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	private void ensureCapacity() {
		if (elements.length == size)
			elements = Arrays.copyOf(elements, 2 * size + 1);
	}

}