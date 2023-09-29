package item45;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ComplexStream {

	public static void main(String[] args) {
		String sample = "a ba a s b c ac a rs t st ";
		List<String> list = Stream.of(sample.split(" ")).collect(Collectors.toList());
/*
		list.stream().collect(
			groupingBy(word -> word.chars().sorted()
				.collect(
					StringBuffer::new,
					(sb, c)-> sb.append((char)c),
					StringBuilder::append).toString()))
			.values().stream()
			.filter(group -> group.size() >= minSize)
			....
	*/
	}
}
