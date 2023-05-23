package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;

import javax.swing.table.DefaultTableModel;

public class AbstractDAO<T> {
	protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

	private final Class<T> type;

	@SuppressWarnings("unchecked")
	public AbstractDAO() {
		this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	private String createSelectQuery(String field) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM ");
		sb.append(type.getSimpleName());
		sb.append(" WHERE " + field + " =?");
		return sb.toString();
	}

	private String createUpdate()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append(type.getSimpleName());
		sb.append(" SET ");

		Field[] fields = type.getDeclaredFields();
		for (int i = 0; i < fields.length; i++)
		{
			Field field = fields[i];
			sb.append(field.getName()).append(" = ?");
			if (i != fields.length - 1) {
				sb.append(", ");
			}
		}
		sb.append(" WHERE id = ?");
		return sb.toString();
	}

	private String createSelectAll() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM ");
		sb.append(type.getSimpleName());
		return sb.toString();
	}

	private String createDelete() {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM ");
		sb.append(type.getSimpleName());
		sb.append(" WHERE id = ?");
		return sb.toString();
	}

	public String idCalc(T t){
		String resultString = "";
		try {
			Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "root");
			Statement statement = connection.createStatement();

			String query = "SELECT COUNT(*) AS id FROM ";
			query = query + type.getSimpleName();
			ResultSet resultSet = statement.executeQuery(query);

			int itemCount = 0;
			if (resultSet.next()) {
				itemCount = resultSet.getInt("id");
			}
			itemCount++;
			resultString = resultString + itemCount;

			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultString;
	}


	private String createInsert(T t)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ");
		sb.append(type.getSimpleName());
		sb.append(" (");

		Field[] fields = type.getDeclaredFields();

		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			sb.append(field.getName());
			if (i != fields.length - 1) {
				sb.append(", ");
			}
		}
		sb.append(") VALUES (");

//		String idInsert = idCalc(t);
//		sb.append(idInsert);
//		sb.append(", ");

		for (int i = 0; i < fields.length; i++) {
			sb.append("?");
			if (i != fields.length - 1) {
				sb.append(", ");
			}
		}
		sb.append(")");
		System.out.println(sb.toString());
		return sb.toString();
	}

	public T insert(T t) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createInsert(t);
		try {
			conn = ConnectionFactory.getConnection();

			// Check if the primary key field is present
			Field primaryKeyField = type.getDeclaredField("id");
			primaryKeyField.setAccessible(true);
			Object primaryKeyValue = primaryKeyField.get(t);

			if (primaryKeyValue != null) {
				// Primary key value is not null, check if it already exists in the database
				String checkQuery = "SELECT id FROM " + type.getSimpleName() + " WHERE id = ?";
				statement = conn.prepareStatement(checkQuery);
				statement.setObject(1, primaryKeyValue);
				resultSet = statement.executeQuery();

				if (resultSet.next()) {
					// Primary key value already exists, handle the error or perform appropriate action
					LOGGER.log(Level.WARNING, "Primary key value already exists: " + primaryKeyValue);
					// You can throw an exception or handle the error based on your requirements
					// For example:
					throw new IllegalArgumentException("Primary key value already exists: " + primaryKeyValue);
				}
			}

			// Continue with the insertion if primary key check passed or primary key is null
			statement = conn.prepareStatement(query);

			int index = 1;
			for (Field field : type.getDeclaredFields()) {
				field.setAccessible(true);
				Object value = field.get(t);
				statement.setObject(index, value);
				index++;
			}

			statement.executeUpdate();
			return t;
		} catch (SQLException | IllegalAccessException | NoSuchFieldException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(conn);
		}
		return t;
	}


	public T delete(T t)
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createDelete();
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			Field idField = type.getDeclaredFields()[0];
			idField.setAccessible(true);
			int idToDelete = Integer.parseInt(idField.get(t).toString());
			statement.setInt(1, idToDelete);
			statement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:deleteClient " + e.getMessage());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}

	public List<T> findAll()
	{
		// TODO:
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createSelectAll();
		try {
			conn = ConnectionFactory.getConnection();
			statement = conn.prepareStatement(query);
			resultSet = statement.executeQuery();
			return createObjects(resultSet);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(conn);
		}
		return null;
	}

	public T findById(int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createSelectQuery("ID");
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();

			return createObjects(resultSet).get(0);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}

	private List<T> createObjects(ResultSet resultSet) {
		List<T> list = new ArrayList<T>();
		Constructor[] ctors = type.getDeclaredConstructors();
		Constructor ctor = null;
		for (int i = 0; i < ctors.length; i++) {
			ctor = ctors[i];
			if (ctor.getGenericParameterTypes().length == 0)
				break;
		}
		try {
			while (resultSet.next()) {
				ctor.setAccessible(true);
				T instance = (T)ctor.newInstance();
				for (Field field : type.getDeclaredFields()) {
					String fieldName = field.getName();
					Object value = resultSet.getObject(fieldName);
					//System.out.println(fieldName + " " + value + " ");
					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
					Method method = propertyDescriptor.getWriteMethod();
					//System.out.println(method);
					method.invoke(instance, value);
					//System.out.println(method);
				}
				list.add(instance);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return list;
	}

	public DefaultTableModel makeTable() {
		String[] columnNames = new String[type.getDeclaredFields().length];
		int idx = 0;
		for (Field field : type.getDeclaredFields())
		{
			field.setAccessible(true);
			columnNames[idx] = field.getName();
			idx++;
		}

		List<T> tableEntryList = findAll();
		Object[][] data = new Object[tableEntryList.size()][idx];

		int index = 0;
		for(T t: tableEntryList)
		{
			ArrayList<Object> rowData = new ArrayList<>();
			for(Field field : type.getDeclaredFields()) {
				field.setAccessible(true);
				try {
					Object value = field.get(t);
					rowData.add(value);
				} catch (Exception e) {e.printStackTrace();}
			}
			data[index] = rowData.toArray();
			index++;
		}
		return new DefaultTableModel(data, columnNames);
	}


	public T modifyTable(T t) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createUpdate();
		try {
			conn = ConnectionFactory.getConnection();
			statement = conn.prepareStatement(query);

			int i = 1;
			for (Field field : type.getDeclaredFields())
			{
				field.setAccessible(true);
				statement.setObject(i++, field.get(t));
			}

			Field idField = type.getDeclaredField("id");
			idField.setAccessible(true);
			statement.setInt(i, (Integer)idField.get(t));

			statement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
		} catch (IllegalAccessException | NoSuchFieldException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(conn);
		}
		return t;
	}
}
