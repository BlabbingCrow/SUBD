package Entities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class DetailOnStock implements IEntity {
	public int idDetail;
	public int idStock;
	public int number;
	
	public DetailOnStock(int idDetail, int idStock, int number) {
		this.idDetail = idDetail;
		this.idStock = idStock;
		this.number = number;
	}
	

	
	public DetailOnStock() {
		
	}
	
	public void addElement(int idDetail, int idStock, int number, Connection connection) throws SQLException {
		Statement stmt = null;
		stmt = connection.createStatement();
		stmt.executeUpdate("insert into detailonstock values(" + idDetail + ", " + idStock + ", " + number + ");");
	}

	public void removeElement(int idDetail, int idStock, Connection connection) throws SQLException {
		Statement stmt = null;
		stmt = connection.createStatement();
		stmt.executeUpdate("delete from detailonstock where \"idDetail\" = " + idDetail + "and \"idStock\" = " + idStock + ";");
	}

	public void refreshElement(int oldIdDetail, int oldIdStock, int idDetail, int idStock, int number, Connection connection) throws SQLException {		
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("update detailonstock set \"idDetail\" = " + idDetail + ", \"idStock\" = " + idStock + ", number = " + number + " where \"idDetail\" = " + oldIdDetail + "and \"idStock\" = " + oldIdStock +  ";");
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
		columnNames.add("Деталь");
		columnNames.add("Склад");
		columnNames.add("Количество");
		return columnNames;
	}
	
	public ArrayList<DetailOnStock> getTable(Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select * from detailonstock");
		ArrayList<DetailOnStock> res = new ArrayList<>();
		while (rs.next()) {
			res.add(new DetailOnStock( (int)rs.getObject(1), (int)rs.getObject(2), (int)rs.getObject(3)));
		}
		return res;
	}
	
	public Vector<Object> setData(Connection connection) throws SQLException {
		Vector<Object> data = new Vector<Object>();
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select name from detail where id = " + idDetail + ";");
		while (rs.next()) {
			data.add(rs.getString("name"));		
		}
		rs = statement.executeQuery("select city from stock where id = " + idStock + ";");
		while (rs.next()) {
			data.add(rs.getString("city"));		
		}
		data.add(number);
		return data;
	}



	@Override
	public void removeElement(int key, Connection connection) throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
