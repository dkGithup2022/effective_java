package item33;

import java.util.HashMap;
import java.util.Map;

/*
* 타입 안전 이종 컨테이너를 고려하라.
*
* -> 지금 까지는 하나의 클래스만 타입 매개변수로 표시하거나 super, extends 로 표시했음.
* -> 타입 자체를 인자로 받아서 타입에 맞는 로직을 새로 짜고 싶다..
*
* 라는 배경에서 등장한게 타입 안전 이종 컨테이너 이다.
* 어려운 개념인거 맞다.
*
* */
public class Favorites{
	 	private Map<Class, Object> map = new HashMap();

		 public void put(Class clazz, Object value){
			 this.map.put(clazz, value);
		 }

		 public Object get(Class clazz){
			 return this.map.get(clazz);
		 }

	public static void main(String[] args) {
		Favorites favorites = new Favorites();
		favorites.put(String.class, 3.0); // 이런게 가능해져 버림 /type safe 하지 않음 .
	}
}
