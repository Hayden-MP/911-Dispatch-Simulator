
public class NegativeAgeException extends Exception {

	public NegativeAgeException() {
	    super("Enter non-negative age.");
	}
	
	public NegativeAgeException(String message) {
	    super(message);
	}
}
