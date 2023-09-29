## 열거 타입과 애너테이션


[상수 대신 열거형을 써라 ]

- 열거형 이란 ? : 정해진 수의 상수값을 정의한 다음 그 외의 값은 허용하지 않는것 .
- 그 외에는 ? : 각 열거 상수 당 정해진 필드를 선언할 수 있고, 열거 상수마다 각각 함수를 정의할 수도 있다.
- 아래는 예시이다 .



- 자바 열거 타임의 특징
    -   열거 타입 자체는 클래스
    - 각 상수 하나당 자신의 인스턴스를 만들어 public static final 필드로 공개됨.
    - 싱글턴 인스턴스로 통제됨 .


-

책에 있는 케이스 

1. 생성자를 가지는 열거 타입
  

열거형 생성자와 필드에 대해,

1. 열거형 생성자의 호출 시점
     -> 열거형 상수는 싱글 인스턴스이기 때문에 로드됨과 동시에 초기화 된다.


  2. 타입의 갯수와 파라미터
     -> 위처럼 파라미터는 3개, 선언 시 인자는 2개여도 상관 없다. 생성자에서 값을 생성해 줄수도 있음
     

```java

public enum Planet {

	MERCURY(3.302e+23, 3.439e6),
	VENUS(4.86e+24, 6.05e6),
	EARTH(5.95e+24, 6.378e6),
	;

	private final double mass;
	private final double radius;
	private final double surfaceGravity;

	

	Planet(double mass, double radius) {
		this.mass = mass;
		this.radius = radius;
		this.surfaceGravity = G * mass / (radius * radius);
	}

	private static final double G = 6.67E-11;

	public double surfaceWeight(double mass) {
		return mass * surfaceGravity;
	}
}
```


3. 분기처리를 위한 열거 타입 

아래 코드처럼 호출이 가능하다 .

주의해야 할 점은 상수의 각 스코프 내에 정의된 함수의 시그니쳐가 enum 에 정의되어 있어야 한다는 것이다.

가령 아래의 함수에서 

public abstract double apply(double a, double b);

가 없다면 아래 함수는 실행되지 않는디.

```java

public enum Operation {
	PLUS("+") {
		public double apply(double x, double y) {
			return x + y;
		}
	},
	MINUS("-") {
		public double apply(double x, double y) {
			return x - y;
		}
	},
	TIMES("*") {
		public double apply(double x, double y) {
			return x * y;
		}
	},
	DIVIDE("/") {
		public double apply(double x, double y) {
			return x / y;
		}
	};

	public abstract double apply(double x, double y);

	private final String symbol;

	Operation(String symbol) {
		this.symbol = symbol;
	}

}

```


4.  strategy pattern 

위의 예시로는 부족한 경우도 있다.

열거타입의 상수 자체이외에  속성별로 동작을 달리 해야 하는 경우도 있기 때문이다. 


이런 경우 switch 문에 로직을 작성하는 것보다 
분기가 되는 enum 을 한번 더 만들어주는 것이 좋다 .


```java
public enum PayrollDay {
	MONDAY, TUESDAY, WEDNESDAY(PayType.EARN_MORE);

	private final PayType payType;

	PayrollDay(PayType payType) {
		this.payType = payType;
	}

	PayrollDay() {
		this.payType = PayType.NORMAL;
	}

	enum PayType {
		EARN_MORE {
			public int bonus(int x) {
				return x * 2;
			}
		},
		NORMAL {
			public int bonus(int x) {
				return x;
			}
		};

		public abstract int bonus(int x);
	}

	public int earn(int hour, int pay) {
		return hour * payType.bonus(pay);
	}
}


```


4. fromString 의 구현 

위 예시의 from String 예시는 아래처럼 만들 수 있다.

물론 String 필드도 추가되어야 한다.


```java
	private static final Map<String, PayrollDay> stringToEnum
		= Stream.of(values()).collect(toMap(Object::toString, e -> e));

	public static Optional<PayrollDay> fromString(String symbol) {
		return Optional.ofNullable(stringToEnum.get(symbol));
	}


```


### ordinal 메서드 대신 인스턴스 필드를 이용하라 
### ordinal 인덱스 대신 EnumMap 을 사용하라.

두개가 비슷해서 같이 정리함.

enum 에 숫자를 붙이는 ordinal 이라는 필드값과 getter 를 제공하지만.

이 필드를 이용한 코드를 작성하는 것은 관리차원에서 좋지 않음 .

아래와 같이 enum 필드의 관리를 하는 것을 추천한다 .


ordinal 을 이용한 함수는 이넘상수 순서만 바뀌거나 추가되어도 에러가 날 것이고,
컴파일 타임에 잡아주지 않을 가능성이 높다.

