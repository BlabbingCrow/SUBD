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
import Entities.Master;
import Entities.Repair;

public class DetailForm extends JFrame implements IForm {

	private Connection connection = null;
	public int idSelected;

	private JPanel panel;
	private JTextField textFieldName;
	private JTextField textField;
	private JComboBox comboBoxRepair;
	private ArrayList<Repair> repair;
	private JButton buttonSave;
	private JButton buttonCancel;

	public DetailForm(Connection connection) throws SQLException  {
		initialize();
		this.connection = connection;
		this.idSelected = -1;
		
		Repair cu = new Repair();
		repair = new ArrayList<>(cu.getTable(connection));
		comboBoxRepair.removeAllItems();
		for (int i = 0; i < repair.size(); i++) {
			comboBoxRepair.addItem("" + repair.get(i).id);
		}
	}
	
	public DetailForm(int id, Connection connection) throws SQLException  {
		initialize();
		
		int rid = 0;
		this.connection = connection;
		this.idSelected = id;
		Detail o = new Detail();
		ArrayList<Detail> detail = new ArrayList<>(o.getTable(connection));
		o = null;
		for (int i = 0; i < detail.size(); i++) {
			if (id == detail.get(i).id) {
				o = detail.get(i);
				break;
			}
		}
		
		Repair c = new Repair();
		repair = new ArrayList<>(c.getTable(connection));
		comboBoxRepair.removeAllItems();
		for (int i = 0; i < repair.size(); i++) {
			comboBoxRepair.addItem("" + repair.get(i).id);
			if (o.idRepair == repair.get(i).id) {
				rid = i;
			}
		}


		textFieldName.setText(o.name);
		textField.setText(o.cost + "");
		comboBoxRepair.setSelectedItem(repair.get(rid).id);
	}
	
	public DetailForm() {
		initialize();
	}

	private void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		panel = new JPanel();
		setContentPane(panel);
		getContentPane().setLayout(null);
		
		JLabel label = new JLabel("Ремонт");
		label.setBounds(12, 13, 90, 16);
		getContentPane().add(label);
		
		JLabel label_1 = new JLabel("Название");
		label_1.setBounds(12, 46, 90, 16);
		getContentPane().add(label_1);
		
		JLabel label_3 = new JLabel("Стоимость");
		label_3.setBounds(12, 79, 90, 16);
		getContentPane().add(label_3);
		
		
		textFieldName = new JTextField();
		textFieldName.setBounds(121, 43, 116, 22);
		getContentPane().add(textFieldName);
		textFieldName.setColumns(10);
		
		
		comboBoxRepair = new JComboBox();
		comboBoxRepair.setBounds(121, 10, 116, 22);
		getContentPane().add(comboBoxRepair);
		
		
		
		buttonSave = new JButton("\u0421\u043E\u0445\u0440\u0430\u043D\u0438\u0442\u044C");
		buttonSave.setBounds(278, 109, 97, 25);
		buttonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Detail detail = null;
				if (textFieldName.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "");
				}
				try {
					detail = new Detail(repair.get(comboBoxRepair.getSelectedIndex()).id, textFieldName.getText(), Integer.parseInt(textField.getText()));
					if (idSelected < 0) {
						detail.addElement(repair.get(comboBoxRepair.getSelectedIndex()).id, textFieldName.getText(), Integer.parseInt(textField.getText()), connection);
					} else {
						detail.refreshElement(idSelected, repair.get(comboBoxRepair.getSelectedIndex()).id, textFieldName.getText(), Integer.parseInt(""), connection);
					}
					setVisible(false);
				} catch (SQLException ex) {
					Logger.getLogger(CarForm.class.getName()).log(Level.SEVERE, null, ex);
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
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(121, 76, 116, 22);
		panel.add(textField);
	}
}
