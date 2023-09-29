package use_list_rather_than_array;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Chooser {
	public static void main(String[] args) {
		List<Integer> original = List.of(1, 2, 3, 4, 5);

		/* bad */

		BadChooser badChooser = new BadChooser(original);
		System.out.println(badChooser.choose());

		/**/

		GoodChooser goodChooser = new GoodChooser<>(original);
		System.out.println(goodChooser.choose());


	}
}

class BadChooser {
	// 사용할 때마다 형 변환을 해야 하는 나쁜 녀석
	private final Object[] choiceArray;

	public BadChooser(Collection choices) {
		this.choiceArray = choices.toArray();
	}

	public Object choose() {
		Random rnd = ThreadLocalRandom.current();
		return choiceArray[rnd.nextInt(choiceArray.length)];
	}
}

class GoodChooser<T> {
	private final List<T> choiceArray;

	public GoodChooser(Collection choices) {
		this.choiceArray = new ArrayList(choices);
	}

	public Object choose() {
		Random rnd = ThreadLocalRandom.current();
		return choiceArray.get(rnd.nextInt(choiceArray.size()));
	}
}