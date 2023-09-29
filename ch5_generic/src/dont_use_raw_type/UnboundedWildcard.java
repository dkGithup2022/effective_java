package dont_use_raw_type;

import java.util.List;

public class UnboundedWildcard {
	public static void main(String[] args) {

	}

	public void chage(final List<?> list,  Object o ){
		// 컴파일 에러, 비 한정적 와일드 카드는 수정을 허용하지 않음 .
		// list.add(o);
	}


}
