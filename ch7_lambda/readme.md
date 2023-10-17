## Lambda & Stream

### item 42 : 익명 클래스보단 람다를 사용해라 .

```java

// 별로 안좋은 방법 
public static void oldPassion(List<Integer> list) {
		Collections.sort(list, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		});
	}
```

함수가 하나 있는 인터페이스에 대한 구현은 위처럼 익명 클래스를 만들어서 ( 인터페이스의 함수 구현체만 채운 일회용 객체 ).  구현하는건 자바 1.1 에서 부터 지원된 너무 오래된 방법이다..

지금은 더 짧고 가독성이 좋은 다음의 방법이 있다.

자바 8에 와서는 추상메서드 하나짜리 인터페이스는 람다식을 사용해 만들 수 있다.
따라서 아래처럼 작성이 가능함


```java
	public static void currentPassion(List<Integer> list) {
		Collections.sort(list, (e1, e2) -> e1.compareTo(e2));
	}

    // Collections.sort(list3, Integer::compareTo); // 혹은 이것
```

위의 e1,e2 의 타입은 컴파일러가 추론할 수 있다. 
인자의 타입은 함수의 정의로서 함수 1개짜리 인터페이스가 들어가는 위치고,
그 함수의 시그니쳐는 함수의 인터페이스에 써져 있기 때문이다.


물론 아래처럼 타입을 명시 할 수도 있다 .


```java
public static void currentPassion2(List<Integer> list) {
		Collections.sort(list, (Integer e1, Integer e2) -> e1.compareTo(e2));
	}
```

그러나 "하위타입만 인자로 받는 비교" 정도의 강제를 둘 것이 아니라면 , 굳이 이렇게 작성할 필요는 없다 .


#### 주의점 2

제너릭이 로 타입이라면 람다식의 타입 추론의 대상이 되지 못할 수도 있다.  

```java
	public static void main(String[] args) {
		// 이건 되지만
		List<String> list = new ArrayList<>(List.of("Hello", " ", "world"));
		Collections.sort(list, (e1,e2) -> e1.compareTo(e2));

		//이건 안된다
		List list2 = new ArrayList<>(List.of("Hello", " ", "world"));
		Collections.sort(list2, (e1,e2) -> e1.compareTo(e2));
	}

```


#### 메서드 지원

아래와 같은 방법을 매서드 지원 이라고 한다.

```java
Collections.sort(list3, Integer::compareTo); 
```

람다에 비해 가지는 장점은 아래와 같다.

1. 변수를 표시할 필요가 없으니, 람다보다 간결하다.
2. 메소드이름이 눈에 보여서 표현력이 좋다.



#### 람다의 단점

1. 이름이 없고 문서화도 못한다.
2.  코드 자체로 설명이 명확하지 않거나 장황해지면 구현체를 따로 만드는것이 권장 된다 .
3.  람다는 this 가 없기 때문에 this 를 쓰려면 추상 클래스나 클래스의 몸이 필요하다 .


### item43 : 람다보다는 메서드 참조를 사용해라.


람다가 익명 클래스보다 나은 점 중 하나는 간결함이다.
메서드 참조는 람다보다 더 큰 간결함을 제공한다 .

즉 
```java

map.merge(key ,1, (count,incr) -> count+ incr);

```
보다는


```java
service.execute(SomeClass::action);
```

이게 더 보기 좋다는 뜻이다.

하지만 반례도 있는데, 만약 호출하는 함수가 this 에 속해 있다면 

```java
service.execute(()->action());
```

이렇게 더 보기좋게도 바꿀 수 있다. 

이 챕터에서 말하고 싶은 바는  
"메소드 참조를 활용할 수 있으면 활용하자" 이다 . 


### item44 : 표준 함수형 인터페이스를 사용하라.

람다식을 필드, 또는 어떤 인자로 받을 때, 매번 함수 시그니쳐를 가지는 인터페이스를 정의할 필요는 없다.

```java
int foo();

void bar();

<T> T zoo(T t)

<T,R> T roo (R r);

```

등 공통적인 표현식을 자바 자체에서 제공한다.



인터페이스
인터페이스 	함수 시그니처	예시

UnaryOperation<T> 	 T apply(T t) </t>  	String::toLowerCase

BinaryOperation<T>  </t> 	T apply(T t1, T T2) 	BigInteger::add

Function<T,R>	R  apply(T t)	Arrays::asList

Predicate<T> 	boolean test(T t)	Collection::isEmpty

Supplier<T>	T get()	Instant::now

Consumer<T>	void accept(T t) 	System.out::println


이 외에도 타입이 명시된 다른 인터페이스들도 존재한다.


</br>

이 인터페이스를 사용할때의 조언은 다음과 같다.

1. 우선 필요한 람다식을 받을때 기본으로 정의된 인터페이스가 있다면 그 타입을 써라
2. 함수형 인터페이스의 타입은 박싱된 타입을 쓰는것을 지양한다.
    1. 성능차이가 꾀 난다.
