package Entities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class Detail implements IEntity {
	public int id;
	public int idRepair;
	public String name;
	public int cost;
	
	public Detail(int id, int idRepair, String name, int cost) {
		this.id = id;
		this.idRepair = idRepair;
		this.name = name;
		this.cost = cost;
	}
	
	public Detail(int idRepair, String name, int cost) {
		this.idRepair = idRepair;
		this.name = name;
		this.cost = cost;
	}
	
	public Detail() {
		
	}
	
	public void addElement(int idRepair, String name, int cost, Connection connection) throws SQLException {
		Statement stmt = null;
		stmt = connection.createStatement();
		stmt.executeUpdate("insert into detail values(nextval('detail_id_seq'), " + idRepair + ", '" + name + "', " + cost + ");");
	}

	public void removeElement(int id, Connection connection) throws SQLException {
		Statement stmt = null;
		stmt = connection.createStatement();
		stmt.executeUpdate("delete from detail where id = " + id + ";");
	}

	public void refreshElement(int id, int idRepair, String name, int cost, Connection connection) throws SQLException {		
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("update detail set \"idRepair\" = " + idRepair + ", name = '" + name + "', cost = " + cost + " where id = " + id + ";");
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
		columnNames.add("Починка");
		columnNames.add("Название");
		columnNames.add("Стоимость");
		return columnNames;
	}
	
	public ArrayList<Detail> getTable(Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select * from detail");
		ArrayList<Detail> res = new ArrayList<>();
		while (rs.next()) {
			res.add(new Detail( (int)rs.getObject(1), (int)rs.getObject(2), rs.getObject(3).toString(), (int)rs.getObject(4)));
		}
		return res;
	}
	
	public Vector<Object> setData(Connection connection) throws SQLException {
		Vector<Object> data = new Vector<Object>();
		data.add(id);
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select id from repair where id = " + idRepair + ";");
		while (rs.next()) {
			data.add(rs.getString("id"));		
		}
		data.add(name);
		data.add(cost);
		return data;

	}

	@Override
	public void removeElement(int firstKey, int secondKey, Connection connection) throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
