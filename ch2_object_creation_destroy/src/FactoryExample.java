import shipFactory.ContainerShipFactory;
import shipFactory.OiltankShipFactory;
import shipFactory.data.Ship;

public class FactoryExample {
	/*
	* effective 자바의 정적 팩토리 메소드 패턴과는 연관은 없음 .
	* gof 의 팩토리 예시
	* */
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

