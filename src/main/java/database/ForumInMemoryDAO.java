package database;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Vector;

import domain.Article;
import domain.Purchase;
import domain.User;
import exceptions.NotEnoughtQuantityException;
import exceptions.NotInBasketException;
import exceptions.NotStockException;
import exceptions.PurchaseClosedException;

public class ForumInMemoryDAO implements ForumDAOInterface  {
	private HashMap<String, User> dbUsers=new HashMap<String,User>();
	private HashMap<String, Article> dbArticles=new HashMap<String,Article>();

	
	public User addUserDAO(String id, String name, String tel) {
		User u=new User(id, name, tel);
		dbUsers.put(id, u);
		return u;
	}
	
	public User removeUserDAO(String id) {
		User u=dbUsers.remove(id);
		return u;
	}
	
	public User getUserDAO(String id) {
		return dbUsers.get(id);
	}

	public void addBasketDAO(User u, Article art,int quantity) throws PurchaseClosedException, NotStockException {
		u.addBasket(art, quantity);
	}

	public void removeBasketDAO(User u, Article art,int quantity) throws PurchaseClosedException, NotStockException, NotInBasketException, NotEnoughtQuantityException {
		u.removeBasket(art, quantity);
	}
	
	public void buyDAO(User u, Date d) {
		 u.buy(d);
	}
	
	
	public Iterator<Purchase> getPurchasesDAO(User u, Date firstDate, Date lastDate) {
		return u.getPurchases(firstDate, lastDate);
	}


	public void removeLastPurchaseDAO(User usr) {
		usr.removeLastPurchase();
		
	}
	public Article addArticleDAO(String id, String desc, int price, boolean isOutlet, int stock) {
		Article art=new Article(id,  desc,  price,  isOutlet,stock);
		dbArticles.put(id, art);
		return art;
	}
	
	public Article addStockDAO(String id,int stock) {
		Article art=dbArticles.get(id);
		art.addStock(stock);
		return art;
	}

	public Article removeStockDAO(String id) {
		return dbArticles.remove(id);
		}
	
	public boolean existUserDAO(String id) {
		// TODO Auto-generated method stub
		return dbUsers.get(id)!=null;
	}

	public List<Article> getStockDAO() {
		// TODO Auto-generated method stub
		List<Article> articles=new Vector<Article>();
		
		for(Entry<String, Article> entry: dbArticles.entrySet()) {
	       articles.add(entry.getValue());
	    }
		return articles;
	}
	public Article getStockDAO(String id) {
		// TODO Auto-generated method stub
		return dbArticles.get(id);
	}

	public ForumInMemoryDAO() {
		super();
		initializeDb();
	}
	
	private void initializeDb() {
		dbArticles.put("1", new Article("1", "asics nimbus", 70, false, 3));
		dbArticles.put("2", new Article("2", "nike alphafly", 80, false, 3));
		dbArticles.put("3", new Article("3", "adidas ultraboost", 90, true, 5));
		dbArticles.put("4", new Article("4", "puma socks", 5, false, 50));



		
	}
	

}
