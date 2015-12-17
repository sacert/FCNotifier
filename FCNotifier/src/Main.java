import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;


public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		// get directory of file
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter file: ");
		String input = reader.nextLine();
		
		// check if a valid file
		File file = new File(input);
		while(!file.exists()) {
			System.out.println("File Directory not valid, try again: ");
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
		
		File file = new File(url);
		boolean noInet = false;
		
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
			Thread.sleep(15000);
		
			// check if there is a valid connect, if not, try to reconnect
			if(!inetTest("www.google.ca")) {
				if(counter == 1) 
					System.out.println("ERROR: No Internet Connection");
				else 
					System.out.println("\nERROR: No Internet Connection");
				int retryCounter = 0;
				
				// attempt 3 reconnects to the internet
				while(retryCounter != 3) {
					System.out.println("Attempting to connect to internet...Retry " + (retryCounter+1) + "\r");
					Thread.sleep(15000);
					
					if(inetTest("www.google.ca")) {
						System.out.println("Reconnected");
						Thread.sleep(15000);
						noInet = false;
						break;
					}
					else {
						noInet = true;
					}
					retryCounter++;
				}
				
			}
			
			int timer = Integer.parseInt(sdf.format(file.lastModified()));
			// check if no modifications are made
			// either the file is done downloading or can not start
			if(timer == temp) {
				if(counter == 1) 
					System.out.println("Nothing Downloading...");
				else
					System.out.println("\nFile Downloaded!");
				break;
			}
			else {
				temp = timer;
			}
			
			if(counter % 3 == 0)
				System.out.print("\rFile Downloading.  ");
			else if(counter % 3 == 1)
				System.out.print("\rFile Downloading.. ");
			else if(counter % 3 == 2)
				System.out.print("\rFile Downloading...");
		}
		
		// print if the file has been modified
		if(counter != 1 && noInet == false) {
			SendEmail.sendMail(sendTo, message);
		}
	}
	
	// determines if the user entered a valid email
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
	
	// check if connected to the internet
	public static boolean inetTest(String site) {
		Socket sk = new Socket();
		InetSocketAddress addr = new InetSocketAddress(site, 80);
		try {
			sk.connect(addr, 3000);
			return true;
		} catch (IOException e) {
			return false;
		} finally {
			try {sk.close();}
			catch (IOException e) {}
		}
	}
	
	
}
