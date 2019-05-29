package form;

import java.awt.EventQueue;
import javafx.scene.control.ComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JMenu;
import javax.swing.JComboBox;
import javax.swing.border.TitledBorder;
import com.sun.glass.events.WindowEvent;

import Entities.Car;
import Entities.Client;
import Entities.Detail;
import Entities.DetailOnStock;
import Entities.IEntity;
import Entities.Master;
import Entities.Repair;
import Entities.Stock;
import form.FormNewConnection;


public class FormMain {
	JComboBox ComboBox;
	private JFrame frame;
	private JTable table;
	Connection connection = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormMain window = new FormMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FormMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		
		String[] str = { "Клиент", "Авто", "Мастер", "Починка", "Деталь", "Деталь на складе", "Склад" };
		frame = new JFrame();
		frame.setBounds(100, 100, 679, 312);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		

	
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu menuConnection = new JMenu("\u041F\u043E\u0434\u043A\u043B\u044E\u0447\u0435\u043D\u0438\u0435");
		menuBar.add(menuConnection);
		
		JMenuItem menuItemNew = new JMenuItem("\u041D\u043E\u0432\u043E\u0435 \u043F\u043E\u0434\u043A\u043B\u044E\u0447\u0435\u043D\u0438\u0435");
		menuItemNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FormNewConnection form = new FormNewConnection(frame);
				connection = form.getConnection();
				if (connection != null) {
					
				}
			}
		});
		menuConnection.add(menuItemNew);
		
		
		JMenuItem menuItemShutdown = new JMenuItem("\u041E\u0442\u043A\u043B\u044E\u0447\u0435\u043D\u0438\u0435");
		menuItemShutdown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Disconnect();
				for( int i = 0; i< table.getRowCount(); i++){
					
				}
			}
		});
		
		menuConnection.add(menuItemShutdown);
		
		JMenuItem menuItemExit = new JMenuItem("\u0412\u044B\u0445\u043E\u0434");
		menuItemExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
			}
		});
		menuConnection.add(menuItemExit);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 661, 250);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		table = new JTable();
		table.setBounds(12, 13, 523, 218);
		panel.add(table);
		
		ComboBox = new JComboBox(str);
		ComboBox.setBounds(547, 9, 102, 22);
		ComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refresh();
			}
		});
		panel.add(ComboBox);
		
		JButton buttonAdd = new JButton("\u0414\u043E\u0431\u0430\u0432\u0438\u0442\u044C");
		buttonAdd.setBounds(547, 61, 97, 25);
		buttonAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (connection != null) {
					IEntity entity;
					IForm form;
					try {
						switch ((String) ComboBox.getSelectedItem()) {
						case "Клиент":
							form = new ClientForm(connection);
							form.setVisible(true);
							entity = new Client();
							table.setModel(entity.TableModel(connection));
							break;
						case "Авто":
							form = new CarForm(connection);
							form.setVisible(true);
							entity = new Car();
							table.setModel(entity.TableModel(connection));
							break;
						case "Мастер":
							form = new MasterForm(connection);
							form.setVisible(true);
							entity = new Master();
							table.setModel(entity.TableModel(connection));
							break;
						case "Починка":
							form = new RepairForm(connection);
							form.setVisible(true);
							entity = new Repair();
							table.setModel(entity.TableModel(connection));
							break;
						case "Деталь":
							form = new DetailForm(connection);
							form.setVisible(true);
							entity = new Detail();
							table.setModel(entity.TableModel(connection));
							break;
						case "Деталь на складе":
							form = new DetailOnStockForm(connection);
							form.setVisible(true);
							entity = new DetailOnStock();
							table.setModel(entity.TableModel(connection));
							break;
						case "Склад":
							form = new StockForm(connection);
							form.setVisible(true);
							entity = new Stock();
							table.setModel(entity.TableModel(connection));
							break;
						}
					} catch (SQLException ex) {
						Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
			}
		});
		panel.add(buttonAdd);
		
		JButton buttonDel = new JButton("\u0423\u0434\u0430\u043B\u0438\u0442\u044C");
		buttonDel.setBounds(547, 99, 97, 25);
		buttonDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() >= 0 && connection != null) {
					int idEl = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
					IEntity entity;
					try {
						switch ((String) ComboBox.getSelectedItem()) {
						case "Клиент":
							entity = new Client();
							entity.removeElement(idEl, connection);
							table.setModel(entity.TableModel(connection));
							break;
						case "Авто":
							entity = new Car();
							entity.removeElement(idEl, connection);
							table.setModel(entity.TableModel(connection));
							break;
						case "Мастер":
							entity = new Master();
							entity.removeElement(idEl, connection);
							table.setModel(entity.TableModel(connection));
							break;
						case "Починка":
							entity = new Repair();
							entity.removeElement(idEl, connection);
							table.setModel(entity.TableModel(connection));
							break;
						case "Деталь":
							entity = new Detail();
							entity.removeElement(idEl, connection);
							table.setModel(entity.TableModel(connection));
							break;
						case "Деталь на складе":
							entity = new DetailOnStock();
							entity.removeElement(idEl, Integer.parseInt(table.getValueAt(table.getSelectedRow(), 1).toString()), connection);
							table.setModel(entity.TableModel(connection));
							break;
						case "Склад":
							entity = new Stock();
							entity.removeElement(idEl, connection);
							table.setModel(entity.TableModel(connection));
							break;
						}
					} catch (SQLException ex) {
						Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
					}
				} else if (connection != null) {
					JOptionPane.showMessageDialog(null, "Выберите элемент");
				}
			}
		});
		panel.add(buttonDel);
		
		JButton buttonCh = new JButton("\u0418\u0437\u043C\u0435\u043D\u0438\u0442\u044C");
		buttonCh.setBounds(547, 137, 97, 25);
		buttonCh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() >= 0 && connection != null) {
					int idEl = (table.getSelectedRow());
					IEntity entity;
					IForm form;
					int i;
					try {
						switch ((String) ComboBox.getSelectedItem()) {
						case "Клиент":
							entity = new Client();
							ArrayList<Client> alClient = entity.getTable(connection);
							i = alClient.get(idEl).id;
							form = new ClientForm(i, connection);
							form.setVisible(true);
							table.setModel(entity.TableModel(connection));
							break;
						case "Авто":
							entity = new Car();
							ArrayList<Car> alCar = entity.getTable(connection);
							i = alCar.get(idEl).id;
							form = new CarForm(i, connection);
							form.setVisible(true);
							table.setModel(entity.TableModel(connection));
							break;
						case "Мастер":
							entity = new Master();
							ArrayList<Master> alMaster = entity.getTable(connection);
							i = alMaster.get(idEl).id;
							form = new MasterForm(i, connection);
							form.setVisible(true);
							table.setModel(entity.TableModel(connection));
							break;
						case "Починка":
							entity = new Repair();
							ArrayList<Repair> alRepair = entity.getTable(connection);
							i = alRepair.get(idEl).id;
							form = new RepairForm(i, connection);
							form.setVisible(true);
							table.setModel(entity.TableModel(connection));
							break;
						case "Деталь":
							entity = new Detail();
							ArrayList<Detail> alDetail = entity.getTable(connection);
							i = alDetail.get(idEl).id;
							form = new DetailForm(i, connection);
							form.setVisible(true);
							table.setModel(entity.TableModel(connection));
							break;
						case "Деталь на складе":
							entity = new DetailOnStock();
							ArrayList<DetailOnStock> alDetailOnStock = entity.getTable(connection);
							int idDetail = alDetailOnStock.get(idEl).idDetail;
							int idStock = alDetailOnStock.get(idEl).idStock;
							form = new DetailOnStockForm(idDetail, idStock, connection);
							form.setVisible(true);
							table.setModel(entity.TableModel(connection));
							break;
						case "Склад":
							entity = new Stock();
							ArrayList<Stock> alStock = entity.getTable(connection);
							i = alStock.get(idEl).id;
							form = new StockForm(i, connection);
							form.setVisible(true);
							table.setModel(entity.TableModel(connection));
							break;
						}
					} catch (SQLException ex) {
						Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
					}
				} else if (connection != null) {
					JOptionPane.showMessageDialog(null, "Выберите элемент");
				}
			}
		});
		
		panel.add(buttonCh);
		
	
				
		
		JButton buttonUpd = new JButton("\u041E\u0431\u043D\u043E\u0432\u0438\u0442\u044C");
		buttonUpd.setBounds(547, 175, 97, 25);
		buttonUpd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refresh();
			}
		});
		
		panel.add(buttonUpd);
		
		

		
		}
		private void Disconnect() {
			if (connection != null) {
				connection = null;
				JOptionPane.showMessageDialog(null, "Соеденение закрыто");
			}
		}

		private void refresh() {
			if (connection != null) {
				IEntity entity;
				try {
					switch ((String) ComboBox.getSelectedItem()) {
					case "Клиент":
						entity = new Client();
						table.setModel(entity.TableModel(connection));
						break;
					case "Авто":
						entity = new Car();
						table.setModel(entity.TableModel(connection));
						break;
					case "Мастер":
						entity = new Master();
						table.setModel(entity.TableModel(connection));
						break;
					case "Починка":
						entity = new Repair();
						table.setModel(entity.TableModel(connection));
						break;
					case "Деталь":
						entity = new Detail();
						table.setModel(entity.TableModel(connection));
						break;
					case "Деталь на складе":
						entity = new DetailOnStock();
						table.setModel(entity.TableModel(connection));
						break;
					case "Склад":
						entity = new Stock();
						table.setModel(entity.TableModel(connection));
						break;
					}
				} catch (SQLException ex) {
					Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		
	}
}
