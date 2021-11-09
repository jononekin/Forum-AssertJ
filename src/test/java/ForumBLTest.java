

import static org.junit.Assert.*;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import businessLogic.ForumBL;
import businessLogic.ForumBLInterface;
import database.ForumDAOInterface;
import database.ForumInMemoryDAO;
import domain.Article;
import domain.User;
import exceptions.NotStockException;
import exceptions.PurchaseClosedException;

public class ForumBLTest {
	ForumDAOInterface forumDAO=new ForumInMemoryDAO();
	ForumBLInterface sut=new ForumBL(forumDAO);
	
	
	//@Test
	/*public void test() {
		String nan="75075708M";
		float expectedValue=(float)26.025;
		User u=sut.addUser(nan, "jon", "7666666");
		Article art2=new Article("2","asics nimbus",150,false,5);
		try {
			sut.addBasket(u, art2, 1);
		
		sut.buy(u, new Date());
		
			float bono=sut.getBonus(nan);
			sut.removeUser(nan);
			sut.removeStock("2");
			
			System.out.println(bono);
			assertTrue(bono==expectedValue);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			sut.removeUser(nan);
			sut.removeStock("2");
			e.printStackTrace();
			fail();
		}
		
}*/
	
	

}
