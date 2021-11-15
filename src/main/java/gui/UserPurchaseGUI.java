package gui;

import java.awt.EventQueue;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import businessLogic.ForumBL;
import businessLogic.ForumBLInterface;
import database.ForumInMemoryDAO;
import domain.Article;
import domain.User;
import exceptions.NotEnoughtQuantityException;
import exceptions.NotInBasketException;
import exceptions.NotStockException;
import exceptions.PurchaseClosedException;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTable;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Color;

public class UserPurchaseGUI extends JFrame {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	

	private ForumBLInterface forumBL;
	//private HashMap<String, Article> hashArticles=new HashMap<String, Article>();
	private JPanel contentPane;
	private JTextField textFieldQty;
	private JButton btnRemoveArticle;
	private JButton btnAddArticle;
	private JLabel lblQty;
	private JButton btnBuy;
	private JComboBox<Article> jComboBoxArticles;
	private DefaultComboBoxModel<Article> modelArticles = new DefaultComboBoxModel<Article>();
	private JLabel lblPrice1;
	private JCheckBox chckbxNewCheckBox;
	private final JLabel lblStock = new JLabel("Stock: ");
	private JLabel lblStock1;
	
	private JScrollPane scrollPanePurchases = new JScrollPane();

	private JTable tablePurchases;
	private DefaultTableModel tableModelPurchases;

	
	private String[] columnNamesPurchases = new String[] {"Qty", "PurchasedArticle", "total", "object"};
	protected Article art1;
	private JLabel lblResult;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					ForumBLInterface bl=new ForumBL(new ForumInMemoryDAO());
					User usr=bl.addUser("33404521B", "jon","tel456");
					UserPurchaseGUI frame = new UserPurchaseGUI(new User("33404521B", "jon","tel456"),bl);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public UserPurchaseGUI(ForumBLInterface forumBL) {
		 new UserPurchaseGUI(null, forumBL);

	}

