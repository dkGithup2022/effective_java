package code_37_6;

import static java.util.stream.Collectors.*;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Stream;

public class Main {
}

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
