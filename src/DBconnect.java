import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnect {
	
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";
	private static final String CONN = "jdbc:mysql://localhost/gpa"; //gpa = name of database

	public static Connection getConnection() throws SQLException {
		
		/**
		 * **GETCONNECTION method**
		 * Output: Returns the information necessary to connect to MySQL database in try blocks in Courses and Users classes.
		 */
		
		return DriverManager.getConnection(CONN, USERNAME, PASSWORD);
	}
	
}
