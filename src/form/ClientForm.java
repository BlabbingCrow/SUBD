package form;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

import Entities.Client;
import Entities.Master;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ClientForm extends JFrame implements IForm {

	public int idSelected;
	private Connection connection = null;
	
	private JPanel panel;
	private JTextField textFieldName;
	private JTextField textFieldSurname;
	private JTextField textFieldPatronymic;

	/**
	 * Launch the application.
	 * @wbp.parser.constructor
	 */
	public ClientForm(Connection connection) {
		initialize();
		this.idSelected = -1;
		this.connection = connection;
	}
	
	public ClientForm(int id, Connection connection)throws SQLException  {
		initialize();
		this.idSelected = id;
		this.connection = connection;
		Client c = new Client();
		ArrayList<Client> client = new ArrayList<>(c.getTable(connection));
		c = null;
		for (int i = 0; i < client.size(); i++) {
			if (id == client.get(i).id) {
				c = client.get(i);
				break;
			}
		}
		textFieldName.setText(c.name);
		textFieldSurname.setText(c.surname);
		textFieldPatronymic.setText(c.patronymic);
	}
	
	private void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		panel = new JPanel();
		setContentPane(panel);
		getContentPane().setLayout(null);
		
		JButton button = new JButton("Отмена");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		button.setBounds(335, 227, 89, 23);
		getContentPane().add(button);
		
		JButton button_1 = new JButton("Сохранить");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Master master = null;
				if (textFieldName.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "ошибка");
				}
				try {
					master = new Master(textFieldName.getText(), textFieldSurname.getText(), textFieldPatronymic.getText() );
					if (idSelected < 0) {
						master.addElement(textFieldName.getText(), textFieldSurname.getText(), textFieldPatronymic.getText(), connection);
					} else {
						master.refreshElement(idSelected,textFieldName.getText(), textFieldSurname.getText(), textFieldPatronymic.getText(), connection);
					}
					setVisible(false);
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
		button_1.setBounds(236, 227, 89, 23);
		getContentPane().add(button_1);
		
		textFieldName = new JTextField();
		textFieldName.setBounds(76, 11, 348, 20);
		getContentPane().add(textFieldName);
		textFieldName.setColumns(10);
		
		textFieldSurname = new JTextField();
		textFieldSurname.setBounds(76, 42, 348, 20);
		getContentPane().add(textFieldSurname);
		textFieldSurname.setColumns(10);
		
		textFieldPatronymic = new JTextField();
		textFieldPatronymic.setBounds(76, 73, 348, 20);
		getContentPane().add(textFieldPatronymic);
		textFieldPatronymic.setColumns(10);
		
		JLabel label = new JLabel("Имя");
		label.setBounds(10, 14, 56, 14);
		getContentPane().add(label);
		
		JLabel label_1 = new JLabel("Фамилия");
		label_1.setBounds(10, 45, 56, 14);
		getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("Отчество");
		label_2.setBounds(10, 76, 56, 14);
		getContentPane().add(label_2);
	}
}