ordinal 사용을 지양하고, 객체를 식별하는 로직을 별도로 만들거나 map 으로 관리하는 것이 추천된다 . 

```java

enum Phase {
	SOLID, LIQUID, GAS;

	public enum Transition {
		MELT(SOLID, LIQUID),
		FREEZE(LIQUID, SOLID),
		BOIL(LIQUID, GAS),
		CONDENSE(GAS, LIQUID),
		SUBLIME(SOLID, GAS),
		DEPOSIT(GAS, SOLID);

		private final Phase from;
		private final Phase to;
		Transition(Phase from, Phase to) {
			this.from = from;
			this.to = to;
		}
		public static Map<Phase, Map<Phase, Transition>> transitionMap
			= Stream.of(
			values()).collect(
			groupingBy(t -> t.from,
				toMap(t -> t.to, t -> t, (x, y) -> y, () -> new EnumMap<>(Phase.class))
			)
		);
		public static Transition get(Phase from, Phase to) {
			return transitionMap.get(from).get(to);
		}
	}
}

```

### 확장이 필요한 열거형이 있다면 인터페이스를 상속 받아라.

열거형은 클래스를 상속받거나, 이넘을 상속할 수 없다.

아래는 인터페이스를 상속하는 예시이다. 


```java
public interface Operation {
	double apply(double x, double y);
}
```

```java
public enum BasicOperation implements Operation {
	PLUS("+") {
		@Override
		public double apply(double x, double y) {
			return x + y;
		}
	},
	MINUS("-") {
		@Override
		public double apply(double x, double y) {
			return x - y;
		}
	};

	private final String symbol;

	BasicOperation(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public abstract double apply(double x, double y);
}
```


```java

public enum ExtendedOperation implements Operation {
	MULTI("*") {
		@Override
		public double apply(double x, double y) {
			return x * y;
		}
	}, DIVIDE("/") {
		@Override
		public double apply(double x, double y) {
			return x / y;
		}
	};

	private final String symbol;

	ExtendedOperation(String symbol) {
		this.symbol = symbol;
	}

	;

	@Override
	public abstract double apply(double x, double y);
}

```


### 명명 패턴보다는 어노테이션을 사용하자.

명명 패턴이란 클래스, 패키지의 이름을 규칙으로 무언가를 결정하는 것이다.

가령 junit 은 Test, Tests 로 종료되는 이름을 가진 클래스, 혹은 패키지를 대상으로만 테스트를 수행한다.

이런 컨트롤하기 힘든 요소를 통해 코드를 조작하려 하지 말고, 별도의 어노테이션을 추가하는 것이 권장된다.

아래는 간단한 어노테이션을 만들고 , 이를 리플랙션을 통해 적용 하는 예시이다.


- 어노테이션 선언 

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Test39 {
}

```

- 테스트 대상 
```java
class Track {
	@Test39
	public static void m1() {
	}

	;

	@Test39
	public static void m2() {
	}

	;

	@Test39
	public static void m3() {
		throw  new RuntimeException("FAIL ONE");
	}

	;
}
```

- 리플랙션을 통해 어노테이션을 확인하기 .
```java
	public static void main(String... args) throws
		ClassNotFoundException,
		InvocationTargetException,
		IllegalAccessException {
		int tests = 0;
		int passed = 0;
		Class<?> testClass = Class.forName("code_39_.Track");
		for (Method m : testClass.getDeclaredMethods()) {

			if (!m.isAnnotationPresent(Test39.class))
				continue;

			System.out.println("detected method : " + m.getName());
			tests++;
			try {
				m.invoke(null);
				passed++;
			} catch (InvocationTargetException exception) {
				Throwable exc = exception.getCause();
				System.out.println(m + " 실패 : " + exc);
			} catch (Exception e) {
				System.out.println("잘못 사용한 @Test39" + m);
			}
		}
	}
```

- 수행 결과 
```text
detected method : m2
detected method : m1
detected method : m3
public static void code_39_.Track.m3() 실패 : java.lang.RuntimeException: FAIL ONE
```


### item 40~ 41

#### item40 : 어노테이션을 일관되게 사용해라

-> Override 예시 

함수의 재정의가 아니라, 시그니쳐에서 실수만 있어도 오버 로딩이 이 되버린다.

실수의 여기가 있으므로, 상위 클래스를 재정의 하는 모든 메소드에 오버라이드를 걸자.

#### item 41: 마커인터페이스 vs 어노테이션

타입에 대한 제한을 두고 싶다면 어노테이션 보다 마커 인터페이스를 사용하라. 