package eccezioni;

public class LexicalException extends Exception {
	
	public LexicalException() {
		super();
	}
	
	public LexicalException(String msg) {
		super(msg);
	}
	
	public LexicalException(Throwable cause) {
		super(cause);
	}
	
	public LexicalException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
