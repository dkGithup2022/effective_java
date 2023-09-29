package item28;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Chosser_Array<T> {
	private final List<T> choiceList;

	public Chosser_Array(Collection<T> choices) {
		this.choiceList = new ArrayList<>(choices);
	}

	public Object choose() {
		Random rd = ThreadLocalRandom.current();
		return choiceList.get(rd.nextInt(choiceList.size()));
	}

	public static void main(String[] args) {
		List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8);

		Chosser_Array<Integer> chooser = new Chosser_Array<>(list);
		for (int i = 0; i < 10; i++)
			System.out.println(chooser.choose());
	}
}
