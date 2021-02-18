import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.sql.PreparedStatement;

public class Courses {
	
	public static void addCourse(String username, String course, int credits, String grade) throws SQLException {
		
		/**
		 * **ADDCOURSE method**
		 * Prepare: We prepare a String called SQL to store an Insert query with place holders to add a course.
		 * Input: None. Input is done in Menu method.
		 * Process: Try block with PreparedStatment. We fill the place holders with stmt.set*();
		 * Output: We SOP that the user added the course.
		 */
		
		String sql = "INSERT INTO courses(username_entry, course_name, credit_hr, letter_grade) VALUES(?, ?, ?, ?)";
		
		try (
				Connection conn = DBconnect.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				)
			{
				stmt.setString(1, username);
				stmt.setString(2, course.toUpperCase());
				stmt.setInt(3, credits);
				stmt.setString(4, grade);
				stmt.execute();
				
				System.out.println("You successfully added the course.");
			}
	
	}
	
	public static boolean checkCourseExistance(String username, String editThisCourse) throws SQLException {
		
		/**
		 * **CHECKCOURSEEXISTANCE method**
		 * Prepare: We prepare a String called SQL to store a read query.
		 * Input: None.
		 * Process: Try block with ResultSet. We go through every row and check with username and course_name condition until we find the course we want.
		 * Output: We return true if the course sought after exists, otherwise false.
		 */
		
		String sql = "SELECT * FROM courses";
			
			try (
					Connection con = DBconnect.getConnection();
					Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ResultSet rs = stmt.executeQuery(sql);
					)
				{
					rs.beforeFirst();
					boolean exists = false;
					
					while (rs.next()) {
						
						if (rs.getString("username_entry").equals(username) && rs.getString("course_name").equals(editThisCourse.toUpperCase())) {
							//course exists
							exists = true;
						}
					
					}
					
					if (exists) {
						return true;
					}
					else return false; //course doesn't exist
				}
	
	}
	
	public static void deleteCourse(String username, String courseToDelete) throws SQLException {
		
		/**
		 * **DELETECOURSE method**
		 * Prepare: We prepare a String called SQL to store a read query.
		 * Input: None. Input is done in Menu method.
		 * Process: Try block with ResultSet. We check every course until we find the one we want that the user requested to be deleted. Then we delete this row by getting the id first.
		 * Output: We SOP that the user deleted the course.
		 */
		
		String sql = "SELECT id, username_entry, course_name FROM courses";
		
		try (
				Connection con = DBconnect.getConnection();
				Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet rs = stmt.executeQuery(sql);
				)
			{
				rs.beforeFirst();
				boolean exists = false;
				
				while (rs.next()) {
					
					rs.getInt("id");
					
					if (rs.getString("username_entry").equals(username) && rs.getString("course_name").equals(courseToDelete.toUpperCase())) {
						
						rs.deleteRow();
						System.out.println("\nYou successfully deleted this course!");
						exists = true;
					}
				}
				
				if (!exists) {
					
					System.out.println("The course you entered doesn\'t exist.");
				
				}
			}
	
	}
	
	public static void updateCourse(String username, String courseName, int creditHours, String letterGrade, String editThisCourse) throws SQLException {
		
		/**
		 * **UPDATECOURSE method**
		 * Prepare: We prepare a String called SQL to store a read query.
		 * Input: None. Input is done in Menu method.
		 * Process: Try block with ResultSet set to be updatable. We check every course until we find the one we want that the user requested to be edited. Then we update this row by updating course_name, credit_hr and letter_grade.
		 * Output: We SOP that the user edited the course.
		 */
		
		String sql = "SELECT * FROM courses";
			
			try (
					Connection con = DBconnect.getConnection();
					Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
					ResultSet rs = stmt.executeQuery(sql);
					)
				{
					rs.beforeFirst();
					
					while (rs.next()) {
						
						rs.getInt("id");
						
						if (rs.getString("username_entry").equals(username) && rs.getString("course_name").equals(editThisCourse.toUpperCase())) {
							
							if (!(courseName.equals(""))) {
								rs.updateString("course_name", courseName.toUpperCase());
							}
							
							if (creditHours != 0) {
								rs.updateInt("credit_hr", creditHours);
							}
							
							if (!(letterGrade.equals(""))) {
								rs.updateString("letter_grade", letterGrade);
							}
							
							rs.updateRow(); //looks for 'id'
							
							System.out.println("You successfully updated this course!");
						
						}
					
					}
				}
	
	}
	
