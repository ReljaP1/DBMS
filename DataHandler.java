package individualProjectDBMS;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DataHandler {
	private Connection conn;
	// Azure SQL connection credentials
	private String server = "popo0002-sql-server.database.windows.net";
	private String database = "cs-dsa-4513-sql-db";
	private String username = "popo0002";
	private String password = "*****";
	// Resulting connection string
	final private String url = String.format(
			"jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;"
					+ "trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;",
					server, database, username, password);

	// Initialize and save the database connection
	private void getDBConnection() throws SQLException {
		if (conn != null) {
			return;
		}
		this.conn = DriverManager.getConnection(url);
	}

	// Return the result of selecting everything from the movie_night table
	public ResultSet GetCustomersByCategoryRange(int min, int max) throws SQLException {
		getDBConnection();
		final String sqlQuery = "    SELECT name, address, category"
				+ "    FROM Customer"
				+ "    WHERE category BETWEEN (?) AND (?)"
				+ "    ORDER BY name;";
		final PreparedStatement stmt = conn.prepareStatement(sqlQuery);
		stmt.setInt(1, min);
		stmt.setInt(2, max);
		return stmt.executeQuery();
	}

	// Inserts a record into the movie_night table with the given attribute values
	public boolean InsertNewCustomer(String name, String address, int category) throws SQLException {
		getDBConnection(); // Prepare the database connection
		// Prepare the SQL statement
		final String sqlQuery = "INSERT INTO Customer (name, address, category)"
				+ "VALUES (?, ?, ?)";
		
		final PreparedStatement stmt = conn.prepareStatement(sqlQuery);
		// Replace the '?' in the above statement with the given attribute values
		stmt.setString(1, name);
		stmt.setString(2, address);
		stmt.setInt(3, category);
		// Execute the query, if only one record is updated, then we indicate success by
		// returning true
		return stmt.executeUpdate() == 1;
	}
}
