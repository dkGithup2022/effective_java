package shipFactory;

import shipFactory.data.ContainerShip;
import shipFactory.data.Ship;

public class ContainerShipFactory extends ShipFacotry {

	private ContainerShipFactory() {
	}

	private static class ContainerShipFactoryHolder {
		static final ContainerShipFactory factory = new ContainerShipFactory();
	}

	public static ContainerShipFactory getInstance() {
		return ContainerShipFactoryHolder.factory;
	}

	@Override
	protected Ship create() {
		return new ContainerShip("container ship", "blue", "10k");
	}
}
