package shipFactory.data;

public class Ship {
	String name, color , capacity;

	public Ship(String name, String color, String capacity) {
		this.name = name;
		this.color = color;
		this.capacity = capacity;
	}

	@Override
	public String toString() {
		return "Ship{" +
			"name='" + name + '\'' +
			", color='" + color + '\'' +
			", capacity='" + capacity + '\'' +
			'}';
	}
}
