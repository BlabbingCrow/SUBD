package Entities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class Repair implements IEntity {
	public int id;
	public int idCar;
	public int idMaster;
	public String causeOfFailure;
	
	public Repair(int id, int idCar, int idMaster, String causeOfFailure) {
		this.id = id;
		this.idCar = idCar;
		this.idMaster = idMaster;
		this.causeOfFailure = causeOfFailure;
	}
	
	public Repair(int idCar, int idMaster, String causeOfFailure) {
		this.idCar = idCar;
		this.idMaster = idMaster;
		this.causeOfFailure = causeOfFailure;
	}
	
	public Repair() {
		
	}
	
	public void addElement(int idCar, int idMaster, String causeOfFailure, Connection connection) throws SQLException {
		Statement stmt = null;
		stmt = connection.createStatement();
		stmt.executeUpdate("insert into repair values(nextval('repair_id_seq'), " + idCar + ", " + idMaster + ", '" + causeOfFailure + "');");
	}

	public void removeElement(int id, Connection connection) throws SQLException {
		Statement stmt = null;
		stmt = connection.createStatement();
		stmt.executeUpdate("delete from repair where id = " + id + ";");
	}

	public void refreshElement(int id, int idCar, int idMaster, String causeOfFailure, Connection connection) throws SQLException {		
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("update repair set \"idCar\" = " + idCar + ", \"idMaster\" = " + idMaster + ", \"causeOfFailure\" = '" + causeOfFailure + "' where id = " + id + ";");
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
		columnNames.add("Машина");
		columnNames.add("Мастер");
		columnNames.add("Причина поломки");
		return columnNames;
	}
	
	public ArrayList<Repair> getTable(Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select * from repair");
		ArrayList<Repair> res = new ArrayList<>();
		while (rs.next()) {
			res.add(new Repair( (int)rs.getObject(1), (int)rs.getObject(2), (int)rs.getObject(3), rs.getObject(4).toString()));
		}
		return res;
	}
	
	public Vector<Object> setData(Connection connection) throws SQLException {
		Vector<Object> data = new Vector<Object>();
		data.add(id);
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select mark from car where id = " + idCar + ";");
		while (rs.next()) {
			data.add(rs.getString("mark"));		
		}
		rs = statement.executeQuery("select name from master where id = " + idMaster + ";");
		while (rs.next()) {
			data.add(rs.getString("name"));		
		}
		data.add(causeOfFailure);
		return data;

	}

	@Override
	public void removeElement(int firstKey, int secondKey, Connection connection) throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
