package token;

/**
 * Implementa i token utilizzati durante l'analisi lessicale.
 * 
 * @author Linda Monfermoso, 20028464
 */
public class Token {

	private int riga;
	private TokenType tipo;
	private String val;

	/**
	 * Costruttore per Token.
	 * 
	 * @param tipo il tipo assegnato al Token
	 * @param riga la riga assegnata al Token
	 * @param val  il valore assegnato al Token
	 */
	public Token(TokenType tipo, int riga, String val) {
		this.tipo = tipo;
		this.riga = riga;
		this.val = val;
	}

	/**
	 * Costruttore per Token.
	 * 
	 * @param tipo il tipo assegnato al Token
	 * @param riga la riga assegnata al Token
	 */
	public Token(TokenType tipo, int riga) {
		this.tipo = tipo;
		this.riga = riga;
	}

	/**
	 * Restituisce la riga assegnata al Token.
	 * 
	 * @return la riga
	 */
	public int getRiga() {
		return riga;
	}

	/**
	 * Restituisce il tipo assegnato al Token.
	 * 
	 * @return il tipo
	 */
	public TokenType getTipo() {
		return tipo;
	}

	/**
	 * Restituisce il valore assegnato al Token.
	 * 
	 * @return il valore
	 */
	public String getVal() {
		return val;
	}

	@Override
	public String toString() {
		if (getTipo() == TokenType.INT || getTipo() == TokenType.FLOAT || getTipo() == TokenType.ID
				|| getTipo() == TokenType.OP_ASSIGN) {
			return getTipo().toString() + ", riga: " + getRiga() + ", " + getVal();
		}
		return getTipo().toString() + ", riga: " + getRiga();
	}

}
