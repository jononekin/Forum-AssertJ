package database;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import domain.Article;
import domain.Purchase;
import domain.User;
import exceptions.NotEnoughtQuantityException;
import exceptions.NotInBasketException;
import exceptions.NotStockException;
import exceptions.PurchaseClosedException;

public interface ForumDAOInterface {

	boolean existUserDAO(String id);
	
	User addUserDAO(String id, String name, String tel);

	User removeUserDAO(String id);

	User getUserDAO(String id);
	
	void addBasketDAO(User u, Article art, int quantity) throws PurchaseClosedException, NotStockException;
	
	void removeBasketDAO(User u, Article art, int quantity) throws PurchaseClosedException, NotStockException, NotInBasketException, NotEnoughtQuantityException;


	void buyDAO(User u, Date d);

	Iterator<Purchase> getPurchasesDAO(User u, Date firstDate, Date lastDate);
	
	void removeLastPurchaseDAO(User usr);

	Article addArticleDAO(String id, String desc, int price, boolean isOutlet, int stock);
	
	Article addStockDAO(String id, int stock);


	Article removeStockDAO(String id);
	
	List<Article> getStockDAO();
	
	Article getStockDAO(String id);

}