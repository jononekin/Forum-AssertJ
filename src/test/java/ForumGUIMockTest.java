import java.util.List;
import java.util.Vector;

import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JComboBoxFixture;
import org.assertj.swing.fixture.JLabelFixture;
import org.assertj.swing.fixture.JTableFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import businessLogic.ForumBL;
import businessLogic.ForumBLInterface;
import database.ForumInMemoryDAO;
import domain.Article;
import domain.User;
import gui.UserPurchaseGUI;

public class ForumGUIMockTest {
	private static FrameFixture mainWindowFixture;
	private static UserPurchaseGUI userPurchaseGUI;
	private static Robot robot;
	private static ForumBLInterface bl;
	private static User usr;
	
	
	// Proba-metodo guztiak erabiliko dituzten baliabideak hasieratzen ditu       
	@BeforeClass
	public  static void before() {
		bl=Mockito.mock(ForumBLInterface.class);

		try {
			Mockito.doReturn(new Article("2", "nike alphafly", 80, false, 3)).when(bl).getStock(Mockito.any(String.class));
			Mockito.doReturn(new User("33404521B", "jon","tel456")).when(bl).addUser(Mockito.any(String.class),Mockito.any(String.class),Mockito.any(String.class));

			Mockito.doReturn(createListArticles()).when(bl).getArticles();
			Mockito.doReturn(new Float(13.88)).when(bl).getBonus(Mockito.any(String.class));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		usr = bl.addUser("33404521B", "jon","tel456");

		  
	}

	//Proba-metodo bakoitza erabiliko dituen baliabideak hasieratzen ditu
	@Before
	public void beforeEach() {  
		
		robot = BasicRobot.robotWithNewAwtHierarchy();
		userPurchaseGUI=  new UserPurchaseGUI(usr, bl);
		userPurchaseGUI.setVisible(true);
		mainWindowFixture=WindowFinder.findFrame("userPurchaseGUI").using(robot);  
			
	}
	//Proba-metodo bakoitza exekutatu ondoren erabilitako baliabideak garbitzen ditu,       
	@After
	public void afterEach() {
		 
		mainWindowFixture.cleanUp();

	}
	
	     
	//Proba-metodo guztiak erabili  dituzten baliabideak eabatzen ditu       
	@AfterClass
	public static void after() {
		bl.removeUser("33404521B");
	}
	
	// Zure test-ak
	@Test
	public void test1() { 
		try {
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//find the fixtures
		//input fields
		JTextComponentFixture jTextQtyFixture=mainWindowFixture.textBox("articleQty");
		JComboBoxFixture jComboBoxFixture=mainWindowFixture.comboBox("comboBoxArticles");
		//buttons
		JButtonFixture addButtonFixture=mainWindowFixture.button("addBasket");
		JButtonFixture getBuyButtonFixture=mainWindowFixture.button("buy");
		//result label
		JLabelFixture labelFixture=mainWindowFixture.label("resultText");
		Article art = null;
		String qty = null;
		try {
			//fix input and output values
			art=bl.getStock("2");
		    qty="1";
			String expectedResult="Obtained bonus: 13.88";

			//execute the interactions
			jComboBoxFixture.selectItem(art.toString());
			jTextQtyFixture.setText(qty);
			addButtonFixture.click();
			getBuyButtonFixture.click();
			//
			//write the assertions
			labelFixture.requireText(expectedResult);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	private static List<Article> createListArticles(){
		List<Article> articles=new Vector<Article>();
		articles.add(new Article("1", "asics nimbus", 70, false, 3));
		articles.add(new Article("2", "nike alphafly", 80, false, 3));
		articles.add(new Article("3", "adidas ultraboost", 90, true, 5));
		articles.add(new Article("4", "puma socks", 5, false, 50));
		return articles;
		}
}
