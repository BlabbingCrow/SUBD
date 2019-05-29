package form;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import Entities.Master;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class MasterForm extends JFrame implements IForm {
	
	public int idSelected;
	private Connection connection = null;

	private JPanel panel;
	private JTextField textFieldName;
	private JTextField textFieldSurname;
	private JTextField textFieldPatronymic;

	/**
	 * Launch the application.
	 */
	public MasterForm(Connection connection) {
		initialize();
		this.idSelected = -1;
		this.connection = connection;
	}
	
	public MasterForm(int id, Connection connection)throws SQLException  {
		initialize();
		this.idSelected = id;
		this.connection = connection;
		Master m = new Master();
		ArrayList<Master> master = new ArrayList<>(m.getTable(connection));
		m = null;
		for (int i = 0; i < master.size(); i++) {
			if (id == master.get(i).id) {
				m = master.get(i);
				break;
			}
		}
		textFieldName.setText(m.name);
		textFieldSurname.setText(m.surname);
		textFieldPatronymic.setText(m.patronymic);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		panel = new JPanel();
		setContentPane(panel);
		getContentPane().setLayout(null);
		
		textFieldName = new JTextField();
		textFieldName.setBounds(143, 46, 116, 22);
		panel.add(textFieldName);
		textFieldName.setColumns(10);
		
		textFieldSurname = new JTextField();
		textFieldSurname.setBounds(143, 81, 116, 22);
		panel.add(textFieldSurname);
		textFieldSurname.setColumns(10);
		
		textFieldPatronymic = new JTextField();
		textFieldPatronymic.setBounds(143, 116, 116, 22);
		panel.add(textFieldPatronymic);
		textFieldPatronymic.setColumns(10);
		
		JButton buttonSave = new JButton("Сохранить");
		buttonSave.setBounds(12, 171, 97, 25);
		buttonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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

		panel.add(buttonSave);
		
		JButton buttonCancel = new JButton("Отмена");
		buttonCancel.setBounds(143, 171, 97, 25);
		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});

		panel.add(buttonCancel);
		
		JLabel label = new JLabel("Имя");
		label.setBounds(12, 49, 84, 16);
		panel.add(label);
		
		JLabel label_1 = new JLabel("Фамилия");
		label_1.setBounds(12, 84, 84, 16);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("Отчество");
		label_2.setBounds(12, 113, 84, 16);
		panel.add(label_2);
	}
}
