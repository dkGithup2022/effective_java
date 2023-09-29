package code34_9;

import static java.util.stream.Collectors.*;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import code_34_4.Operation;

public enum PayrollDay {
	MONDAY, TUESDAY, WEDNESDAY(PayType.EARN_MORE);

	private final PayType payType;

	private static final Map<String, PayrollDay> stringToEnum
		= Stream.of(values()).collect(toMap(Object::toString, e -> e));

	public static Optional<PayrollDay> fromString(String symbol) {
		return Optional.ofNullable(stringToEnum.get(symbol));
	}

	PayrollDay(PayType payType) {
		this.payType = payType;
	}

	PayrollDay() {
		this.payType = PayType.NORMAL;
	}

	enum PayType {
		EARN_MORE {
			public int bonus(int x) {
				return x * 2;
			}
		},
		NORMAL {
			public int bonus(int x) {
				return x;
			}
		};

		public abstract int bonus(int x);
	}

	public int earn(int hour, int pay) {
		return hour * payType.bonus(pay);
	}
}
