
import static org.assertj.core.api.Assertions.assertThat;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JComboBoxFixture;
import org.assertj.swing.fixture.JLabelFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.junit.After;
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

	// Aldi bat exekutatzen da GUI-a hasieratzeko robot-batekin
	@BeforeClass
	public  static void setUpOnce() {
		robot = BasicRobot.robotWithNewAwtHierarchy();
		bl=new ForumBL(new ForumInMemoryDAO());
		User usr=bl.addUser("33404521B", "jon","tel456");
		userPurchaseGUI=  new UserPurchaseGUI(usr, bl);
		userPurchaseGUI.setVisible(true);

		//mainWindowFixture = new FrameFixture(robot,userPurchaseGUI);

		mainWindowFixture=WindowFinder.findFrame("userPurchaseGUI").using(robot);    	
	}

	/**
	 * Proba-gailuak hasieratzen ditu, proba-metodo bat exekutatzen 
	 * den bakoitzean exekutatzen da
	 */
	@Before
	public void setUp() {  
	}

	/**     
	 * Proba-metodo bakoitza exekutatu ondoren erabilitako baliabideak garbitzen ditu, 
	 * teklatuaren blokeoa askatzen du hurrengo probarako      
	 */
	@After
	public void tearDown() {
		mainWindowFixture.cleanUp();
	}
	// Zure test-ak
	@Test
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

		try {
			//fix input and output values
			Article art=bl.getStock("2");
			String qty="1";
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

	public  void lo(int s) {
		try {
			TimeUnit.SECONDS.sleep(s);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
