package Entities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class Stock implements IEntity {
	public int id;
	public String city;
	
	public Stock(int id, String city) {
		this.id = id;
		this.city = city;
	}
	
	public Stock(String city) {
		this.city = city;
	}
	
	public Stock() {
		
	}
	
	public void addElement(String city, Connection connection) throws SQLException {
		Statement stmt = null;
		stmt = connection.createStatement();
		stmt.executeUpdate("insert into stock values(nextval('stock_id_seq'), '" + city + "');");
	}

	public void removeElement(int id, Connection connection) throws SQLException {
		Statement stmt = null;
		stmt = connection.createStatement();
		stmt.executeUpdate("delete from stock where id = " + id + ";");
	}

	public void refreshElement(int id, String city, Connection connection) throws SQLException {		
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("update stock set city = '" + city + "' where id = " + id + ";");
	}
	
	public DefaultTableModel TableModel(Connection connection) throws SQLException {
		Vector<String> columnNames = null;
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		DefaultTableModel tableModel = new DefaultTableModel();
		columnNames = getTitles();
		for (int i = 0; i < getTable(connection).size(); i++) {
			data.add(getTable(connection).get(i).setData(connection));
		}
		tableModel.setDataVector(data, columnNames);
		return tableModel;
	}
	
	public Vector<String> getTitles() {
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("id");
		columnNames.add("Город");
		return columnNames;
	}
	
	public ArrayList<Stock> getTable(Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select * from stock");
		ArrayList<Stock> res = new ArrayList<>();
		while (rs.next()) {
			res.add(new Stock( (int)rs.getObject(1), rs.getObject(2).toString()));
		}
		return res;
	}
	
	public Vector<Object> setData(Connection connection) throws SQLException {
		Vector<Object> data = new Vector<Object>();
		data.add(id);
		data.add(city);
		return data;

	}

	@Override
	public void removeElement(int firstKey, int secondKey, Connection connection) throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
