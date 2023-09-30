## 제너릭

### 로 타입은 사용하지 말라

#### 용어 정리

1. 제너릭 클래스 (인터페이스) : 타입 매개변수가 쓰인 클래스, 제너릭 타입이라고도 한다.
2. 타입 매개변수 : List<E> 에서 E
3. 로 타입 : 제너릭에서 타입 매개변수를 사용하지 않은 경우.

#### 로타입 

로타입은 아래같은 상황을 말한다 .

```text
List list = new ArrayList();
```

타입이 정의 되어있지 않은 제너릭타입 , 이것은 이후에 타입 관련한 에러를 컴파일 타임에 확인하지 않기 때문에 런타임에 
ClassCastException 을 반환하는 원인이 된다.

따라서 로타입 사용을 지양해야 한다.

로타입으로 인해 문제를 일으키는 예시는 아래에

```java
	public static void unsafeAdd(List list, Object o) {
	list.add(o);
	}
    ...
public static void unsafeCase() {
	List<String> stringList = new ArrayList<>();
	unsafeAdd(stringList, "aa");
	unsafeAdd(stringList, 3L);
	stringList.forEach(
	e -> {
	System.out.println("this is " + e);
	}
	);

	}

    ...
	Exception in thread "main" java.lang.ClassCastException: class java.lang.Long cannot be cast to class java.lang.String 
```

위의 예시에서 unsafeAdd 에서 로타입 리스트는 모든 타입을 원소로 받을 수는 있다.

하지만 이후 cast 에서 class cast exception 을 확인 할 수 있다 . 


#### 와일드 카드의 사용

로타입을 사용하지 말아야 하는 이유는 알았다.

타입을 강제하는 방법은 두가지가 있는데, E 에 명시적인 타입을 넣는 것 이외에, wildcard 를 사용하여 유연하게 타입을 정의할 수도 있다.

wild card 에는 세가지 사용법이 있다.

1. <?>, 비한정적 와일드 카드  : 타입을 모른다는 뜻, null 만 입력 가능 , 
2. 한정적 와일드카드 , <? extends String>  or  <? super String >: 타입에 강제를 두는 와일드 카드들 

비한정적 와일드 카드에 대해서만 언급하고 한정적 와일드 카드는 item 31에서 다룬다.

#### 비한정적 와일드 카드 
 
아래 코드는 컴파일 실패한다.

```java
public void chage(final List<?> list,  Object o ){
		list.add(o);
	}
```

? 라는 타입에 아무것도 붙어있지 않으면 타입을 모른다는 뜻이다.

(저자는 ? 라는 시그니쳐를 정말 잘 지었다고 한다 .)

? 에 걸리는 타입을 모르니 , Object 도 넣을 수 없다 .

이 특성은 불변객체와 정말 잘어울린다. 아래는 집합을 Union 하는 함수이다.

아래처럼 조회만 하는 것에는 제한이 없기에 , 객체의 상태를 변하지 않게 유지한다는 것을 강제한다 .

```java

public static int countCommon(Set<?> set1, Set<?> set2) {

	int cnt = 0;
	for (Object o : set1)
	if (set2.contains(o))
	cnt++;
	return cnt;
	}

```

### 제너릭과 생산성 

책에는 없고 백기선님 강의에 있는 내용이다.

제너릭이랑 친해지는 것을 추천하는 부분인데, 제너릭을 잘 쓰면 개발자 생산성이 꾀나 늘어난다는 것이다.

아래는 간단하지만 제너릭을 이용해 관리해야 하는 코드의 줄 수를 꾀나 줄인 예시이다 . 


- 제너릭 추상 클래스 

```java
public class GenericDao<E extends Entity> {
	private final Set<E> db = new HashSet();

	public void save(E u) {
		db.add(u);
	}

	public List<E> findAll() {
		return new ArrayList<E>(db);
	}
}

```

- 상속 

```java

public class GArticleDao extends GenericDao<Article> {
}


public class GUserDao extends GenericDao<User> {
}

```

- 사용 부분 

```java
	public static void main(String[] args) {

		GUserDao userDao = new GUserDao();
		User u1 = new User();
		User u2 = new User();
		userDao.save(u1);
		userDao.save(u2);

		GArticleDao articleDao = new GArticleDao();
		Article a1 = new Article();
		Article a2 = new Article();

		articleDao.save(a1);
		articleDao.save(a2);

		System.out.println(userDao.findAll());
		System.out.println(articleDao.findAll());

	}
```

비슷한 예시로 spring data jdbc 를 쓰는 예시에서 
sql 관련 공통부분을 하나로 모은 레포 예시가 있다 .

