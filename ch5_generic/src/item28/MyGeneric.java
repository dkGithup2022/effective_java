package item28;

import java.util.ArrayList;
import java.util.List;

public class MyGeneric {
	public static void main(String[] args) {
		List<String> names = new ArrayList<>();
		names.add("dk");
		String name = names.get(0);
		System.out.println(name);

		List<Number> nums = new ArrayList<>();
		nums.add(1);
		nums.add(1.0);
		Number n = nums.get(0);
		System.out.println(nums);

	}
}

/*
아래는 List<String> 으로 초기화 되지만 ,
사실 제너릭을 쓰면 List<Object> , List 가 될 수도 있다.
(런타임 중엔 타입 정보가 지워짐)
리턴 타입에서 제너릭에 맞는 형 변환을 함.




public class MyGeneric {
    public MyGeneric() {
    }

    public static void main(String[] args) {
        List<String> names = new ArrayList();
        names.add("dk");
        String name = (String)names.get(0); -> 제너릭 타입에
        System.out.println(name);
    }
}
	*/

/*
바이트 코드
 // access flags 0x9
  public static main([Ljava/lang/String;)V
   L0
    LINENUMBER 8 L0
    NEW java/util/ArrayList -> 타입 정보가 애초에 없다.
    DUP
    INVOKESPECIAL java/util/ArrayList.<init> ()V
    ASTORE 1
   L1
    LINENUMBER 9 L1
    ALOAD 1
    LDC "dk"
    INVOKEINTERFACE java/util/List.add (Ljava/lang/Object;)Z (itf)
    POP
   L2
    LINENUMBER 10 L2
    ALOAD 1
    ICONST_0
    INVOKEINTERFACE java/util/List.get (I)Ljava/lang/Object; (itf)
    CHECKCAST java/lang/String
    ASTORE 2
   L3
    LINENUMBER 11 L3
    GETSTATIC java/lang/System.out : Ljava/io/PrintStream;
    ALOAD 2
    INVOKEVIRTUAL java/io/PrintStream.println (Ljava/lang/String;)V
   L4
    LINENUMBER 13 L4
    RETURN
   L5
    LOCALVARIABLE args [Ljava/lang/String; L0 L5 0
    LOCALVARIABLE names Ljava/util/List; L1 L5 1
    // signature Ljava/util/List<Ljava/lang/String;>;
    // declaration: names extends java.util.List<java.lang.String>
    LOCALVARIABLE name Ljava/lang/String; L3 L5 2
    MAXSTACK = 2
    MAXLOCALS = 3
}

 */