	/**
	 * Create the frame.
	 */
	public UserPurchaseGUI(User u,  ForumBLInterface forumBL) {
		user=u;
		this.forumBL=forumBL;
		this.setName("userPurchaseGUI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblArticle = new JLabel("Select Article");
		lblArticle.setBounds(29, 25, 116, 23);
		contentPane.add(lblArticle);
		
		jComboBoxArticles = new JComboBox<Article>();
		jComboBoxArticles.setName("comboBoxArticles");
		jComboBoxArticles.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
			}
		});
		
		
		jComboBoxArticles.setModel(modelArticles);
		jComboBoxArticles.setBounds(144, 24, 153, 27);
		
		List<Article> articles = forumBL.getArticles();

		for (Article art : articles) {
			modelArticles.addElement(art);
		    //hashArticles.put(art.getDescription(), art);
		}	
		jComboBoxArticles.repaint();
		
		contentPane.add(jComboBoxArticles);
		
		JLabel lblPrice = new JLabel("Price:");
		lblPrice.setBounds(29, 76, 61, 16);
		contentPane.add(lblPrice);
		
		lblPrice1 = new JLabel("price");
		lblPrice1.setBounds(71, 76, 61, 16);
		contentPane.add(lblPrice1);
		
		JLabel lblOutlet = new JLabel("");
		lblOutlet.setForeground(Color.WHITE);
		lblOutlet.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblOutlet.setEnabled(false);
		lblOutlet.setBounds(29, 132, 61, 16);
		contentPane.add(lblOutlet);
		
		chckbxNewCheckBox = new JCheckBox("Outlet");
		chckbxNewCheckBox.setEnabled(false);
		chckbxNewCheckBox.setBounds(18, 114, 128, 23);
		contentPane.add(chckbxNewCheckBox);
		
		btnAddArticle = new JButton("Add Basket");
		btnAddArticle.setName("addBasket");
		btnAddArticle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Article art=(Article) jComboBoxArticles.getSelectedItem();
				String qty=textFieldQty.getText();
				
				
				try {
					forumBL.addBasket(user,art, Integer.parseInt(qty));
					
					int rows=tablePurchases.getRowCount();
					Article tableArt;
					boolean find=false;
					int r=0;
					while (r<rows && !find) {
						 tableArt=(Article)tableModelPurchases.getValueAt(r,3); // obtain article
						 if (tableArt==art) {
							 Integer q=(Integer)tableModelPurchases.getValueAt(r,0);
							 q=q+Integer.parseInt(qty);
							 tableModelPurchases.setValueAt(q, r, 0);
							 tableModelPurchases.setValueAt(q*art.getPrice(), r, 2);
							 
							 find=true;
							 
						 }
						 r++;
					}
					
				if (!find) {
					Vector<Object> row = new Vector<Object>();

					row.add(Integer.parseInt(qty));
					row.add(art.getDescription());
					row.add(Integer.parseInt(qty)*art.getPrice());
					row.add(art); // art object added in order to obtain it with tableModelEvents.getValueAt(i,2)
					tableModelPurchases.addRow(row);		

			
					tableModelPurchases.setColumnCount(4); // another column added to allocate ev objects

					tablePurchases.getColumnModel().getColumn(0).setPreferredWidth(25);
					tablePurchases.getColumnModel().getColumn(1).setPreferredWidth(238);
					tablePurchases.getColumnModel().getColumn(2).setPreferredWidth(20);

					tablePurchases.getColumnModel().removeColumn(tablePurchases.getColumnModel().getColumn(3)); // not shown in JTable
				}
				
					lblStock1.setText(Integer.toString(art.getStock()));
					btnBuy.setEnabled(true);

				
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (PurchaseClosedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NotStockException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnAddArticle.setBounds(121, 225, 117, 29);
		contentPane.add(btnAddArticle);
		
		lblQty = new JLabel("qty");
		lblQty.setBounds(309, 28, 20, 16);
		contentPane.add(lblQty);
		
		textFieldQty = new JTextField();
		textFieldQty.setName("articleQty");
		textFieldQty.setBounds(337, 24, 61, 23);
		contentPane.add(textFieldQty);
		textFieldQty.setColumns(10);
		
		btnRemoveArticle = new JButton("Remove Basket");
		btnRemoveArticle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String qty=textFieldQty.getText();

				int row=tablePurchases.getSelectedRow();
				
				Article art=(Article)tableModelPurchases.getValueAt(row,3); // obtain article
				

				try {
					try {
						forumBL.removeBasket(user, art, (Integer)tableModelPurchases.getValueAt(row,0));
					
					lblStock1.setText(Integer.toString(art.getStock()));
					tableModelPurchases.removeRow(row);

					
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (PurchaseClosedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NotInBasketException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NotEnoughtQuantityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				} catch (NotStockException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnRemoveArticle.setBounds(232, 225, 135, 29);
		contentPane.add(btnRemoveArticle);
		

		btnBuy = new JButton("Buy");
		btnBuy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					forumBL.buy(user, new Date());
					float bonus=forumBL.getBonus(user.getId());
					lblResult.setText("Obtained bonus: "+Float.toString(bonus));
					btnBuy.setEnabled(false);
					tablePurchases.getModel(); 
					DefaultTableModel dm = (DefaultTableModel)tablePurchases.getModel();
					while(dm.getRowCount() > 0)
					{
					    dm.removeRow(0);
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnBuy.setName("buy");

		btnBuy.setBounds(393, 225, 135, 29);
		contentPane.add(btnBuy);
		
		tablePurchases = new JTable();
		tablePurchases.setName("tablePurchases");
		tablePurchases.setBounds(30, 260, 403, 200);
		//contentPane.add(tablePurchases);
		lblStock.setBounds(29, 93, 44, 29);
		contentPane.add(lblStock);
		
		lblStock1 = new JLabel("stock");
		lblStock1.setBounds(84, 99, 61, 16);
		contentPane.add(lblStock1);
		
		jComboBoxArticles.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				Article art =  (Article) jComboBoxArticles.getSelectedItem();
				lblPrice1.setText(Float.toString(art.getPrice()));
				
			}
		});
		
		// first initialization of fields
		jComboBoxArticles.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				Article art =  (Article) jComboBoxArticles.getSelectedItem();
				lblPrice1.setText(Float.toString(art.getPrice()));
				lblStock1.setText(Integer.toString(art.getStock()));
				chckbxNewCheckBox.setSelected(art.isOutlet());
			}
		});
		jComboBoxArticles.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				Article art =  (Article) jComboBoxArticles.getSelectedItem();
				lblPrice1.setText(Float.toString(art.getPrice()));
				lblStock1.setText(Integer.toString(art.getStock()));
				chckbxNewCheckBox.setSelected(art.isOutlet());
			}
		});
		scrollPanePurchases.setBounds(new Rectangle(175, 63, 346, 150));
		scrollPanePurchases.setViewportView(tablePurchases);
		
		tableModelPurchases = new DefaultTableModel(null, columnNamesPurchases); 
		
		tableModelPurchases.setColumnCount(4); // another column added to allocate ev objects

		tablePurchases.setModel(tableModelPurchases);
		tablePurchases.getColumnModel().getColumn(0).setPreferredWidth(25);
		tablePurchases.getColumnModel().getColumn(1).setPreferredWidth(238);
		tablePurchases.getColumnModel().getColumn(2).setPreferredWidth(20);
		tablePurchases.getColumnModel().removeColumn(tablePurchases.getColumnModel().getColumn(3)); // not shown in JTable


		this.getContentPane().add(scrollPanePurchases, null);
		
		lblResult = new JLabel("result");
		lblResult.setName("resultText");
		lblResult.setBounds(131, 282, 351, 16);
		contentPane.add(lblResult);
		
		tablePurchases.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	            // do some actions here, for example
	            // print first column value from selected row
				if (!event.getValueIsAdjusting() && tablePurchases.getSelectedRow() != -1) {
	        		art1=(Article)tableModelPurchases.getValueAt(tablePurchases.getSelectedRow(),3);
				    jComboBoxArticles.setSelectedItem(art1);
				}
	        }
	    });
		
	}
}
