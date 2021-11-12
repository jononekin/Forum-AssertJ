package domain;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import exceptions.NotEnoughtQuantityException;
import exceptions.NotInBasketException;
import exceptions.NotStockException;
import exceptions.PurchaseClosedException;

public class User {
	private String id;
	private String name;
	private String telephone;
	private List<Purchase> purchases=new Vector<Purchase>();;
	private Purchase basket;
	public User(String dni, String name, String telefono) {
		super();
		this.id = dni;
		this.name=name;
		this.telephone = telefono;
		purchases=new Vector<Purchase>();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public Iterator<Purchase> getPurchases(Date firstDate, Date lastDate){
		List<Purchase> cs=new Vector<Purchase>();
		System.out.println("#of purchases "+purchases.size());
		for(Purchase c:purchases) {
			if ( (c.getPurchasedDate().compareTo(firstDate)>=0) && (c.getPurchasedDate().compareTo(lastDate)<=0) )
				cs.add(c);}
		return cs.iterator();
	}
	public void buy(Date d) {
		basket.setPurchasedDate(d);
		purchases.add(basket);
		basket=null;
	}
	public void addBasket(Article a, int cantidad) throws PurchaseClosedException, NotStockException{
		if (basket==null) basket=new Purchase();
		basket.addBasket(a,cantidad);
	}
	public void removeBasket(Article a, int cantidad) throws PurchaseClosedException, NotInBasketException, NotEnoughtQuantityException {
		if (basket==null) basket=new Purchase();
		basket.removeBasket(a,cantidad);
	}
	public void removeLastPurchase() {
		Purchase pur=purchases.get(purchases.size()-1);
		Iterator<PurchasedArticle> iter=pur.getPurchaseIterator();
		PurchasedArticle pa;
		Article art;
		int q;
		while (iter.hasNext()) {
			pa=iter.next();
			art=pa.getArt();
			q=pa.getQuantity();
			art.addStock(q);	
		}
		
		purchases.remove(purchases.size()-1);
		
	}
	public Purchase getBasket() {
		return basket;
	}
	
	public String toString() {
		return id+ " "+" "+name+" "+telephone;
	}
}
