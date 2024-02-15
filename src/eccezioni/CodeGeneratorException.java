package eccezioni;

/**
 * Eccezione per la generazione del codice.
 * 
 * @author Linda Monfermoso, 20028464
 */
public class CodeGeneratorException extends Exception{
	
	/**
	 * Costruttore per CodeGeneratorException con messaggio.
	 * 
	 * @param msg il messaggio dell'eccezione
	 */
	public CodeGeneratorException(String msg) {
		super(msg);
	}
	
}
