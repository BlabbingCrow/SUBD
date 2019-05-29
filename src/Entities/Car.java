package Entities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class Car implements IEntity {
	public int id;
	public String mark;
	public String number;
	public int idClient;
	
	public Car(int id, String mark, String number, int idClient) {
		this.id = id;
		this.mark = mark;
		this.number = number;
		this.idClient = idClient;
	}
	
	public Car(String mark, String number, int idClient) {
		this.mark = mark;
		this.number = number;
		this.idClient = idClient;
	}
	
	public Car() {
		
	}
	
	public Vector<Object> setData(Connection connection) throws SQLException {
		Vector<Object> data = new Vector<Object>();
		data.add(id);
		data.add(mark);
		data.add(number);
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select name from client where id = " + idClient + ";");
		while (rs.next()) {
			data.add(rs.getString("name"));		
		}
		return data;
	}

	public void addElement(String mark, String number, int idClient, Connection connection) throws SQLException {
		Statement stmt = null;
		stmt = connection.createStatement();
		stmt.executeUpdate("insert into car values('" + mark + "', '" + number + "', " + idClient + ", nextval('car_id_seq'));");
	}

	public void removeElement(int id, Connection connection) throws SQLException {
		Statement stmt = null;
		stmt = connection.createStatement();
		stmt.executeUpdate("delete from car where id = " + id + ";");
	}

	public void refreshElement(int id, String mark, String number, int idClient, Connection connection) throws SQLException {		
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("update car set mark = '" + mark + "', number = '" + number + "', \"idClient\" = " + idClient + " where id = " + id + ";");
	}

	public DefaultTableModel TableModel(Connection connection) throws SQLException {
		Vector<String> columnNames = null;
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		DefaultTableModel tableModel = new DefaultTableModel();
		columnNames = getTitles();
		for (int i = 0; i <= getTable(connection).size() - 1; i++) {
			data.add(getTable(connection).get(i).setData(connection));
		}
		tableModel.setDataVector(data, columnNames);
		return tableModel;
	}

	public Vector<String> getTitles() {
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("id");
		columnNames.add("Марка");
		columnNames.add("Гос номер");
		columnNames.add("Клиент");
		return columnNames;
	}

	public ArrayList<Car> getTable(Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select * from car");
		ArrayList<Car> res = new ArrayList<>();
		while (rs.next()) {
			res.add(new Car( (int)rs.getObject(4), rs.getObject(1).toString(), rs.getObject(2).toString(), (int)rs.getObject(3)));
		}
		return res;
	}

	@Override
	public void removeElement(int firstKey, int secondKey, Connection connection) {
		// TODO Auto-generated method stub
		
	}
}