	public static void viewCourses(String username) throws SQLException {
		
		/**
		 * **VIEWCOURSES method**
		 * Prepare: We prepare a String called SQL to store a read query where the condition is the username (we only want to list courses from the logged-in user).
		 * Input: None.
		 * Process: Try block with ResultSet. We go through every row and append them into a StringBuffer.
		 * Output: We SOP the StringBuffer to list all courses.
		 */
		
		String sql = "SELECT * FROM courses WHERE username_entry = '" + username + "'";
		ResultSet rs = null;
		
		try (
				Connection conn = DBconnect.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				)
			{
				rs = stmt.executeQuery();
				
				if (rs.next()) {
					
					System.out.println("Here are your courses:");
				
				}
				
				else {
					
					System.out.println("You haven\'t entered any courses yet.");
				
				}
				
				rs.beforeFirst();
				
				int i = 1;
				
				while(rs.next()) {
					
					StringBuffer buffer = new StringBuffer();
					
					buffer.append(i + ". Course name: " + rs.getString("course_name") + "\tCredit Hour: " + rs.getInt("credit_hr") + "\tLetter Grade: " + rs.getString("letter_grade"));
					
					System.out.println(buffer.toString());
					
					i++;
				
				}
			}
		
	}

	public static void predictedGPA(String username) throws SQLException {
		
		/**
		 * **PREDICTEDGPA method**
		 * Prepare: We prepare a String called SQL to store a read query where the username is from the logged-in user.
		 * Input: None.
		 * Process: Try block with PreparedStatement. We check every course and in the meanwhile we accumulate data such as total credit hours and quality points.
		 * 			We also convert the letter grade to numeric ones for calculation purposes. Then at the end we make one big calculation that contains the GPA.
		 * Output: We SOP the GPA that has been calculated.
		 */
		
		String sql = "SELECT * FROM courses WHERE username_entry = '" + username + "'";
		ResultSet rs = null;
		
		try (
				Connection conn = DBconnect.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				)
			{
				rs = stmt.executeQuery();
				
				if (!(rs.next())) {
					
					System.out.println("You haven\'t entered any courses yet.");
				
				}
				
				else {
					
					System.out.println("Here are your courses:");
				
				}
				
				rs.beforeFirst();
				
				int creditHrs = 0; //total number of credit hours
				double qualityPts = 0; //quality points
				String lg = ""; //temporary letter grade holder
				double lgNumeric = 0; //letter grade in numeric value
				
				while(rs.next()) {
					
					creditHrs += rs.getInt("credit_hr");
					
					lg = rs.getString("letter_grade");
					
					if(lg.equals("F")) {
						lgNumeric = 0;
					}
					if(lg.equals("D")) {
						lgNumeric = 1;
					}
					if(lg.equals("D+")) {
						lgNumeric = 1.33;
					}
					if(lg.equals("C-")) {
						lgNumeric = 1.67;
					}
					if(lg.equals("C")) {
						lgNumeric = 2;
					}
					if(lg.equals("C+")) {
						lgNumeric = 2.33;
					}
					if(lg.equals("B-")) {
						lgNumeric = 2.67;
					}
					if(lg.equals("B")) {
						lgNumeric = 3;
					}
					if(lg.equals("B+")) {
						lgNumeric = 3.33;
					}
					if(lg.equals("A-")) {
						lgNumeric = 3.67;
					}
					if(lg.equals("A")) {
						lgNumeric = 4;
					}
					
					qualityPts += lgNumeric * rs.getInt("credit_hr");
					
				}
				
				double predictedGPA = qualityPts / creditHrs;
				DecimalFormat df = new DecimalFormat("#.##");
				System.out.println("Your predicted GPA is: " + df.format(predictedGPA));
			}
	
	}
	
}