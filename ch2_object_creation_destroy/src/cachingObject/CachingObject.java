package cachingObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Pattern;

public class CachingObject {

	private static final Pattern ENGLISH_PATTERN = Pattern.compile("^[a-zA-Z0-9]*$");

	public static void main(String[] args) {
		String cnn = "On the left flank [near Verbove] we have a breakthrough and we continue to advance further,” Oleksandr Tarnavsky told CNN Senior International Correspondent Frederik Pleitgen during an interview on Friday, though he conceded his troops were moving slower than anticipated";
		Long beforeCaching = measureExecutionTime(() -> isEnglish(cnn));
		Long afterCaching = measureExecutionTime(() -> isEnglishWithCaching(cnn));

		System.out.println("before caching : " + beforeCaching);
		System.out.println("after caching : " + afterCaching);
	}

	public static long measureExecutionTime(Runnable code) {
		long startTime = System.currentTimeMillis();
		for(int i = 0 ; i< 10000; i++)
			code.run(); // 람다 실행
		long endTime = System.currentTimeMillis();
		return endTime - startTime;
	}

	private static Boolean isEnglish(String s) {
		return s.matches("^[a-zA-Z0-9]*$");
	}

	private static Boolean isEnglishWithCaching(String s) {
		return ENGLISH_PATTERN.matcher(s).matches();
	}
}