3. 만약 함수 시그니쳐를 제공하는 인터페이스를 람다용으로 만들면 이 사항을 알리는 어노테이션이 있다.
    1. @FunctionalInterface 이거 꼭 쓰자 .




### item45 : 스트림은 주의 해서 사용하라 .

#### 스트림 용어 정리 

1. stream : 유한 혹은 무한한 어떤 시퀀스
2. 스트림 파이프라인 : 원소를 이용한 연산 .

#### 연산의 종류 

1. 종간 연산 :끝날때 한번 ( collect )
2. 중간 연산 : 각원소 (+ 이전 원소) 에 대한 연산 ( filter )

중간연산의 특징은 이것도 stream 을 반환해야 한다.

왜냐하면 stream api 를 제작할 때 fulent 한 사용성을 보장하기로 약속이 되어 있기 대문 .
(빌더처럼 체이닝 )

#### 스트림 적용 규칙

스트림이 항상 가독성을 좋게 만들지도, 항상 안전하지도 않다 .
아래의 규칙에 맞게 쓰는 것이 권장된다.

1. 너무 긴 로직을 스트림에 녹여내려하지 말자. ( 예시 아래 )
2. 매개 변수의 이름을 잘 지어라 , 타입이 없기 때문에 더 중요 함.
3. char 는 'a' ->96 다 이렇게 integer 로 바뀐다. char 연산은 지양해라.
4. 분기처리, 예외처리는 스트림에서 불가능 하다. 함수를 별도로 만들어야 한다 .

</br>

 그러면 적용하면 좋은 경우는

</br>

1. 시퀀스의 필터링
2. 시퀀스의 일관적인 변환
3. 하나의 연산자만 적용하는 경우
4. 컬렉션으로 변환
5. 시퀀스 내 조건에 맞는 원소 찾기



### item46 : 스트림에서는 부작용 없는 함수를 사용하라 .

스트림의 패러다임은 "일련의 변환" 이라고 한다

( 책의 표현, 마땅히 좋은 번역을 못찾겠다 )

이전의 결과와 지금 상태만 연산에 영향을 줘야한다는 뜻이다.

아래와 같은 조건을 순수 함수라고 하는데, 스트림을 순수함수로 유지하는 것이 중요하다고 하다.

1. 입력만이 결과에 영향을 줌
2. 다른 가변상태를 참조하지도, 변경하지도 않음 .
   
가령 , 아래의 코드는 스트림의 패러다임을 이해하지 못한 것이라 한다.


```java
	public static void main(String[] args) {
		Map<String, Long> freq = new HashMap<>();

		list.stream().forEach(
			word -> freq.merge(word.toLowerCase(Locale.ROOT), 1L, Long::sum)
		);

	}
```


순수함수하게 바꾼 표현은 아래와 같다. 


```java
	Map<String, Long> freq1 = list.stream().collect(
			groupingBy(String::toLowerCase, counting())
		);

```

역할은 같으나, 순수한 함수를 표방한다.
외부 변수에 접근하지 않고 불변 콜렉션을 반환함으로서 좁은 변경범위로 종료된다 .

여기서 팁은 forEach 는 되도록 결과를 출력할때 쓰자는 것이다.

#### collect 

reduce 라는 동작으로 설명되기도 한다.

reduce -> 줄인다 -> 취합한다 -> 결과를 collection 으로 만든다 

정도의 흐름으로 이해하고 있다. 

collect 를 사용한 예시는 아래와 샅다 . 


1. enum 의 symbol 과 객체를 map 으로 
```java
	public static void example1() {
		Map<String, MyEnum> map =
			Arrays.stream(MyEnum.values())
				.collect(toMap(MyEnum::toString, e -> e));

		System.out.println("MAP : " + map.toString());
	}


```


2. 인수를 3개 받는 toMap 
```java
	public static void example2() {
		Map<String, MyEnum2> map = Arrays.stream(MyEnum2.values())
			.collect(toMap(MyEnum2::toString, e -> e, (oldVal, newVal) -> newVal));
	}
```

인자 3개는 아래 같은 의미이다 .


```java
<R> Collector<T, ?, Map<K, V>> toMap(
    Function<? super T, ? extends K> keyMapper,
    Function<? super T, ? extends V> valueMapper,
    BinaryOperator<V> mergeFunction
)
```

BinaryOperator<V> mergeFunction

 -> 키 충돌이 일어났을 시, 키값을 결정하는 함수 .
 

### item 47 : 리턴 타입으로는 스트림 보다는 컬렉션이 낫다 .

1. stream 은 for each 를 쓸 수 없다.
2. 쓰고 싶다면 컬렉션으로 바꾸거나 어댑터 기능을 만들자.
3. 단순 형변환을 통해 for each 를 흉내내는건  가독성 떨어지고 나쁨 .


### item 48 :  스트림 병렬화는 조심해서 써라 .

-> 이부분은 별도 작성이 필요 . 