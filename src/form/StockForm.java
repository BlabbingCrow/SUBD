package form;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Entities.Client;
import Entities.Master;
import Entities.Repair;
import Entities.Stock;
import javax.swing.JPanel;

public class StockForm extends JFrame implements IForm {

	public int idSelected;
	private Connection connection = null;
	
	private JPanel panel;
	private JFrame frame;
	private JTextField textFieldCity;
	
	private JButton buttonSave;
	private JButton buttonCancel;


	public StockForm(Connection connection) {
		initialize();
		this.idSelected = -1;
		this.connection = connection;
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public StockForm(int id, Connection connection)throws SQLException  {
		initialize();
		this.idSelected = id;
		this.connection = connection;
		Stock c = new Stock();
		ArrayList<Stock> stock = new ArrayList<>(c.getTable(connection));
		c = null;
		for (int i = 0; i < stock.size(); i++) {
			if (id == stock.get(i).id) {
				c = stock.get(i);
				break;
			}
		}
		
		textFieldCity.setText(c.city);
	}
	
	public StockForm() {
		initialize();
	}
	
	private void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		panel = new JPanel();
		setContentPane(panel);
		getContentPane().setLayout(null);
		
		JLabel label = new JLabel("Город");
		label.setBounds(12, 13, 90, 16);
		getContentPane().add(label);
		
		
		
		textFieldCity = new JTextField();
		textFieldCity.setBounds(121, 10, 116, 22);
		getContentPane().add(textFieldCity);
		textFieldCity.setColumns(10);
		

		
		
		buttonSave = new JButton("\u0421\u043E\u0445\u0440\u0430\u043D\u0438\u0442\u044C");
		buttonSave.setBounds(278, 109, 97, 25);
		buttonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Stock stock = null;
				if (textFieldCity.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "");
				}
				try {
					stock = new Stock(textFieldCity.getText());
					if (idSelected < 0) {
						stock.addElement(textFieldCity.getText(), connection);
					} else {
						stock.refreshElement(idSelected, textFieldCity.getText(), connection);
					}
					setVisible(false);
				} catch (SQLException ex) {
					Logger.getLogger(CarForm.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
		});

		getContentPane().add(buttonSave);
		
		buttonCancel = new JButton("\u041E\u0442\u043C\u0435\u043D\u0430");
		buttonCancel.setBounds(278, 158, 97, 25);
		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});

		getContentPane().add(buttonCancel);
	}
}
