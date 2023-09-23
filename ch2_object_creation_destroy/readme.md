
## 2장 : 객체의 생성과 파괴

</br>

### 1. 객체 생성에 팩토리 메소드 사용을 고려하라.

</br>


#### 장점 

1. 어떤 객체의 생성인지 네이밍으로 명시 가능.

2. 캐싱된 객체를 리턴 가능. 
   - 정규식의  Compile 함수가 비슷한 역할을 한다 

3. 반환 타입의 하위 객체를 선택해서 리턴 할 수 있다. 
   - List.of , Arrays.asList  등등 ... 
   - 조금 더 유연해지고 익히기 쉬워진다 .

4. 입력 매개변수에 따라 묵시적 ( 생성자 로직으로 ) 으로 다른 객체를 생성 할 수 있다 . 

5. 팩토리를 작성하는 시점에는 반환할 구현체가 존재하지 않아도 된다. 

#### 단점


1. 상속을 하려면 public, protected 생성자가 필요하니, private 으로 생성을 막는 행위가 하위 클래스가 있다면 하기 힘들다 .


2. 프로그래머가 읽어야 하는 로직이 많아진다 . 
   - 그래서 잘 알려진 네이밍을 써야 한다. (from , of, getInstance ... )


</br>


### 2. 매개변수가 많다면 빌더 패턴을 고려하라.

</br>

#### 장점

1. 생성자 호출 부분이 상대적으로 깔끔해진다 . 

2. 생성자 작성을 유연하게 가능 . 

3. 각 필드에 대한 함수를 생성로직에 둬서 일관성을 유지할 수도 있다.

4. 계층적으로 설계된 클래스와 잘 어울린다. 


```java

public abstract class Pizza{
  public enum Topping {HAM, MUSHROOM, ONION, PEPPER, SAUSAGE };
  final Set<Topping> toppings ;

  abstract static class Builder<T extends Builder<T>> {
      EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);
      public T addTopping(Topping topping){
         toppings.add(Objects.requireNonNull(topping));
         return self();
      }

   abstract Pizza build();
   protected abstract T self();
  }

   Pizza(Builder<?> builder){
      toppings = builder.toppings.clone();
   }
}

```

</br>

### 싱글턴 & 이넘

- private 생성자로 초기화를 방지
- static 한 맴버변수, 함수를 통해 실제 객체를 가져옴
- static scope 에서 생성하면 eager 로딩, 첫 함수 호출 시 가져오는 선택지도 있다 .
- enum 을 통해서도 싱글턴을 생성 할 수 있고 권장된다.
  - 이 경우, 직렬화나 리플랙션을 통해서도 제2의 인스턴스가 생기지 않는다 .
  - 대부분의 경우 이렇게 만드는 것이 권장된다 . 
  - 다만 enum 외의 클래스를 상속 할 수는 없다 . 
- 
```java
public enum EnumSingleton {
	INSTANCE;
	int value;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
```

```java
public class Main {
	public static void main(String[] args) {
		EnumSingleton singleton = EnumSingleton.INSTANCE;
		int first = singleton.getValue();
		
		System.out.println(first);
		singleton.setValue(singleton.getValue() +1 );

		EnumSingleton singleton2 = EnumSingleton.INSTANCE;
		System.out.println(singleton2.getValue());

	}
}
```


### 불필요한 객체 생성을 피해라 (정규식, String 예시 ) 


```java

# 1
String s1 = new String ("bikini");

# 2 
String s2 = "bikini";

```

(1) 은 생성 횟수만큼 heap 에 쌓이지만 ,(2) 는 하나만 쓴다.

비슷한 예시로 

```java

# 1
Boolean b = Boolean.valueOf(true);

# 2
Boolean b2 = new Boolean(true);
```

(1) 은 실제 객체를 만들지 않고 캐싱된 참조만 넘겨준다.

위와 같은 예시의 효용은 정규식에서 두드러 지는데, 어떤 문자열이 영어로 이루어져 있지 파악하는 두 함수 이다. 


### 다 쓴 객체를 참조 해제해라 & try-with-resources 를 사용해라 

- 여러 자원에 대한 try- with -resoureces 도 가능하다.
- AutoClosable 을 댕겨받아와야 한다.

```java

static void copy(String src, String dst){
	
	try(InputStream in = new FileInputStream;
        OutputStream out = new FileOutputStream(dst);
    )
    byte[] buf = new byte[Buffer_SIZE];
		int n ;
		while((n = in.read(buf)) >=0)
			out.write(buf,0,n);
    }

```
