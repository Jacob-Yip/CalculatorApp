package src.main.java;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import static src.main.java.Utils.log;

public class CalculatorClient {
	private InputStream inputStream;
	private DataInputStream dis;
	private OutputStream outputStream;
	private DataOutputStream dos;
	
	private Socket connection;
	private final static int PORT = 3000;
	private final static String HOST = "127.0.0.1";
	
	
	public static void main(String[] args) {
		CalculatorClient client;
		try {
			client = new CalculatorClient();
			client.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @throws IOException Thrown when cannot find the host server or cannot establish a connection
	 */
	public CalculatorClient() throws IOException {
		connection = new Socket(HOST, PORT);
		log("Connected to server");
		
		inputStream = connection.getInputStream();
		dis = new DataInputStream(inputStream);
		
		outputStream = connection.getOutputStream();
		dos = new DataOutputStream(outputStream);
		
		dos.writeUTF(connection.getLocalAddress().getHostAddress());
	}
	
	
	public void run() {
		Scanner scan = new Scanner(System.in);
		boolean again = true;
		
		if(dos == null) {
			scan.close();
			close();
			return;
		}
		
		do {
			try {
				System.out.println("Please enter the first number: ");
				int n = scan.nextInt();
				dos.writeInt(n);
				System.out.println("Please enter the second number: ");
				int m = scan.nextInt();
				dos.writeInt(m);
				int sum = dis.readInt();
				System.out.println(n + " + " + m + " = " + sum);
				log("Again? ");
				String answer = scan.nextLine().toLowerCase();
				again = !(answer.equalsIgnoreCase("no") || answer.equalsIgnoreCase("n"));
			} catch (IOException e) {
				System.err.println("Cannot read data from client: " + e);
				again = false;
				break;
			}
		} while(again);
		
		scan.close();
		close();
	}
	
	/**
	 * For JavaFX
	 * @param m The first number
	 * @param n The second number
	 * @return The result of the 2 numbers
	 */
	public int calculate(int m, int n) {
		int result = -1;
		try {
			dos.writeInt(m);
			dos.writeInt(n);
			result = dis.readInt();
		} catch (IOException e) {
			System.err.println("Error in calculating numbers: " + e);
		}
		return result;
	}
	
	
	public void close() {
		try {
			dos.close();
			dis.close();
			connection.close();
		} catch (IOException e) {
			System.err.println("Cannot close resources: " + e);
		}
	}
	
}
