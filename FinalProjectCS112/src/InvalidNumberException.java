
public class InvalidNumberException extends Exception {

	public InvalidNumberException() {
	    super("Invalid number - enter 10 digits only.");
	}
	
	public InvalidNumberException(String message) {
	    super(message);
	}
}
