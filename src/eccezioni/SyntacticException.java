package eccezioni;

import token.TokenType;

/**
 * Eccezione per l'analisi sintattica.
 * 
 * @author Linda Monfermoso, 20028464
 */
public class SyntacticException extends Exception {

	/**
	 * Costruttore per SyntacticException con riga, token atteso e token ottenuto.
	 * 
	 * @param riga     la riga dell'errore
	 * @param atteso   il token atteso
	 * @param ottenuto il token ottenuto
	 */
	public SyntacticException(int riga, String atteso, TokenType ottenuto) {
		super("Errore sintattico a riga " + riga + ": atteso " + atteso + ", ma è " + ottenuto);
	}

	/**
	 * Costruttore per SyntacticException con messaggio.
	 * 
	 * @param msg
	 */
	public SyntacticException(String msg) {
		super(msg);
	}

}
