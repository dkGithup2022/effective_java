package shipFactory;

import shipFactory.data.Ship;

public abstract class ShipFacotry {

	/*
	* 목적
	*  1. 팩토리를 통한 정해진 형식의 객체 생성 ( 다른 형식은 생성을 제한할 수도 있음 )
	*  2. 생성과 비즈니스 로직의 분리
	*  3. 추가적인 배 객체가 생기면 기존의 클래스를 수정하지 않고 추가 가능 .
	* 	- 가령 "조각배" 생성시 , 조각배클래스 , 조각배 생성 클래스만 추가하여 확장이 가능 .
	*  4.  singleton 적용하여 static 하게 호출 가능한 객체들 .
	* */

	public final Ship orderShip(String email){
		validate(email);
		Ship ship = create();
		sendEmailTo(email, ship);
		return ship;
	}

	abstract protected  Ship create();

	private void validate(String email){
		if(email == null || email.isBlank())
			throw new IllegalArgumentException("이메일을 남겨주세요 ");
	}

	private void sendEmailTo(String email, Ship ship){
		System.out.println("발송 : " + email);
		System.out.println("상품 : "+ ship.toString());
	}

}
