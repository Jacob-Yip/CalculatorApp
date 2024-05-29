package src.main.java;

public class Utils {
	public static void log(String message) {
		System.out.println(message);
	}
	
	public static void log(String message, int value) {
		StringBuilder builder = new StringBuilder();
		builder.append(message);
		builder.append(": ");
		builder.append(value);
		log(builder.toString());
	}
	
	public static void log(String message, Object o) {
		StringBuilder builder = new StringBuilder();
		builder.append(message);
		builder.append(": ");
		builder.append(o.toString());
		log(builder.toString());
	}
	
	public static void log(String message, boolean value) {
		StringBuilder builder = new StringBuilder();
		builder.append(message);
		builder.append(": ");
		builder.append(value);
		log(builder.toString());
	}
	
	public static void log(String... messages) {
		for(String message: messages) {
			System.out.println(message);
		}
	}
}