-> 수정 적용해서 예시로 올리기 (매모 ) 

https://github.com/dkGithup2022/spring_study_with_fc



### 비검사 경고를 제거하라 .

제너릭을 사용하면 컴파일러 경고를 되게 많이보게 되는데, 가능하면 전부 지워주도록 한다.

탕비 세이프 하지만 구조상 지울 수 없는 경우도 있다 .
이런 경우엔 @SuppresedWarning("checked")
를 통해 컴파일러 경고를 지워주도록 하자 .



### 배열보다는 리스트를 사용하라 

- 배열은 공변 / 리스트는 불공변 

배열은 Object[] 가 String[] 의 부모 클래스 역할 이지만 

List<Object> 와 List<String> 은 상위 타입도 하위 타입도 아니다 .

즉 코드로 보면 배열은 아래와 같은 버그를 컴파일 타임에 잡을 수 없다 .

```java
Object[] anything = new String[10];  //1
		anything[0] = 1;
```

반대로 컬렉션은 아래의 코드는 컴파일 조차 되지 않위다.

```java
	List<String> names = new ArrayList<>();

		List<Object> objects = names;

    ...

	java: incompatible types: java.util.List<java.lang.String> cannot be converted to java.util.List<java.lang.Object>
```

```java
```

- 바이트 코드로 보기 


위의 예시에서 E, List<E>, List<String> 같은 타입을 실체화 불가 타입이라 한다.

실체화 되지 않아서 런타임 중엔 컴파일 시점보다 타입 정보를 적게 가진다는 뜻이다.

아래는 인텔리 제이에서 shift*2 를 통해 
바이트 코드를 들여다 본 것이다 .

변수의 선언에는 타입이 없다가, get 함수에서 형변환만 됨을 확인할 수 있다 . 



```java

	List<String> names = new ArrayList<>();
		names.add("dk");
	String name = names.get(0);
		System.out.println(name);


... 바이트 코드

// access flags 0x9
public static main([Ljava/lang/String;)V
	L0
	LINENUMBER 8 L0
	NEW java/util/ArrayList -> 타입 정보가 애초에 없다.
	DUP
	INVOKESPECIAL java/util/ArrayList.<init> ()V
	ASTORE 1
	L1
	LINENUMBER 9 L1
	ALOAD 1
	LDC "dk"
	INVOKEINTERFACE java/util/List.add (Ljava/lang/Object;)Z (itf)
	POP
	L2
	LINENUMBER 10 L2
	ALOAD 1
	ICONST_0
	INVOKEINTERFACE java/util/List.get (I)Ljava/lang/Object; (itf)
	CHECKCAST java/lang/String -> 10 번 라인 , 형변환이 자동으로 된다 .
	ASTORE 2
	L3
```



### item 29 : 이왕이면 제너릭 타입으로 만들라 .
### item: 30 : 이왕이면 제너릭 메서드로 만들라.

아래의 E 타입 매개변수를 통해, 같은 타입을 가진 집합에 대한 연산임을 추론 할 수 있다 .

이렇게 타입을 유연하게 정의하는 것은 유지보수 단에서 도움이 된다 .

제너릭 메서드의 예시 
*  리턴 타입 전에 사용할 타입을 먼저 정의 한다.
* 스코프 내에서 E 는 모두 같은 타입이여야 한다.

```java
public static <E> Set<E> union(Set<E> s1, Set<E> s2) {
		Set<E> set = new HashSet<>(s1);
		set.addAll(s2);
		return set;
	}
```




#### 제너릭 싱글턴 팩토리 패턴

```java
class GenericSingletonFactory {
	private static UnaryOperator<Object> IDENTIFY_FN = (t) -> t;

	public static <T> UnaryOperator<T> identifyFunction() {
		return (UnaryOperator<T>)IDENTIFY_FN;
	}
}
```

위의 예시에서 UnaryOperator<Object>  는 함수에 대한 타입이다. 

위의 객체를 형변환 해줘서 (t)->t 의 관계를 다른 타입에 대해서도 사용 가능하게 하는 예시이다 . 

- 사용부분 
```java
UnaryOperator<String> identifyString = GenericSingletonFactory.identifyFunction();
		UnaryOperator<Integer> identifyInteger = GenericSingletonFactory.identifyFunction();

		Integer a = identifyInteger.apply(3);
		Integer b = identifyInteger.apply(4);
		String c = identifyString.apply("aa");
		System.out.println("a : " + a);
		System.out.println("c : " + c);

```


### item 31: 한정적 와일드카드를 사용해 API 유연성을 높여라 

제너릭 메서드의 이점을 가지면서 타입에 대한 강제도 추가할 수 있다 .

