package dpais.exercise;

import java.sql.*;
import java.sql.SQLException;

public class Database {
	public static String connectionString = "jdbc:hsqldb:file:db-data/myfiledb";
	
	public static Connection con = null;
	
	
	public Database() {
		// establish connection with database
	       try {
	    	   Class.forName("org.hsqldb.jdbc.JDBCDriver");
	    	   con = DriverManager.getConnection(connectionString, "SA", "");
	       } catch (Exception e) {
	          e.printStackTrace();
	       }
	     }
	
	// finish the connection with the database
	public void closeConnection() {
	       if (con == null) return;
	       try {
	       con.close();
	       con = null;
	       } catch(SQLException ex) {
	          ex.printStackTrace();
	       }
	     }
	
	// create a SQL table in the connected database with the requested event info
	public void create() throws SQLException {
	       if (con == null) {
	          throw new SQLException("Connection inexistent");
	          }
	       String createCommand = "CREATE TABLE IF NOT EXISTS eventstable(id VARCHAR(50) NOT NULL, host VARCHAR(50), type VARCHAR(50), duration INT NOT NULL, alert BOOLEAN NOT NULL, PRIMARY KEY (id) );";
	       Statement statement = con.createStatement();
	       statement.execute(createCommand);
	       

	    }
	
	// insert analysed events in to the database
	public void insert(String key, AnalysedEvent value) throws SQLException {
	       if (con == null)
	          throw new SQLException("Connection inexistent");
	       
	       String insertCommand = "INSERT INTO eventstable VALUES ?, ?, ?, ?, ?";;
	       PreparedStatement ps = con.prepareStatement(insertCommand);
	       ps.setString(1, key);
	       ps.setString(2, value.host);
	       ps.setString(3, value.type);
	       ps.setInt(4, value.duration);
	       ps.setBoolean(5, value.alert);
	       ps.execute();

	    }
	
}