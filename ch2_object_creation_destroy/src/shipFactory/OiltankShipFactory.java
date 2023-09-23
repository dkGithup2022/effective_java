package shipFactory;

import shipFactory.data.OiltankShip;
import shipFactory.data.Ship;

public class OiltankShipFactory extends ShipFacotry {

	private OiltankShipFactory() {
	}

	private static class OiltankShipFactoryHolder {
		private static final OiltankShipFactory factory = new OiltankShipFactory();
	}

	public static OiltankShipFactory getInstance() {
		return OiltankShipFactoryHolder.factory;
	}

	@Override
	protected Ship create() {
		return new OiltankShip("oilTank ", "red", "222");
	}
}
