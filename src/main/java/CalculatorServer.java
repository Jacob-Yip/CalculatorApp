package src.main.java;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static src.main.java.Utils.log;

public class CalculatorServer {
	private static CalculatorServer server = null;
	private final static int PORT = 3000;
	private Socket connection;
	private ServerSocket serverSocket;
	private InputStream inputStream;
	private DataInputStream dis;
	private OutputStream outputStream;
	private DataOutputStream dos;
	
	private boolean connected = false;
	private String clientAddress;
	
	
	public static void main(String[] args) {
		CalculatorServer driver = CalculatorServer.getInstance();
		driver.run();
	}
	
	private CalculatorServer() {
		try {
			serverSocket = new ServerSocket(PORT);
			log("Listening to port", PORT);
			
			connection = serverSocket.accept();
			connected = true;
			log("Connection established");
			
			inputStream = connection.getInputStream();
			dis = new DataInputStream(inputStream);
			
			outputStream = connection.getOutputStream();
			dos = new DataOutputStream(outputStream);
			
			clientAddress = dis.readUTF();
			log("Address of client", clientAddress);
			
		} catch (IOException e) {
			System.err.println("Unable to create server: " + e);
		}
		
	}
	
	public static CalculatorServer getInstance() {
		if(server == null) {
			server = new CalculatorServer();
		}
		return server;
	}
	
	private void run() {
		if(server == null) {
			return;
		}
		
		while(connected) {
			log("Listening client ...");
			try {
				int m = dis.readInt();
				int n = dis.readInt();
				
				int sum = m + n;
				log("Sum", sum);
				dos.writeInt(sum);
			} catch (IOException e) {
				System.err.println("Unable to read numbers from client: " + e);
				connected = false;
			}
		}
		
		close();
	}
	
	private void close() {
		try {
			log("Closing server resources");
			dos.close();
			dis.close();
			serverSocket.close();
			connection.close();
		} catch (IOException e) {
			System.err.println("Cannot close resources: " + e);
		}
	}
	
}
