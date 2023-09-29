package item46;

import static java.util.stream.Collectors.*;

import java.util.Arrays;
import java.util.Map;

public class Examples {
	public static void main(String[] args) {
		example1();
	}

	public static void example1() {
		Map<String, MyEnum> map =
			Arrays.stream(MyEnum.values())
				.collect(toMap(MyEnum::toString, e -> e));

		System.out.println("MAP : " + map.toString());
	}

	public static void example2() {
		Map<String, MyEnum2> map = Arrays.stream(MyEnum2.values())
			.collect(toMap(MyEnum2::toString, e -> e, (oldVal, newVal) -> newVal));
	}

	enum MyEnum {
		HELLO("H"),
		WORLD("W");
		private String symbol;

		MyEnum(String symbol) {
			this.symbol = symbol;
		}
	}

	enum MyEnum2 {
		HELLO("H", (int)'h'),
		WORLD("W", (int)'w'),
		HOO("h", (int)'f'),
		BAR("B", (int)'b');

		private String symbol;
		private int value;

		MyEnum2(String symbol, int value) {
			this.symbol = symbol;
			this.value = value;
		}
	}
}
