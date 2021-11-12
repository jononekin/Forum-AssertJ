
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.data.TableCell;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JComboBoxFixture;
import org.assertj.swing.fixture.JLabelFixture;
import org.assertj.swing.fixture.JTableCellFixture;
import org.assertj.swing.fixture.JTableFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import businessLogic.ForumBL;
import businessLogic.ForumBLInterface;
import database.ForumInMemoryDAO;
import domain.Article;
import domain.User;

import gui.UserPurchaseGUI;

public class ForumGUITest {

	private static FrameFixture mainWindowFixture;
	private static UserPurchaseGUI userPurchaseGUI;
	private static Robot robot;
	private static ForumBLInterface bl;
	private static User usr;
	private JTableFixture tablePurchasesFixture;

	// Proba-metodo guztiak erabiliko dituzten baliabideak hasieratzen ditu       
	@BeforeClass
	public  static void before() {
		bl=new ForumBL(new ForumInMemoryDAO());
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
/*	@Test
	public void test1() { 
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
			lo(5);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
*/
	// Zure test-ak
	/*	@Test
		public void test2() { 
			//find the fixtures
			//input fields
			JTextComponentFixture jTextQtyFixture=mainWindowFixture.textBox("articleQty");
			JComboBoxFixture jComboBoxFixture=mainWindowFixture.comboBox("comboBoxArticles");
			tablePurchasesFixture = mainWindowFixture.table("tablePurchases");
			     
			//buttons
			JButtonFixture buyButtonFixture=mainWindowFixture.button("buy");
			JButtonFixture addArticleButtonFixture=mainWindowFixture.button("addBasket");
			JButtonFixture removeArticleButtonFixture=mainWindowFixture.button(JButtonMatcher.withText("Remove Basket"));

			//result label
			JLabelFixture labelFixture=mainWindowFixture.label("resultText");
			
			try {
				//fix input and output values
				Article art1=bl.getStock("1");
			    String qty1="2";
			    Article art2=bl.getStock("2");
			    String qty2="1";
				String expectedResult="Obtained bonus: 13.88";
				
				//execute the interactions
				jComboBoxFixture.selectItem(art1.toString());
				jTextQtyFixture.setText(qty1);
				addArticleButtonFixture.click();
				jComboBoxFixture.selectItem(art2.toString());
				jTextQtyFixture.setText(qty2);
				addArticleButtonFixture.click();

				int artRow =findRowArticle(art1.getDescription());
				tablePurchasesFixture.cell(TableCell.row(artRow).column(0)).click();
				removeArticleButtonFixture.click();
				buyButtonFixture.click();

				//write the assertions
				labelFixture.requireText(expectedResult);
				lo(5);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			bl.removeLastPurchase(usr);	
		}*/
		
		@Test
		public void test3() { 
			//find the fixtures
			//input fields
			JTextComponentFixture jTextQtyFixture=mainWindowFixture.textBox("articleQty");
			JComboBoxFixture jComboBoxFixture=mainWindowFixture.comboBox("comboBoxArticles");
			JTableFixture tablePurchasesFixture = mainWindowFixture.table("tablePurchases");
			     
			//buttons
			JButtonFixture buyButtonFixture=mainWindowFixture.button("buy");
			JButtonFixture addArticleButtonFixture=mainWindowFixture.button("addBasket");
			JButtonFixture removeArticleButtonFixture=mainWindowFixture.button(JButtonMatcher.withText("Remove Basket"));

			//result label
			JLabelFixture labelFixture=mainWindowFixture.label("resultText");
			
			try {
				//fix input and output values
				Article art1=bl.getStock("2");
			    String qty1="1";
			    Article art2=bl.getStock("1");
			    String qty2="2";
				String expectedResult="Obtained bonus: 13.88";
				
				//execute the interactions
				jComboBoxFixture.selectItem(art1.toString());
				jTextQtyFixture.setText(qty1);
				addArticleButtonFixture.click();
				jComboBoxFixture.selectItem(art2.toString());
				jTextQtyFixture.setText(qty2);
				addArticleButtonFixture.click();

				int artRow =findRowArticle(art1.getDescription());
				tablePurchasesFixture.cell(TableCell.row(artRow).column(0)).click();
				removeArticleButtonFixture.click();

				buyButtonFixture.click();

				//write the assertions
				labelFixture.requireText(expectedResult);
				lo(5);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
		
	public int findRowArticle(String  art) {
		boolean found=false;
		int r=0; 
		while ((r<=tablePurchasesFixture.rowCount()) && (!found)) {
			JTableCellFixture cell=tablePurchasesFixture.cell(TableCell.row(r).column(1));
			System.out.println("cell"+cell.value());
	    	if ((cell.value().compareTo(art)==0)) found=true;
	    	else r++;
		}
		if (found) return r; else return -1;
		
	}
	
	public  void lo(int s) {
		try {
			TimeUnit.SECONDS.sleep(s);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
