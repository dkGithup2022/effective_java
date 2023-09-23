package pizzaBuilder;

public class Main {
	public static void main(String[] args) {
		NYPizza nyPizza = new NYPizza
			.Builder(NYPizza.Size.SMALL)
			.addTopping(Pizza.Topping.ONION)
			.addTopping(Pizza.Topping.HAM)
			.build();

		Calzone calzone = new Calzone.Builder().sauceInside()
			.addTopping(Pizza.Topping.PEPPER)
			.addTopping(Pizza.Topping.MUSHROOM)
			.addTopping(Pizza.Topping.HAM)
			.build();

		System.out.println("nyPizza : " + nyPizza.toString());

		System.out.println("calzone: " + calzone.toString());

	}
}
