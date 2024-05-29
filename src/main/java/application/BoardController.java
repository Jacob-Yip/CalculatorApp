package src.main.java.application;

import src.main.java.CalculatorClient;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import static src.main.java.Utils.log;

import java.io.IOException;

public class BoardController {
	CalculatorClient client;
	int m = 0;
	int n = 0;
	boolean firstNum = true;    // Whether we are amending the first number
	
	@FXML
	TextArea taDisplay;
	
	public BoardController() throws IOException {
		client = new CalculatorClient();
	}
	
	public void reset() {
		m = 0;
		n = 0;
		firstNum = true;
		// Update display
		taDisplay.setText("0");
	}
	
	public void setInput(MouseEvent event) {
		Button button = (Button)event.getSource();
		log("input", button.getText());
		if(firstNum) {
			m = m * 10 + Integer.valueOf(button.getText()).intValue();
			// Update display
			taDisplay.setText(String.valueOf(m));
		} else {
			n = n * 10 + Integer.valueOf(button.getText()).intValue();
			// Update display
			taDisplay.setText(String.valueOf(n));
		}
		
		
	}
	
	public void switchInput(MouseEvent event) {
		log("switchInput()'s firstNum", firstNum);
		if(firstNum) {
			firstNum = !firstNum;
			// Update display
			taDisplay.setText("0");
		}
	}
	
	public void calculate() {
		if(client != null) {
			if(firstNum) {
				return;
			}
			int sum = client.calculate(m, n);
			// Display result
			taDisplay.setText(String.valueOf(sum));
			m = 0;
			n = 0;
			firstNum = true;
		} else {
			System.err.println("calculate() invoked when client == null");
		}
	}
	
	public void close() {
		client.close();
		Platform.exit();
		System.exit(0);
	}
}
