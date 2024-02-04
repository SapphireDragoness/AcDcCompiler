package scanner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.HashMap;
import java.util.HashSet;

import token.*;

/**
 * Questa classe implementa uno scanner che legge un file carattere per
 * carattere e restituisce dei token rappresentanti quei caratteri.
 * 
 * @author Linda Monfermoso, 20028464
 */
public class Scanner {

	final char EOF = (char) -1;
	private int riga;
	private PushbackReader buffer;
	private Token current;

	/* insieme di caratteri non considerati dallo scanner */
	public HashSet<Character> skipChars;
	/* insieme di lettere dell'alfabeto */
	public HashSet<Character> letters;
	/* insieme di cifre */
	public HashSet<Character> numbers;

	/* insieme dei caratteri '+', '-', '*', '/', ';', '=', ';' */
	public HashMap<Character, TokenType> charTypeMap;
	/* insieme delle parole chiave "print", "float", "int" */
	public HashMap<String, TokenType> keywordsMap;

	public Scanner(String fileName) throws FileNotFoundException {
		this.buffer = new PushbackReader(new FileReader(fileName));
		this.riga = 1;
		this.current = null;

		inizializza();
	}

	private void inizializza() {
		/* Inizializza il set skipChars */
		skipChars = new HashSet<Character>();
		skipChars.add(' ');
		skipChars.add('\n');
		skipChars.add('\t');
		skipChars.add('\r');
		skipChars.add('#');
		skipChars.add(EOF);

		/* Inizializza il set letters */
		letters = new HashSet<Character>();
		for (char c = 'a'; c <= 'z'; c++) {
			letters.add(c);
		}

		/* Inizializza il set numbers */
		numbers = new HashSet<Character>();
		for (char c = '0'; c <= '9'; c++) {
			numbers.add(c);
		}

		/* Inizializza l'hashmap charTypeMap */
		charTypeMap = new HashMap<Character, TokenType>();
		charTypeMap.put('+', TokenType.PLUS);
		charTypeMap.put('-', TokenType.MINUS);
		charTypeMap.put('*', TokenType.TIMES);
		charTypeMap.put('/', TokenType.DIVIDE);
		charTypeMap.put(';', TokenType.SEMI);
		charTypeMap.put('=', TokenType.OP_ASSIGN);

		/* Inizializza l'hashmap keywordsMap */
		keywordsMap = new HashMap<String, TokenType>();
		keywordsMap.put("print", TokenType.PRINT);
		keywordsMap.put("float", TokenType.TYFLOAT);
		keywordsMap.put("int", TokenType.TYINT);
	}

	/**
	 * Ritorna il prossimo token presente nello stream, consumandolo.
	 * 
	 * @return un token
	 * @throws LexicalException se non è possibile leggere lo stream o se il
	 *                          carattere è illegale.
	 */
	public Token nextToken() throws LexicalException {
		if (current != null) {
			Token t = current;
			current = null;
			return t;
		}
		char nextChar;
		nextChar = peekChar();

		/*
		 * Avanza nel buffer leggendo i carattere in skipChars incrementando riga se
		 * leggi '\n'. Se raggiungi la fine del file ritorna il Token EOF
		 */
		if (skipChars.contains(nextChar)) {
			while (skipChars.contains(nextChar)) {
				if (nextChar == EOF) {
					return new Token(TokenType.EOF, riga, "EOF");
				}
				/* il commento inizia con '#' e finisce a fine riga */
				if (nextChar == '#') {
					while(nextChar != '\n') {
						readChar();
						nextChar = peekChar();
					}
				}
				if (nextChar == '\n') {
					riga++;
				}
				readChar();
				nextChar = peekChar();
			}
		}

		/*
		 * Se nextChar e' in letters return scanId() che legge tutte le lettere
		 * minuscole e ritorna un Token ID o il Token associato Parola Chiave (per
		 * generare i Token per le parole chiave usate l'HashMap di corrispondenza
		 */		
		if (letters.contains(nextChar)) {
			return scanId();
		}

		// Se nextChar e' o in operators oppure
		// ritorna il Token associato con l'operatore o il delimitatore
		if (charTypeMap.containsKey((Object) nextChar)) {
			return scanOperators();
		}

		// Se nextChar e' in numbers
		// return scanNumber()
		// che legge sia un intero che un float e ritorna il Token INUM o FNUM
		// i caratteri che leggete devono essere accumulati in una stringa
		// che verra' assegnata al campo valore del Token
		if (numbers.contains(nextChar)) {
			return scanNumber();
		}

		// Altrimenti il carattere NON E' UN CARATTERE LEGALE sollevate una
		// eccezione lessicale dicendo la riga e il carattere che la hanno
		// provocata.
		throw new LexicalException("Carattere illegale " + nextChar + " alla riga " + riga);
	}

