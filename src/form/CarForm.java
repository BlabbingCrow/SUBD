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
import Entities.Client;

public class CarForm extends JFrame implements IForm {

	private Connection connection = null;
	public int idSelected;
	private JPanel contentPane;

	private JPanel panel;
	private JTextField textFieldMark;
	private JTextField textFieldNumber;
	private JComboBox comboBoxClient;
	private ArrayList<Client> client;
	private JButton buttonSave;
	private JButton buttonCancel;

	public CarForm(Connection connection) throws SQLException  {
		initialize();
		this.connection = connection;
		this.idSelected = -1;
		
		Client cu = new Client();
		client = new ArrayList<>(cu.getTable(connection));
		comboBoxClient.removeAllItems();
		for (int i = 0; i < client.size(); i++) {
			comboBoxClient.addItem("" + client.get(i).name);
		}
	}
	
	public CarForm(int id, Connection connection) throws SQLException  {
		initialize();
		
		int cid = 0;
		this.connection = connection;
		this.idSelected = id;
		Car o = new Car();
		ArrayList<Car> car = new ArrayList<>(o.getTable(connection));
		o = null;
		for (int i = 0; i < car.size(); i++) {
			if (id == car.get(i).id) {
				o = car.get(i);
				break;
			}
		}
		
		Client c = new Client();
		client = new ArrayList<>(c.getTable(connection));
		comboBoxClient.removeAllItems();
		for (int i = 0; i < client.size(); i++) {
			comboBoxClient.addItem("" + client.get(i).name);
			if (o.idClient == client.get(i).id) {
				cid = i;
			}
		}

		textFieldMark.setText(o.mark);
		textFieldNumber.setText(o.number);
		comboBoxClient.setSelectedItem(client.get(cid).name);
	}
	
	public CarForm() {
		initialize();
	}

	private void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		panel = new JPanel();
		setContentPane(panel);
		getContentPane().setLayout(null);
		
		JLabel label = new JLabel("Марка авто");
		label.setBounds(12, 13, 90, 16);
		getContentPane().add(label);
		
		JLabel label_1 = new JLabel("Номер авто");
		label_1.setBounds(12, 49, 90, 16);
		getContentPane().add(label_1);
		
		JLabel label_3 = new JLabel("Владелец авто");
		label_3.setBounds(12, 126, 90, 16);
		getContentPane().add(label_3);
		
		
		textFieldMark = new JTextField();
		textFieldMark.setBounds(121, 10, 116, 22);
		getContentPane().add(textFieldMark);
		textFieldMark.setColumns(10);
		
		textFieldNumber = new JTextField();
		textFieldNumber.setBounds(121, 46, 116, 22);
		getContentPane().add(textFieldNumber);
		textFieldNumber.setColumns(10);
		
		
		comboBoxClient = new JComboBox();
		comboBoxClient.setBounds(121, 123, 116, 22);
		getContentPane().add(comboBoxClient);
		
		
		buttonSave = new JButton("\u0421\u043E\u0445\u0440\u0430\u043D\u0438\u0442\u044C");
		buttonSave.setBounds(278, 109, 97, 25);
		buttonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Car car = null;
				if (textFieldMark.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "");
				}
				try {
					car = new Car(textFieldMark.getText(), textFieldNumber.getText(), comboBoxClient.getSelectedIndex());
					if (idSelected < 0) {
						car.addElement(textFieldMark.getText(), textFieldNumber.getText(), client.get(comboBoxClient.getSelectedIndex()).id, connection);
					} else {
						car.refreshElement(idSelected, textFieldMark.getText(), textFieldNumber.getText(), client.get(comboBoxClient.getSelectedIndex()).id, connection);
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