안좋은 예시는 아래와 같다. 컬렛션은 불공변이기 떄문에 아래의 코드는 컴파일 에러가 발생한다 .


```java

public void pushAll(Iterable<E> src){
	for(E e : src){
		push(e);
    }
}
    ...

Stack<Number> numStack = new Stack();
Iterable<Integer> ints = ....;
numStack.pushAll(ints);

```


pushAll 을 아래처럼 바꿔줌으로서 해결이 가능하다.


```java
public void pushAll(Iterable<? extends E> src){
	for( E  e : src)
		    push(e);
    }
```

제너릭 타입에 값을 추가, 변경 할때  그 하위 타입을 가져와도 상관은 없을 수 있다. 

Number 로 선언한 원소의 자리에 sub class 의 객체가 들어가도 된다는 보장이 있을때, 할 수 있는 전략이다.

(사실상 특별히 하위 타입을 막는 경우를 제외하곤 그냥 이게 best practice 인 것으로 보인다 .)


아래는 받아온 인자로 제너릭 타입의 값을 집어 넣는 예시이다.


```java
public void popAll(Collection< ? super E> dst){
	for( !isEmpty())
		dst.add(pop());
    }
```

dst 의 타입을 X 라고 하고 맴버 변수의 타입을 E 라 했을 때,

X 에 E 가 대입되려면 , E 가 X 의 하위 타입이여야 한다.

따라서 이런 시그니처는 말이 된다.

```java
? super E
```

위에 내용이 햇갈려서, peoducer -extends, consumer -super 라는 이름으로도 불리는것 같다.

(PECS)




### Item 32: 제네릭과 가변인수를 함께 쓸ㄸ때는 신중하라

제너릭으로 가변인수를 받는 best practice 는 가변인수를 쓰지 않고 컬랙션으로 받는 것이다.

제너릭으로 가변인수를 받으면 안되는 이유는, 가변 인수는 배열로 치환되기 때문이다.

제너릭에 대한 배열을 받았을 때, 생기는 문제는 제너릭 가변인수에서도 똑같이 발생한다.

```java
	private static void dangerous(List<String>... args) {
		List<Integer> intList = List.of(42);
		Object[] objects = args;
		objects[0] = intList;    // heap pollution
		String s = args[0].get(0); // class cast exception
	}
```

그냥 봐도 위험해보이는 위 함수는 컴파일 타임에 형변환에 대한 에러를 반환하지 않는다.

각 줄만 봐서는 문법적인 하자가 없는 코드이기 때문이다. 배열로 변환된 제너릭 타입은 위와 같은 문제를 발생시킨다.

애초에 List<String> ...args 대신  차라리 var args 를 받지 말고 List<List<? extends T>> 를 인자로 받는 것이 best practice 이다 .


### item33: 타입 안전 이종 컨테이너를 고려하라 

이 챕터에서 설명하는 container 는 주로 컬렉션이다.\

(데이터를 담을 수 있는 어떤 객체를 말한다 .)

보통 이런 컬렉션들은 아래처럼 하나의 타입 혹은 하위 타입만 담는다.


```java

List<String > list = new ArrayList();
// list.add(1) // compile 에러
```

만약,  어떤 타입 자체를 인자로 받아서 그 타입에 맞는 컨테이너를 만들고 싶다 라고 하면 아래처럼 작성 할 수 있다.


```java

public class Favorites2 {

	// 1번 예시와 다르게, 타입을 강제하려면 호출하는 메소드, 키값에 타입을 당제해야함.
	// Class 는 애초에 Generic 클래스이기 때문에, 아래처럼 타입 와일드카드를 붙일 수 있음 .
	private Map<Class<?>, Object> map = new HashMap();
	// 임의의 클래스 타입이 입력된다는 뜻.

	public <T> void put(Class<T> clazz, T value) {
		this.map.put(clazz, value);
	}

	@SuppressWarnings("checked")
	public <T> T get(Class<T> clazz) {
		return clazz.cast(this.map.get(clazz));
	}

	public static void main(String[] args) {
		Favorites2 favorites = new Favorites2();
		favorites.put(String.class, "Hello world");
		favorites.put(Integer.class, 3);

		String s = favorites.get(String.class);
		Integer i = favorites.get(Integer.class);
		System.out.println(s);
		System.out.println(i);

	}
}
```


-> 위 예시에서 "@SuppressWarnings("checked")" 이것은 사실 없어도 된다. cast 함수 자체적으로 타입에 대한 체크가 있기 때문에,

명시적으로 typesafe 함을 알려주지 않아도 되는 케이스 .