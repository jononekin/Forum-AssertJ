package businessLogic;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import database.*;
import domain.Article;
import domain.Purchase;
import domain.PurchasedArticle;
import domain.User;
import exceptions.NotEnoughtQuantityException;
import exceptions.NotInBasketException;
import exceptions.NotStockException;
import exceptions.PurchaseClosedException;

public class ForumBL implements ForumBLInterface {
	ForumDAOInterface dao;
	//API Usuarios
	
	public ForumBL(ForumDAOInterface dao) {
		this.dao=dao;
	}
	/* (non-Javadoc)
	 * @see businessLogic.ForumBLInterface#addUser(java.lang.String, java.lang.String, java.lang.String)
	 */
	public User addUser(String id, String name, String tel) {
		if (id==null || name==null) throw new  RuntimeException("id or name is null");
		if (!dao.existUserDAO(id)) {
			  dao.addUserDAO(id, name, tel);
			  return dao.getUserDAO(id);
		}
		else throw new  RuntimeException("id no in DB");
	}
	/* (non-Javadoc)
	 * @see businessLogic.ForumBLInterface#removeUser(java.lang.String)
	 */
	public boolean removeUser(String id) {
		dao.removeUserDAO(id);
		return false;
	}
	/* (non-Javadoc)
	 * @see businessLogic.ForumBLInterface#getUser(java.lang.String)
	 */
	public User getUser(String id) {
		return dao.getUserDAO(id);
	}
	//Artikulua saskira gehitzen du
	/* (non-Javadoc)
	 * @see businessLogic.ForumBLInterface#addBasket(domain.User, domain.Article, int)
	 */
	public void addBasket(User u, Article art, int quantity) throws PurchaseClosedException, NotStockException{
		if (quantity<=0) throw new  RuntimeException("ERROR, quantity must be greater that 0");
		if (u==null || art==null) throw new  RuntimeException("ERROR, user or article is null");
		
		//in other case
		dao.addBasketDAO(u, art, quantity);
	}

	//Saskian dauden artikuloak erosi egiten dira
	/* (non-Javadoc)
	 * @see businessLogic.ForumBLInterface#buy(domain.User, java.util.Date)
	 */
	
	//Artikulua saskira ezabatzen du
		/* (non-Javadoc)
		 * @see businessLogic.ForumBLInterface#addBasket(domain.User, domain.Article, int)
		 */
		public void removeBasket(User u, Article art, int quantity) throws PurchaseClosedException, NotStockException, NotInBasketException, NotEnoughtQuantityException{
			if (u==null || art==null) throw new  RuntimeException("ERROR, user or article is null");
			
			//in other case
			dao.removeBasketDAO(u, art, quantity);
		}

		//Saskian dauden artikuloak erosi egiten dira
		/* (non-Javadoc)
		 * @see businessLogic.ForumBLInterface#buy(domain.User, java.util.Date)
		 */
	public void buy(User u, Date d) {
		 dao.buyDAO(u, d);
	}

	/* (non-Javadoc)
	 * @see businessLogic.ForumBLInterface#getPurchases(domain.User, java.util.Date, java.util.Date)
	 */
	public Iterator<Purchase> getPurchases(User u, Date firstDate, Date lastDate) {
		return dao.getPurchasesDAO(u,firstDate, lastDate);
	}
	/* (non-Javadoc)
	 * @see businessLogic.ForumBLInterface#addStock(java.lang.String, java.lang.String, int, boolean, int)
	 */
	public Article addStock(String id, String desc, int precio, boolean isOutlet, int stock) {
		return dao.addStockDAO(id, desc, precio, isOutlet,stock);
	}
	/* (non-Javadoc)
	 * @see businessLogic.ForumBLInterface#removeStock(java.lang.String)
	 */
	public Article removeStock(String id) {
		return dao.removeStockDAO(id);
		}
	/* (non-Javadoc)
	 * @see businessLogic.ForumBLInterface#getBonus(java.lang.String)
	 */
	public float getBonus(String id) throws Exception {
		if ((id==null) || (!new ValidadorDNI(id).validar())) 
			throw new Exception("id null or not valid");
		
		User u=dao.getUserDAO(id);
		
		
		if (u==null) throw new Exception("NAN not in Database");
		
		if (u.getTelephone()==null) throw new Exception(id+" not registered telephone");
	    
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date firstDate = sdf.parse("01/09/2020");
		Date lastDate = sdf.parse("06/12/2025");

		Iterator<Purchase> purchases=dao.getPurchasesDAO(u,firstDate, lastDate);
		return getReturnVat(purchases);
		
	}
	
	public float getBonusBasket(String id) throws Exception {
		if ((id==null) || (!new ValidadorDNI(id).validar())) 
			throw new Exception("id null or not valid");
		
		User u=dao.getUserDAO(id);
		
		
		if (u==null) throw new Exception("NAN not in Database");
		
		if (u.getTelephone()==null) throw new Exception(id+" not registered telephone");
		
		Vector<Purchase> basketPurchase=new Vector<Purchase>();
		basketPurchase.add(u.getBasket());
		return getReturnVat(basketPurchase.iterator());
		
	}
	/* (non-Javadoc)
	 * @see businessLogic.ForumBLInterface#getArticles()
	 */
	public List<Article> getArticles(){
		return dao.getStockDAO();
	}
	
	
	private float getReturnVat(Iterator<Purchase> purchases) {
		float sumPurchases=0;
		float vat=0;
		while (purchases.hasNext()) {
			Purchase c=purchases.next();
			Iterator <PurchasedArticle> articles=c.getPurchaseIterator();
			while (articles.hasNext()) {
				PurchasedArticle article=articles.next();
				if (!article.isOutlet()) sumPurchases=sumPurchases+article.getPrice()*article.getQuantity();

			}
		}
		if (sumPurchases>30)
			if (sumPurchases>288) vat=50;
			  else vat=(float) (sumPurchases*0.1735);
		
		return vat;
	}
	public Article getStock(String id) throws Exception {
		if (id==null)  throw new Exception("id null");
		return dao.getStockDAO(id);
	}
}

