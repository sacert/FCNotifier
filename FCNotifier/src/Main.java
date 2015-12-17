import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;


public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		// get directory of file
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter file or directory: ");
		String input = reader.nextLine();
		
		// check if a valid file
		File file = new File(input);
		while(!file.exists()) {
			System.out.println("Directory not valid, try again: ");
			input = reader.nextLine();
			file = new File(input);
		}
		
		// get recipient
		System.out.println("Enter recipient: ");
		String sendTo = reader.nextLine();
		
		// if not a proper email address, prompt user to try again
		while(!isValidEmail(sendTo)) {
			System.out.println("Recipient not valid, try again (must be an email address): ");
			sendTo = reader.nextLine();
		}
		
		System.out.println("Enter message: ");
		String message = reader.nextLine();
		
		// determine if the file has been changed (i.e, is downloading)
		checkModified(input, sendTo, message);
		
		// close reader
		reader.close();

	}
	
	// checks to see if a file has been modified which indicates that it is currently downloading
	public static void checkModified(String url, String sendTo, String message) throws InterruptedException {
		
		// get directory of file
		//Scanner reader = new Scanner(System.in);
		File file = new File(url);
//		while(!file.exists()) {
//			System.out.println("Directory not valid, try again: ");
//			String input = reader.nextLine();
//			file = new File(input);
//		}
//		reader.close();
		
		// get current modified file size
		SimpleDateFormat sdf = new SimpleDateFormat("ss");
		int temp = Integer.parseInt(sdf.format(file.lastModified()));
		
		// use counter to check if the file has been modified or not
		int counter = 0;
		System.out.println("<< Starting >>");
		
		// while downloading, keep checking if the file has been modified
		// if counter = 1 and the counter hasn't been modified, the file is not being modified 
		while(true) {
			counter++;
			Thread.sleep(10000);
			int timer = Integer.parseInt(sdf.format(file.lastModified()));
			if((timer - temp) == 0) {
				if(counter == 1) 
					System.out.println("Nothing Downloading...");
				else
					System.out.println("File Downloading...");
				break;
			}
			else
				temp = timer;
		
			System.out.println("File Downloading...");
		}
		
		// print if the file has been modified
		if(counter != 1) {
			SendEmail.sendMail(sendTo, message);
		}
	}
	
	public static boolean isValidEmail(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException e) {
			result = false;
		}
		return result;
	}
}
