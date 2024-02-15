package eccezioni;

/**
 * Eccezione per l'analisi lessicale.
 * 
 * @author Linda Monfermoso, 20028464
 */
public class LexicalException extends Exception {

	/**
	 * Costruttore per LexicalException con riga e carattere illegale.
	 * 
	 * @param riga la riga dell'errore
	 * @param c    il carattere illegale
	 */
	public LexicalException(int riga, char c) {
		super("Errore lessicale a riga " + riga + ": carattere illegale " + c);
	}

	/**
	 * Costruttore per LexicalException con riga e id del token.
	 * 
	 * @param riga la riga dell'errore
	 * @param id   il token malformato
	 */
	public LexicalException(int riga, String id) {
		super("Errore lessicale a riga " + riga + ": sequenza di caratteri '" + id + "' non riconosciuta");
	}

	/**
	 * Costruttore per LexicalException con riga.
	 * 
	 * @param riga la riga dell'errore
	 */
	public LexicalException(int riga) {
		super("Errore lessicale a riga " + riga + ": impossibile leggere carattere");
	}

}
