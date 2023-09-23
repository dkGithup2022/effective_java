
## 객체의 공통 메서드


Object 의 메소드 중 , equals., hashcode, toString, clone, finalize 는 재정의를 염두해 두고 설계된 것이라 작성에 규칙이 있음 .

하지만 컴파일 타임의 규칙으로 잡지 않아서, 개발자가 알아서 잘 만들어줘야하는 것들이라 볼 수 있음 .

만약 이 규칙을 지키지 않는 다면 HashMap, HashSet 등의 함수가 잘 동작하지 않거나 다른  library 들의 함수가 정상적으로 수행되지 않을수도 있다. 


### Equals 메소드

equals 메소드는 아래의 상황중에 포함되는게 있다면 재정의하지 않는 것이 권장된다.

equals 를 재정의 하는 것은 equals 를 사용하고 있는 다른 collection 이나 라이브러리도 영향을 받기 때문에 신중하게 써야한다는 것이 저자의 의견이다.

</br>


1. 각 인스턴스가 본질적으로 고유하다
   - ex) Thread 


2. 논리적 동치성을 검사할 일이 없다.
   - 실제로 같은 객체인지만 검사한다면 Object 의 equals 함수로 해결된다.


3. 상위 클래스의 equals 가 하위 클래스의 equals 에도 적용된다. 


4. 클래스가 private, package-private 이고 equals 를 호출할 일이 없다 .


-> 클라이언트가 사용하지 않을 것을 위험을 감수하고 만들 이유는 없다.

</br>

써야하는 곳은 

"논리적으로 같은지 아닌지 확인할 필요가 있는 경우" 이다.

ex) entity 의 id 로 비교.


위와 같은 경우에 재정의 할 때의 규칙은 아래와 같다.

1. 반사성 : null 이 아닌 모든 x 에 대해  x.equals(x) : true 이다.


2. 대칭성: null이 아닌 모든 x,y 에 대해 x.equals(y): true 이면 
y.equals(x): true 이다.

   
3. 추이성 : x.equals(y): true, y.equals(z) : true 라면 x.equals(z): true 이다.


4. 일관성 : 같은 객체에 대하 항상 같은 결과를 리턴

5. null- 아님 : null 이아닌 x 에 대해 false 를 리턴한다.


아래는 각 예시에 맞지 않는 것들이다. 

#### 대칭성 : 타입 불일치에 의한 대칭성 깨짐 


```java

public static void main(String[] args) {
		String s = "tmp";
		SensitiveString ss = new SensitiveString("tmp");
		
		System.out.println(s.equals(ss)); // false
		System.out.println(ss.equals(s)); // true
	}


	public static class SensitiveString{
		private final String s;

		public SensitiveString(String s) {
			this.s = Objects.requireNonNull(s);
		}

		@Override
		public boolean equals(Object obj) {
			if(obj instanceof SensitiveString)
				return ((String)s).equals(obj);
			if( obj instanceof String)
				return s.equals(obj);
			return super.equals(obj);
		}
	}
```

</br>

위의 예시에서
- System.out.println(s.equals(ss)) : false;

String 은 SensitiveString 과는 별개이므로 타입 불일치 false 를 리턴함.

- System.out.println(ss.equals(s)); // true

SensitiveString 은 재정의된 로직에 의해 true 를 리턴함.

따라서 잘못된 로직임 . 


</br>

#### 깨진 추이성 

```java

class Point {
	public int x, y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Point))
			return false;

		if (((Point)obj).x == x && ((Point)obj).y == y)
			return true;
		return false;
	}
}

class ColorPoint extends Point {

	public enum COLOR {RED, GREEN, BLUE}

	;

	private COLOR color;

	public ColorPoint(int x, int y, COLOR color) {
		super(x, y);
		this.color = color;
	}

	public boolean equals_broken_symmetry(Object obj) {
		if (!(obj instanceof ColorPoint))
			return false;

		return super.equals(obj) && ((ColorPoint)obj).color == color;
	}

	/*
	*
	* */
	public boolean equals_broken_transitivity(Object obj) {
		if (!(obj instanceof Point))
			return false;

		if (!(obj instanceof ColorPoint))
			return obj.equals(this);

		return super.equals(obj) && ((ColorPoint)obj).color == color;
	}

	/*
	 * 아래 같은 함수를 써버리면 아래의 상속받는 모든 함수들이 super.equals() 를 했을때 깨질수도 있다.
	 * final 인 경우를 제외하면 쓰면 안됨 .
	 * */
	public boolean equals_broken_liskov(Object obj) {
		if (obj == null || obj.getClass() != this.getClass())
			return false;

		return super.equals(obj) && ((ColorPoint)obj).color == this.color;
	}
}
```


