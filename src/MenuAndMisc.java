import java.sql.SQLException;
import java.util.Scanner;

public class MenuAndMisc {
		
	public static void Menu(String username) throws SQLException {
		
		/**
		 * **MENU method**
		 * Prepare: An input variable.
		 * Input: We let the user choose an item from the menu by entering a number between 1 and 7.
		 * Process: We check if the user entered a number between 1 and 7. If he did, then we go through with the item he chose. Otherwise he has to try again.
		 * Output: Once the user inputs a valid number, he will proceed with his selection. The options are in themselves self-explanatory and SOPs explain what is going on.
		 */
		
		int input;
		
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("Menu: (enter the number option you want to select");
		System.out.println("1. Display predicted GPA");
		System.out.println("2. View courses");
		System.out.println("3. Add a new course");
		System.out.println("4. Edit a course");
		System.out.println("5. Delete a course");
		System.out.println("6. Delete my account");
		System.out.println("7. Exit program");
		System.out.println("Option #:");
		
		do {	
			input = keyboard.nextInt();
		} while (input < 1 || input > 7);
		
		System.out.print("\n");
		
		if (input == 1) { //predict GPA
			
			Courses.predictedGPA(username);
		
		}
		
		if (input == 2) { //view courses
			
			Courses.viewCourses(username);
		
		}
		
		if (input == 3) { //add course
			
			System.out.println("To add a course, please fill in the following:");
			
			keyboard.nextLine();
			System.out.println("Course name:");
			String course = keyboard.nextLine();
			
			System.out.println("Credit hours: (between 1 and 6 included)");
			int credits = 0;
			
			do {
				
				credits = keyboard.nextInt();
				keyboard.nextLine();
			
			} while ((credits < 1) || (credits > 6));
			
			String grade = makeLetterGrade();
			
			Courses.addCourse(username, course, credits, grade);
			
		}
		
		if (input == 4) { //edit course
			
			Courses.viewCourses(username);
			System.out.println("\nWhich course do you wish to edit? (please enter the name of the course)");
			
			String editThis = "";
			keyboard.nextLine();
		
			do {
				
				editThis = keyboard.nextLine();
				
				if (Courses.checkCourseExistance(username, editThis)) {
					
					//edit the course let user enter fields
					System.out.println("\nPlease enter the following fields. If you don\'t want to change the specific field, just press enter.");
					System.out.println("Course name:");
					String course = keyboard.nextLine();
					
					System.out.println("Credit hours: (please enter 0 if you don\'t want to change it, otherwise between 1 and 6 included)");
					int credits = 0;
					
					do {
						
						credits = keyboard.nextInt();
						keyboard.nextLine();
					
					} while ((credits < 0) || (credits > 6));

					String grade = makeLetterGrade();
					
					Courses.updateCourse(username, course, credits, grade, editThis);
					
					editThis = "done";
				
				}
				
				else if (!(editThis.equals("done"))) {
				
					System.out.println("Course doesn\'t exist. Please try again: (please enter the name of the course)");
				
				}
			
			} while(!(editThis.equals("done")));
		
		}
		
		if (input == 5) { //delete course
			
			Courses.viewCourses(username);
			keyboard.nextLine();
			
			System.out.println("\nWhich course do you wish to delete? (please enter the name of the course)");
			String deleteThis = keyboard.nextLine();
			
			Courses.deleteCourse(username, deleteThis);
		
		}
		
		if (input == 6) { //delete acct
			
			System.out.println("Are you sure you want to delete your account? (please write YES if you wish to go through)");
			keyboard.nextLine();
			String delete = keyboard.nextLine();
			
			if (delete.equalsIgnoreCase("yes")) {
				
				Users.deleteUsername(username);
				Users.deleteCourses(username);
				
				System.out.println("\nYour account has been deleted successfully along with added courses that could be retrieved.");
				System.out.println("Thank you for using our program.");
				
				System.exit(1);
			
			}
			
			else System.out.println("Your account won\'t be deleted!");
			
		}
		
		if (input == 7) { //close program
			
			System.out.println("Thank you for using our program.");
			System.exit(1);
		
		}
		
	}
	
	public static String makeLetterGrade() {
		
		/**
		 * **MAKELETTERGRADE method**
		 * Prepare: We prepare a boolean "correct" that registers whether the user typed in a valid letter grade. We also initialize a String "letterGrade" for user input.
		 * Input: We let the user input a letter grade.
		 * Process: We only accept the input if the letter grade is F, D, D+, C-, C, C+, B-, B, B+, A- or A. Otherwise the user has to try again. We also upper case it for uniformity purposes.
		 * Output: We return a valid letter grade to the Menu method. Only the create course and edit course option use it because they deal with the input of a letter grade.
		 */
		
		Scanner keyboard = new Scanner(System.in);
		boolean correct = false;
		String letterGrade = "";
		
		System.out.println("Letter Grade:");
		
		do {
		
			letterGrade = keyboard.nextLine();
			letterGrade = letterGrade.toUpperCase();
			
			if (!(letterGrade.equals("F") || letterGrade.equals("D") || letterGrade.equals("D+") || letterGrade.equals("C-") || letterGrade.equals("C") || letterGrade.equals("C+") || letterGrade.equals("B-") || letterGrade.equals("B") || letterGrade.equals("B+") || letterGrade.equals("A-") || letterGrade.equals("A"))) {
			
				System.out.println("Please try to enter a valid letter grade:");
			
			}
			
			else correct = true;
			
		} while (!correct);
		
		return letterGrade;
	
	}
	
}