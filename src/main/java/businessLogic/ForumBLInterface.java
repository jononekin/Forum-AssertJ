package businessLogic;

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

public interface ForumBLInterface {

	User addUser(String id, String name, String tel);

	boolean removeUser(String id);

	User getUser(String id);

	//Artikulua saskira gehitzen du
	void addBasket(User u, Article art, int quantity) throws PurchaseClosedException, NotStockException;

	public void removeBasket(User u, Article art, int quantity) throws PurchaseClosedException, NotStockException, NotInBasketException, NotEnoughtQuantityException;

	//Saskian dauden artikuloak erosi egiten dira
	void buy(User u, Date d);

	Iterator<Purchase> getPurchases(User u, Date firstDate, Date lastDate);

	Article addArticleStock(String id, String desc, int precio, boolean isOutlet, int stock);
	
	Article addStock(String id, int stock);


	Article removeStock(String id);

	float getBonus(String id) throws Exception;

	List<Article> getArticles();
	
	Article getStock(String id) throws Exception;
	
	public void removeLastPurchase(User usr);


}