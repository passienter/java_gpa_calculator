import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class Users {
	
	public static void addUsernameDB(String newUsername) throws SQLException {
		
		/**
		 * **ADDUSERNAMEDB method**
		 * Prepare: We prepare a String called SQL to store an insert query with a placeholder for the username.
		 * Input: None. Input is done in the MainGPA method.
		 * Process: Try block with PreparedStatement. We add a username to the db table.
		 * Output: Nothing.
		 */
		
		String sql = "INSERT INTO db(username) VALUES(?)";
			
			try (
					Connection conn = DBconnect.getConnection();
					PreparedStatement stmt = conn.prepareStatement(sql);
					)
				{
					stmt.setString(1, newUsername);
					stmt.execute();
				}
	
	}
	
	public static int checkUsernameDB(String newUsername) throws SQLException {
		
		/**
		 * **CHECKUSERNAMEDB method**
		 * Prepare: We prepare an integer usernameExists with value 2 (neutral position) and a temporary username holder.
		 * Input: None.
		 * Process: Try block with ResultSet. We check every username. When we detect one that is the same as the one the user inputted, then usernameExists gets the value 1 (true) and the username doesn't get created in the db.
		 * Output: We return the value of usernameExists which will determine whether the user is authorized to make the account or not.
		 */
		
		int usernameExists = 2; //I used an integer for usernameExists for debugging purposes so that I had 3 "settings" instead of just 2 with a boolean.
		String tempUsername = newUsername;
		String tempHolder = "";
		
		try (
				Connection con = DBconnect.getConnection();
				Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery("SELECT username FROM db");
				)
			{
				while (rs.next()) {
					
					StringBuffer buffer = new StringBuffer();
					
					buffer.append(rs.getString("username"));
					tempHolder = buffer.toString();
					
					if (tempHolder.equals(tempUsername)) {
						
						usernameExists = 1;
					
					}
					
				}
				
				return usernameExists;
			}
	
	}
	
	public static void deleteCourses(String username) throws SQLException {
		
		/**
		 * **DELETECOURSES method**
		 * This method is in relation to deleting an account where all courses also need to be deleted (if any exist).
		 * Prepare: We prepare a String called SQL to store a delete query with a placeholder for the username.
		 * Input: None.
		 * Process: Try block with PreparedStatement. We set the username to fill the placeholder and then we execute to delete all courses where the username is that of the logged-in user.
		 * Output: None.
		 */
		
		String sql = "DELETE FROM courses WHERE (username_entry = ?)";
		
		try (
				Connection conn = DBconnect.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				)
		{
			stmt.setString(1, username);
			stmt.execute();
		}
	
	}
	
	public static void deleteUsername(String username) throws SQLException {
		
		/**
		 * **DELETEUSERNAME method**
		 * Prepare: We prepare a String called SQL with a delete query with a placeholder for the username.
		 * Input: None.
		 * Process: Try block with PreparedStatement. We fill in the placeholder with the corresponding username. Then we execute to delete the corresponding row.
		 * Output: None.
		 */
		
		String sql = "DELETE FROM db WHERE (username = ?)";
		
			try (
					Connection conn = DBconnect.getConnection();
					PreparedStatement stmt = conn.prepareStatement(sql);
					)
			{
				stmt.setString(1, username);
				stmt.execute();
			}
	
	}
	
}