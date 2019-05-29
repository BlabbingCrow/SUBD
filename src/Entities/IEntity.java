package Entities;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.TableModel;

public interface IEntity {

	void removeElement(int key, Connection connection) throws SQLException;

	TableModel TableModel(Connection connection) throws SQLException;

	void removeElement(int firstKey, int secondKey, Connection connection) throws SQLException;

	<T> ArrayList<T> getTable(Connection connection) throws SQLException;
}
