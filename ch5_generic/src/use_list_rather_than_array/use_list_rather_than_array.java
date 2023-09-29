package use_list_rather_than_array;

import java.util.ArrayList;
import java.util.List;

public class use_list_rather_than_array {
	public static void main(String[] args) {
		System.out.println("이게 왜 실행됨");
		Object[] objects = new Long[1];
		objects[0] = "타입이 달라서 안들어가용";
	}
	/*

	public static void compileError(){
		List<Object> list = new ArrayList<Long>();
		list.add(" 애초에 컴파일 에러 ");
	}

	 */
}
