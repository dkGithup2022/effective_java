package generic_repository.beforeGeneric;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import generic_repository.entity.Article;

public class ArticleDao {
	private static Set<Article> db = new HashSet();

	public void save(Article u) {
		db.add(u);
	}

	public List<Article> findAll() {
		return List.of(db.toArray(Article[]::new));
	}
}