	/**
	 * Ritorna il prossimo token presente nello stream, senza consumarlo.
	 * 
	 * @return un token
	 * @throws LexicalException se non è possibile leggere lo stream o se il
	 *                          carattere è illegale.
	 */
	public Token peekToken() throws LexicalException {
		if (current == null) {
			current = nextToken();
		}
		return current;
	}

	/**
	 * Scandisce un numero (intero o float) costruendo una stringa e ritornando il
	 * token corrispondente.
	 * 
	 * @return il token corrispondente
	 * @throws LexicalException se il numero è in formato non corretto
	 */
	private Token scanNumber() throws LexicalException {
		String number = "";
		char c;

		while (!skipChars.contains(peekChar()) && peekChar() != ';') {
			c = readChar();
			number += c;
		}
		if (number.matches("[0-9]+[.]([0-9]{0,5})")) {
			return new Token(TokenType.FLOAT, riga, number);
		} else if (number.matches("0|([1-9][0-9]*)")) {
			return new Token(TokenType.INT, riga, number);
		} else
			throw new LexicalException(
					"FLOAT o INT non parsificabile alla riga " + riga + ", non corrisponde al regex.");
	}

	/**
	 * Scandisce un id costruendo una stringa e ritornando il token corrispondente.
	 * 
	 * @return il token corrispondente
	 * @throws LexicalException se l'id è in formato non corretto
	 */
	private Token scanId() throws LexicalException {
		String id = "";
		char c;

		while (!skipChars.contains(peekChar()) && peekChar() != ';' && !charTypeMap.containsKey(peekChar())) {
			c = readChar();
			id += c;
		}
		if (!id.matches("[a-z]+")) {
			throw new LexicalException("ID non parsificabile alla riga " + riga + ", non corrisponde al regex.");
		}
		if (keywordsMap.containsKey(id)) {
			for (String kw : keywordsMap.keySet()) {
				if (id.matches(kw)) {
					return new Token(keywordsMap.get(kw), riga);
				}
			}
		}
		return new Token(TokenType.ID, riga, id);
	}

	/**
	 * Scandisce un operatore ritornando il token corrispondente.
	 * 
	 * @return il token corrispondente
	 * @throws LexicalException se il token è in formato non corretto
	 */
	private Token scanOperators() throws LexicalException {
		char c;
		c = readChar();
		if (charTypeMap.containsKey(peekChar())) {
			String op = "";
			op += c;
			op += readChar();
			return new Token(TokenType.OP_ASSIGN, riga, op);
		}
		return new Token(charTypeMap.get(c), riga, Character.toString(c));
	}

	/**
	 * Legge un carattere dallo stream consumandolo.
	 * 
	 * @return il carattere letto
	 * @throws LexicalException se non è possibile leggere dallo stream
	 */
	private char readChar() throws LexicalException {
		try {
			return ((char) this.buffer.read());
		} catch (IOException e) {
			throw new LexicalException("Impossibile leggere carattere.");
		}
	}

	/**
	 * Legge un carattere dallo stream senza consumarlo.
	 * 
	 * @return il carattere letto
	 * @throws LexicalException
	 * @throws IOException      se non è possibile leggere dallo stream
	 */
	private char peekChar() throws LexicalException {
		char c = 0;
		try {
			c = (char) buffer.read();
		} catch (IOException e) {
			throw new LexicalException("Impossibile leggere carattere.");
		}
		try {
			buffer.unread(c);
		} catch (IOException e) {
			throw new LexicalException("Impossibile leggere carattere.");
		}
		return c;
	}

}
