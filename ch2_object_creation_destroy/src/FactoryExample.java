import shipFactory.ContainerShipFactory;
import shipFactory.OiltankShipFactory;
import shipFactory.data.Ship;

public class FactoryExample {
	public static void main(String[] args) {
		factoryCase1();
	}

	// 싱글턴 확장 가능한 factory

	//
	private static void factoryCase1() {
		Ship containerShip = ContainerShipFactory.getInstance().orderShip("my email ");
		Ship oiltankShip = OiltankShipFactory.getInstance().orderShip("your email");

	}
}

