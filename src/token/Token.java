package token;

public class Token {

	private int riga;
	private TokenType tipo;
	private String val;
	
	public Token(TokenType tipo, int riga, String val) {
		this.tipo = tipo;
		this.riga = riga;
		this.val = val;
	}
	
	public Token(TokenType tipo, int riga) {
		this.tipo = tipo;
		this.riga = riga;
	}

	public int getRiga() {
		return riga;
	}

	public TokenType getTipo() {
		return tipo;
	}

	public String getVal() {
		return val;
	}

	@Override
	public String toString() {
		if(getTipo() == TokenType.INT || getTipo() == TokenType.FLOAT || getTipo() == TokenType.ID || getTipo() == TokenType.OP_ASSIGN) {
			return getTipo().toString() + ", riga: " + getRiga() + ", " + getVal();
		}
		return getTipo().toString() + ", riga: " + getRiga();
	}

}
