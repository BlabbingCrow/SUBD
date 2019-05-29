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
import javax.swing.JPanel;
import javax.swing.JTextField;

import Entities.Car;
import Entities.Detail;
import Entities.DetailOnStock;
import Entities.Master;
import Entities.Repair;
import Entities.Stock;

public class DetailOnStockForm extends JFrame implements IForm {
	private Connection connection = null;
	public int selectedIdDetail;
	public int selectedIdStock;
	private JPanel contentPane;

	private JPanel panel;
	private JTextField textFieldNumber;
	private JComboBox comboBoxDetail;
	private JComboBox comboBoxStock;
	private ArrayList<Detail> detail;
	private ArrayList<Stock> stock;
	private JButton buttonSave;
	private JButton buttonCancel;

	public DetailOnStockForm(Connection connection) throws SQLException  {
		initialize();
		this.connection = connection;
		this.selectedIdDetail = -1;
		this.selectedIdStock = -1;
		
		Detail cu = new Detail();
		detail = new ArrayList<>(cu.getTable(connection));
		comboBoxDetail.removeAllItems();
		for (int i = 0; i < detail.size(); i++) {
			comboBoxDetail.addItem("" + detail.get(i).name);
		}
		Stock m = new Stock();
		stock = new ArrayList<>(m.getTable(connection));
		comboBoxStock.removeAllItems();
		for (int i = 0; i < stock.size(); i++) {
			comboBoxStock.addItem("" + stock.get(i).city);
		}
	}
	
	public DetailOnStockForm(int idDetail, int idStock, Connection connection) throws SQLException  {
		initialize();
		
		int cid = 0;
		int mid = 0;
		this.connection = connection;
		this.selectedIdDetail = idDetail;
		this.selectedIdStock = idStock;
		DetailOnStock o = new DetailOnStock();
		ArrayList<DetailOnStock> detailOnStock = new ArrayList<>(o.getTable(connection));
		o = null;
		for (int i = 0; i < detailOnStock.size(); i++) {
			if (selectedIdDetail == detailOnStock.get(i).idDetail && selectedIdStock == detailOnStock.get(i).idStock) {
				o = detailOnStock.get(i);
				break;
			}
		}
		
		Detail c = new Detail();
		detail = new ArrayList<>(c.getTable(connection));
		comboBoxDetail.removeAllItems();
		for (int i = 0; i < detail.size(); i++) {
			comboBoxDetail.addItem("" + detail.get(i).name);
			if (o.idDetail == detail.get(i).id) {
				cid = i;
			}
		}
		Stock m = new Stock();
		stock = new ArrayList<>(m.getTable(connection));
		comboBoxStock.removeAllItems();
		for (int i = 0; i < stock.size(); i++) {
			comboBoxStock.addItem("" + stock.get(i).city);
			if (o.idStock == stock.get(i).id) {
				mid = i;
			}
		}

		textFieldNumber.setText(o.number + "");
		comboBoxDetail.setSelectedItem(detail.get(cid).name);
		comboBoxStock.setSelectedItem(stock.get(mid).city);
	}
	
	public DetailOnStockForm() {
		initialize();
	}

	private void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		panel = new JPanel();
		setContentPane(panel);
		getContentPane().setLayout(null);
		
		JLabel label = new JLabel("Причина поломки");
		label.setBounds(12, 13, 90, 16);
		getContentPane().add(label);
		
		JLabel label_1 = new JLabel("Марка авто");
		label_1.setBounds(12, 129, 90, 16);
		getContentPane().add(label_1);
		
		JLabel label_3 = new JLabel("Мастер");
		label_3.setBounds(12, 156, 90, 16);
		getContentPane().add(label_3);
		
		
		textFieldNumber = new JTextField();
		textFieldNumber.setBounds(121, 10, 116, 22);
		getContentPane().add(textFieldNumber);
		textFieldNumber.setColumns(10);
		
		
		comboBoxDetail = new JComboBox();
		comboBoxDetail.setBounds(121, 123, 116, 22);
		getContentPane().add(comboBoxDetail);
		
		comboBoxStock = new JComboBox();
		comboBoxStock.setBounds(121, 153, 116, 22);
		getContentPane().add(comboBoxStock);
		
		
		buttonSave = new JButton("\u0421\u043E\u0445\u0440\u0430\u043D\u0438\u0442\u044C");
		buttonSave.setBounds(278, 109, 97, 25);
		buttonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DetailOnStock detailOnStock = null;
				if (textFieldNumber.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "");
				}
				try {
					detailOnStock = new DetailOnStock(detail.get(comboBoxDetail.getSelectedIndex()).id, stock.get(comboBoxStock.getSelectedIndex()).id, Integer.parseInt(textFieldNumber.getText()));
					if (selectedIdDetail < 0) {
						detailOnStock.addElement(detail.get(comboBoxDetail.getSelectedIndex()).id, stock.get(comboBoxStock.getSelectedIndex()).id, Integer.parseInt(textFieldNumber.getText()), connection);
					} else {
						detailOnStock.refreshElement(selectedIdDetail, selectedIdStock, detail.get(comboBoxDetail.getSelectedIndex()).id, stock.get(comboBoxStock.getSelectedIndex()).id, Integer.parseInt(textFieldNumber.getText()), connection);
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
