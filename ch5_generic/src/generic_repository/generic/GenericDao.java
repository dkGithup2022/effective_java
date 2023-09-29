package generic_repository.generic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import generic_repository.entity.Entity;

public class GenericDao<E extends Entity> {
	private final Set<E> db = new HashSet();

	public void save(E u) {
		db.add(u);
	}

	public List<E> findAll() {
		return new ArrayList<E>(db);
	}
}
