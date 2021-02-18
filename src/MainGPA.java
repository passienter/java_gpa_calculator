import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class MainGPA {

	public static void main(String[] args) throws SQLException {
		
		/**
		 * **MAIN method**
		 * Prepare: usernameInput variable for user input.
		 * Input: user inputs either a username, or types new to make a new account
		 * Process: we check if the user typed new. If so, we make him type a new name. If it already exists, he need to try again. If the user didn't type new, we check if what he typed is an existing account name. Otherwise we let him try again.
		 * Output: The user either creates an account or logs into one. Afterwards, he faces the menu which he interacts with.
		 */
		
		String usernameInput = "";
		
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("Please enter your username. If you wish to create a new account, please type \"new\"");
		usernameInput = keyboard.nextLine();
		
		if (usernameInput.equalsIgnoreCase("new")) {
			
			do {
				
				System.out.println("Please enter a new username: (if you have already tried, it\'s because the username already exists)");
				usernameInput = keyboard.nextLine();
			
			} while (Users.checkUsernameDB(usernameInput) == 1); //if it already exists then loop again
			
			if (Users.checkUsernameDB(usernameInput) == 2) {
				
				Users.addUsernameDB(usernameInput); //add new user in db
				System.out.println("Account made successfully!");
			
			}
		
		}
		
		else {
			
			if(Users.checkUsernameDB(usernameInput) == 1) { //username exists, then we are logged in
				
				System.out.println("Logged in successfully!");
			
			}
			
			else {
				
				do {
					
					System.out.println("Couldn\'t find your username. Please try again:");
					usernameInput = keyboard.nextLine();
				
				} while (Users.checkUsernameDB(usernameInput) == 2);
				
				System.out.println("Logged in successfully!");
			}
			
		}
		
		do {
			
			System.out.print("\n");
			MenuAndMisc.Menu(usernameInput); //our Menu
		
		} while (true);
	
	}

}