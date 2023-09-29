package item46;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SideEffect {
	private static final String words = "a na bajak lajan aa a asa a a a a s s na ";
	private static final List<String> list = new ArrayList<>(List.of(words.split(" ")));

	public static void main(String[] args) {
		Map<String, Long> freq = new HashMap<>();

		list.stream().forEach(
			word -> freq.merge(word.toLowerCase(Locale.ROOT), 1L, Long::sum)
		);

		Map<String, Long> freq1 = list.stream().collect(
			groupingBy(String::toLowerCase, counting())
		);
	}
}
