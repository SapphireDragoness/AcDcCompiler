package eccezioni;

public class LexicalException extends Exception {
	
	public LexicalException(int riga, char c) {
		super("Errore lessicale a riga " + riga + ": carattere illegale " + c);
	}
	
	public LexicalException(int riga, String id) {
		super("Errore lessicale a riga " + riga + ": sequenza di caratteri '" + id + "' non riconosciuta");
	}

	public LexicalException(int riga) {
		super("Errore lessicale a riga " + riga + ": impossibile leggere carattere");
	}
	
}
