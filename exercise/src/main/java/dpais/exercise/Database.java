package dpais.exercise;

import java.sql.*;

public class Database {
	public static String connectionString = "jdbc:hsqldb:file:db-data/myfiledb";
	
	public static Connection con = null;
	
	
	public Database() {
	       try {
	    	   Class.forName("org.hsqldb.jdbc.JDBCDriver");
	    	   con = DriverManager.getConnection(connectionString, "SA", "");
	       } catch (Exception ex) {
	          ex.printStackTrace();
	       }
	     }
	
	public void closeConnection() {
	       if (con == null) return;
	       try {
	       con.close();
	       con = null;
	       } catch(SQLException ex) {
	          ex.printStackTrace();
	       }
	     }
	
	public void create() throws SQLException {
	       if (con == null) {
	          throw new SQLException("Connection inexistent");
	          }
	       String createCommand = "CREATE TABLE IF NOT EXISTS eventstable(id VARCHAR(50) NOT NULL, host VARCHAR(50), type VARCHAR(50), duration INT NOT NULL, alert BOOLEAN NOT NULL, PRIMARY KEY (id) );";
	       Statement statement = con.createStatement();
	       statement.execute(createCommand);
	       

	    }
	
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