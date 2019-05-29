package Entities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class Client implements IEntity {
	public int id;
	public String name;
	public String surname;
	public String patronymic;
	
	public Client() {
		
	}
	
	public Client(int id, String name, String surname, String patronymic){
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.patronymic = patronymic;
	}
	
	public Client(String name, String surname, String patronymic){
		this.name = name;
		this.surname = surname;
		this.patronymic = patronymic;
	}
	
	public Vector <Object> setData(Connection connection) throws SQLException{
		Vector<Object> data = new Vector<Object>();
		data.add(id);
		data.add(name);
		data.add(surname);
		data.add(patronymic);
		return data;
	}
	
	public void addElement(String name, String surname, String patronymic, Connection connection) throws SQLException {
		Statement statement = null;
		statement = connection.createStatement();
		statement.executeUpdate("insert into client values( nextval('seq_client'), '" + name + "', '" + surname + "', '" + patronymic + "');");
	}

	public void removeElement(int id, Connection connection) throws SQLException {
		Statement statement = null;
		statement = connection.createStatement();
		statement.executeUpdate("delete from client where id = " + id + ";");
	}
	
	public void refreshElement(int id, String name, String surname, String patronymic, Connection connection) throws SQLException {
		Statement statement = null;
		statement = connection.createStatement();
		statement.executeUpdate("update client set name = '" + name + "', surname = '" + surname + "', patromic = '" + patronymic + "' where id = " + id + ";");
	}
	
	public DefaultTableModel TableModel(Connection connection) throws SQLException {
		Vector<String> columnNames = null;
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		DefaultTableModel tableModel = new DefaultTableModel();
		columnNames = getTitles();
		ArrayList<Client> clients = getTable(connection);
		for (int i = 0; i < clients.size(); i++) {
			data.add(clients.get(i).setData(connection));
		}
		tableModel.setDataVector(data, columnNames);
		return tableModel;
	}

	public Vector<String> getTitles() {
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("id");
		columnNames.add("Имя");
		columnNames.add("Фамилия");
		columnNames.add("Отчество");
		return columnNames;
	}

	public ArrayList<Client> getTable(Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select * from client;");
		ArrayList<Client> res = new ArrayList<>();
		while (rs.next()) {
			res.add(new Client((int) rs.getObject(4), rs.getObject(1).toString(), rs.getObject(2).toString(), rs.getObject(3).toString()));
		}
		return res;
	}

	@Override
	public void removeElement(int firstKey, int secondKey, Connection connection) throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
