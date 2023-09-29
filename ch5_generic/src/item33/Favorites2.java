package item33;

import java.util.HashMap;
import java.util.Map;

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
