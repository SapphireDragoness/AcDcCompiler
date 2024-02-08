package eccezioni;

public class SyntacticException extends Exception {
	
	public SyntacticException() {
		super();
	}
	
	public SyntacticException(String msg) {
		super(msg);
	}
	
	public SyntacticException(Throwable cause) {
		super(cause);
	}
	
	public SyntacticException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
