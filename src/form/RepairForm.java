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
import Entities.Master;
import Entities.Repair;

public class RepairForm extends JFrame implements IForm {

	private Connection connection = null;
	public int idSelected;
	private JPanel contentPane;

	private JPanel panel;
	private JTextField textFieldCauseOfFailure;
	private JComboBox comboBoxCar;
	private JComboBox comboBoxMaster;
	private ArrayList<Car> car;
	private ArrayList<Master> master;
	private JButton buttonSave;
	private JButton buttonCancel;

	public RepairForm(Connection connection) throws SQLException  {
		initialize();
		this.connection = connection;
		this.idSelected = -1;
		
		Car cu = new Car();
		car = new ArrayList<>(cu.getTable(connection));
		comboBoxCar.removeAllItems();
		for (int i = 0; i < car.size(); i++) {
			comboBoxCar.addItem("" + car.get(i).mark);
		}
		Master m = new Master();
		master = new ArrayList<>(m.getTable(connection));
		comboBoxMaster.removeAllItems();
		for (int i = 0; i < master.size(); i++) {
			comboBoxMaster.addItem("" + master.get(i).name);
		}
	}
	
	public RepairForm(int id, Connection connection) throws SQLException  {
		initialize();
		
		int cid = 0;
		int mid = 0;
		this.connection = connection;
		this.idSelected = id;
		Repair o = new Repair();
		ArrayList<Repair> repair = new ArrayList<>(o.getTable(connection));
		o = null;
		for (int i = 0; i < repair.size(); i++) {
			if (id == repair.get(i).id) {
				o = repair.get(i);
				break;
			}
		}
		
		Car c = new Car();
		car = new ArrayList<>(c.getTable(connection));
		comboBoxCar.removeAllItems();
		for (int i = 0; i < car.size(); i++) {
			comboBoxCar.addItem("" + car.get(i).mark);
			if (o.idCar == car.get(i).id) {
				cid = i;
			}
		}
		Master m = new Master();
		master = new ArrayList<>(m.getTable(connection));
		comboBoxMaster.removeAllItems();
		for (int i = 0; i < master.size(); i++) {
			comboBoxMaster.addItem("" + master.get(i).name);
			if (o.idMaster == master.get(i).id) {
				mid = i;
			}
		}

		textFieldCauseOfFailure.setText(o.causeOfFailure);
		comboBoxCar.setSelectedItem(car.get(cid).mark);
		comboBoxMaster.setSelectedItem(master.get(mid).name);
	}
	
	public RepairForm() {
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
		
		
		textFieldCauseOfFailure = new JTextField();
		textFieldCauseOfFailure.setBounds(121, 10, 116, 22);
		getContentPane().add(textFieldCauseOfFailure);
		textFieldCauseOfFailure.setColumns(10);
		
		
		comboBoxCar = new JComboBox();
		comboBoxCar.setBounds(121, 123, 116, 22);
		getContentPane().add(comboBoxCar);
		
		comboBoxMaster = new JComboBox();
		comboBoxMaster.setBounds(121, 153, 116, 22);
		getContentPane().add(comboBoxMaster);
		
		
		buttonSave = new JButton("\u0421\u043E\u0445\u0440\u0430\u043D\u0438\u0442\u044C");
		buttonSave.setBounds(278, 109, 97, 25);
		buttonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Repair repair = null;
				if (textFieldCauseOfFailure.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "");
				}
				try {
					repair = new Repair(car.get(comboBoxCar.getSelectedIndex()).id, master.get(comboBoxMaster.getSelectedIndex()).id, textFieldCauseOfFailure.getText());
					if (idSelected < 0) {
						repair.addElement(car.get(comboBoxCar.getSelectedIndex()).id, master.get(comboBoxMaster.getSelectedIndex()).id, textFieldCauseOfFailure.getText(), connection);
					} else {
						repair.refreshElement(idSelected, car.get(comboBoxCar.getSelectedIndex()).id, master.get(comboBoxMaster.getSelectedIndex()).id, textFieldCauseOfFailure.getText(), connection);
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
