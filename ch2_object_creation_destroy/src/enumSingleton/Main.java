package enumSingleton;

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
