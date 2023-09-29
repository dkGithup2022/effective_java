package generic_repository;

import generic_repository.entity.Article;
import generic_repository.entity.User;
import generic_repository.generic.GArticleDao;
import generic_repository.generic.GUserDao;

public class GenericRepository {
	public static void main(String[] args) {

		GUserDao userDao = new GUserDao();
		User u1 = new User();
		User u2 = new User();
		userDao.save(u1);
		userDao.save(u2);

		GArticleDao articleDao = new GArticleDao();
		Article a1 = new Article();
		Article a2 = new Article();

		articleDao.save(a1);
		articleDao.save(a2);

		System.out.println(userDao.findAll());
		System.out.println(articleDao.findAll());

	}
}