```
public class GoodEqualsMethod {
	public static void main(String[] args) {
		String s = "tmp";
		SensitiveString ss = new SensitiveString("tmp");

		System.out.println(s.equals(ss)); // false
		System.out.println(ss.equals(s)); // true

		//

		Point p1 = new Point(1,1);
		ColorPoint p2 = new ColorPoint(1,1, ColorPoint.COLOR.BLUE);
		ColorPoint p3 = new ColorPoint(1,1, ColorPoint.COLOR.RED);

		System.out.println(" 깨진 대칭성 ");
		System.out.println(p1.equals(p2)); 	// false
		System.out.println(p2.equals_broken_symmetry(p1)); // true


		System.out.println(" 깨진 추이성 ");

		System.out.println(p2.equals_broken_transitivity(p1)); // T 
		System.out.println(p3.equals_broken_transitivity(p1)); // T 
		System.out.println(p3.equals_broken_transitivity(p2)); // F
	}
}


```



### hashcode : equals 정의 시 hashcode 정의 필수 .

아래는 Object 의 hashCode 함수에 대한 규약이다. 

1. equals 가 같다면 hashcode 도 같아야 한다.

2. hashcode 가 같다고 해서 equals 가 같을 필요는 없지만, 다른 값을 반환해야 해쉬 테이블이 성능을 보장 할 수 있다 .


아래는 hashcode 작성 요령이다.


1. int c 를 초기화 한다.
2. 핵심 필드 ( equals 에 사용되는 필드 ) 에 대해 아래와 같이 수행한다.
   3. 기본타입이라면 방싱 클래스의 hashcode() 함수를 c 에 더해준다.
   4. 참조타입이라면 hashcode() 를 계싼한다.
   5. null 값에 대해선 0 을 쓰는 것은 강제는 아니지만 전통(?) 이다.
   6. 배열이라면 각각의 원소를 별도의 필드처럼 다룬다. 재귀적인 호출이 편할 수도 있다 .

3. 계산은 
   4. result = 31*result + c 의 형태로 한다.

31 인 이유는 오버플로가 발생해도 숫자를 잃지 않기 때문이다.
(짝수라면 잃는다고 함 )


전형적인 hashcode 는 아래와 같다.

```java

public int hashcode(){
    int result = Integer.hashcode(x);	
	result = 31*result + Integer.hashcode(y);
	result = 31*result + String.hashcode(color);
	
	return result;
}

```


만약 객체가 불변이라면 해시코드를 캐싱하는 것도 고려해볼만 하다고 한다.


### toSting() 을 재정의 하라 

1. 있어야 사용성이 좋고 디버깅이 좋아진다.
2. 포멧(이메일, 전화번호) 에 맞게 출력하지 안항도 되지만 , 의도는 밝혀야 한다.
3. 이 책의 저자는 일단 모든 구체클래스는 toString() 을 갖는 것을 권장한다.
   (물론 jpa entity 같은 프레임워크를 끼고 있는 경우는 예외이다. )

### Clone

Object 의 clone 을 쓰기보단 복사 생성자나 팩토리를 통해 clone 기능을 지원하는 것이 권장 된다.


왜냐면 Object 의 clone 을 쓰는 것이 더 손이 많이간다 .
clonable 의 exception 이라던지, clone 의 동기화라던지 ...


따라서 책에서는 clone 의 명세를 찾아서 할 수도 있는데, 그냥 딥카피 신경써서 팩토리나 생성자 복제를 사용하는 것을 추천한다 .



