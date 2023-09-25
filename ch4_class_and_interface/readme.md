## 클래스와 인터페이스 

추상화의 기본 단위인 클래스와 인터페이스를 만들 때 적용할 수 있는 규칙에 대해 다룹니다 .

### 클래스와 맴버의 접근 권한을 최소화 하라.

좋은 컴포넌트의 조건 중 하나는 "내부 구현 정보를 외부 컴포넌트로부터 얼마나 잘 숨겼느냐" 다.

잘 숨겨진 내부구조는, 클라이언트로 하여금 사용이 쉽게 하는 것 이욍에도 아래와 같은 장점이 있다.

1. 시스템 개발 속도 상승, 여러 컴포넌트를 병렬로 개발할 수 있다.

2. 관리 비용을 낮춘다. ( 다른 컴포넌트에 주는 영향이 적다 )   

3. 컴포넌트 파악에 도움이 된다.( 다른 컴포넌트에 주는 영향이 적다 )

4. 재사용성을 높인다 .

5. 큰 시스템 설계에 재작 난이도를 낮춘다 .


#### 기본 원칙

기본 원칙은 모든 클래스와 맴버의 접근성을 가능한 좁혀야 한다. 

- public 으로 공개한 모든 기능은 공개 api 라고 볼 수 있다 . 
- package-private 라면 패키지 내에서만 접근이 가능하다 .

이러한 구분이 중요한 이유는 , 외부에서 public 한 객체에 접근하고 있는 시스템의 경우, public field 와 메소드의 수정은 곧, 그 ㄱ메소드를 사용하고 있는 다른 영역으로의 수정을 동반하기 때문이다.

접근할 필요가 없다면 애초에 접근 자체가 안되게 하는 것이 best practice 라고 할 수 있다 .


### public 클래스에서는 public 필드 대신 접근 메서드를 정의 하라 ( getter, setter )


public 클래스는 가변 필드를 직접 노출 하면 안된다.
불변 필드라도 노출하는 것은 지양해야 한다.

다만, package-private class , private 중첩 클래스는 필드를 노출해도 사이드 이펙트가 상대적으로 적다 .


### 변경 가능성을 최소화하라.

가능하면 불변 클래스로 만들어야 한다. 불변 클래스는 설계, 구현이 쉽고, 오류가 생길 여지가 적다.

그 이외의 규칙은 아래와 같다.

1. 객체의 상태를 변경하는 메서드를 제공하지 않는다 (setter )
2. 클래스를 확장 할 수 없도록 한다 .
3. 모든 필드를 final 로 선언한다. 
   4. final 강제는 종종 thread safe 하다는 점에서 이득을 볼 수도 있다.
5. 모든 필드를 private 로 선언한다 .
6. 자신 외에는 가변 컴포넌트에 접근할 수 없도록 한다 . 

불변 객체의 단점은 아래와 같다.

값이 다르면 무조건 다른객체가 생성되어야 한다 .


위의 규칙을 요약하면 아래와 같다.

1. 가능하면 클래스는 불변이여야한다.
2. 최대한 많은 부분이 불변이여야 한다.
3. 접근 제어자는 private final 일 때 가장 좋다 .
4. 생성자는 불변의 형식을 가진 완전한 클래스를 

불변객체의 예시 
```java
private static class Complex {
		private final double re;
		private final double im;

		public Complex(double re, double im) {
			this.re = re;
			this.im = im;
		}

		public Complex plus(Complex c) {
			return new Complex(re + c.re, im + c.im);
		}

		public Complex minus(Complex c) {
			return new Complex(re - c.re, im - c.im);
		}
	}

```


### 클래스 상속보다는 컴포지션을 사용하라 

(인터페이스 상속을 말하는 것이 아니다!)

상속 보다는 컴포지션을 활용하라 .

함수의 오버라이딩은 자칫하면 클래스의 캡슐화를 파괴할 수 있다. 아래의 예시에서는 
hashSet 의 addAll 에 대한 오버라이드가 어떻게 망가지는지를 볼 수 있다 .

아래의 예시에서는 hashSet 의 addAll 의 내부에서 add() 를 호출하기 때문에 오버라이드 된 add 가 호출된다 . 따라서 숫자가 두번 올라가서 잘못된 결과가 나온다 .


```java
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
```


아의 예시에서 hashSet 의 addAll() 은 CountingSet 의 add 를 더이상 호출하지 않는다.

로직의 분리가 일어났기 때문에 캡슐화가 깨지지 않는다 .

위와 같은 예시를 위임 이라고 부르고 데코레이터 패턴, 이라고도 한다.

그리고 CountingSet2 는 Set 을 감싸고 있으므로 wrapper 클래스 라고도 한다.

```java
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
   ....

```

상속은 캡슐화를 해칠 가능성이 있다. 
상속은 완전한 is-a 관계에서만 써야 한다.

is-a 관계에서도 안심하면 안되는게, 상속을 위해 설계된 클래스에서만 위와 같은 문제가 생기지 않는다.

만약 상속으로 인한 캡슐화 파괴가 걱정되면 컴포지션과 딜리게이션을 사용하자 . 


### 상속을 고려해 설계하고 문서를 남겨라 . 그게 아니라면 상속을 금해라 .


1. 상속용 클래스는 재정의 할 수 있는 메서드와 사용법에 대한 기록을 남겨야 한다 .

2. 내부적으로 함수를 호출하는 경우, 호출하는 메서드의 순서와 영향범위에 대해 기록이 필요하다 . 

3. 상속용 클래스는 배포 전 하위 클래스ㅇ를 만들어 검증래야 한다.


4. 상속용 클래스의 생성자는 재정의 가능 메서드를 호출해서는 안된다 .

안되는 이유는 아래와 같다 .

```java

public class no_override_method_on_constructor {
	public static void main(String[] args) {
		Sub sub = new Sub();
		sub.overrideMe();
		/*
		null
		2023-09-25T11:57:23.330612Z

		순서는 아래와 같다 .
		1. super 생성자 호출
		2. sub 생성자 호출
			-> Instant 객체 생성
		->따라서 super 생성자에선 overrideMe() 가 null 을 출력. 
		*/

	}
}

class Super {
	public Super() {
		overrideMe();
	}

	public void overrideMe() {
	}
}

class Sub extends Super {
	private final Instant instant;

	Sub() {
		this.instant = Instant.now();
	}

	@Override
	public void overrideMe() {
		System.out.println(instant);
	}
}


```


### 추상 클래스보다 인터페이스를 우선하라
### 인터페이스는 구현하는 쪽을 생각해 설계하라
### 인터페이스는 타입을 정의하는 용도로만 사용 하라.



### 태그달린 클래스보다는 클래스 계층구조를 활용하라 .

-> 객체의 타입을 필드로서 나타내고 로직에 switch 를 둬서 컨트롤 하는 것은 클래스 크기를 쓸데 없이 크게 만들고, 오류의 가능성을 높인다. 

### 맴버클래스는 되도록 static 으로 만들라 .