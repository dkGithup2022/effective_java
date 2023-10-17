import java.util.Date;

public class DeffensiveCopy {
	public static void main(String[] args) {

	}

	public static void check_from_to_success(Date start, Date end) {
		Date start_ = new Date(start.getTime());
		Date end_ = new Date(end.getTime());

		if (start_.after(end_))
			throw new RuntimeException();
	}

	public static void check_from_to_fail(Date start, Date end) {
		if (start.after(end))
			throw new RuntimeException();
	}
}
