package generic_repository.beforeGeneric;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import generic_repository.entity.User;

public class UserDao {
	private static Set<User> db = new HashSet();

	public void save(User u) {
		db.add(u);
	}

	public List<User> findAll() {
		return List.of(db.toArray(User[]::new));
	}
}
