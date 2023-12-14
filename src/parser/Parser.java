package parser;

import scanner.LexicalException;
import scanner.Scanner;
import token.Token;
import token.TokenType;

public class Parser {
	
	private Scanner scanner;
	
	public Parser(Scanner scanner) {
		this.scanner = scanner;
	}
	
	public void parse() throws LexicalException, SyntacticException {
		this.parsePrg();
	}
	
	private void parsePrg() throws SyntacticException {
		Token t = null
				;
		try {
			t = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}
		
		switch(t.getTipo()) {
			case TYFLOAT, TYINT, ID, PRINT, EOF -> {
				parseDSs();
				try {
					match(TokenType.EOF);
				} catch (LexicalException e) {
					throw new SyntacticException(e.getMessage());
				}
				return;
			}
			default -> {
				throw new SyntacticException("Il token " + t.getTipo() + " alla riga " + t.getRiga() + " non è l'inizio del programma.");
			}
		}
	}
	
	private Token match(TokenType tipo) throws LexicalException, SyntacticException {
		Token t = scanner.peekToken();
		
		if(tipo.equals(t.getTipo())) {
			return scanner.nextToken();
		}
		else {
			throw new SyntacticException("Aspettato " + tipo.toString() + "alla riga" + t.getRiga());
		}
	}
	
	private void parseDSs() throws SyntacticException {
		Token t;
		
		try {
			t = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}
		
		switch(t.getTipo()) {
			case TYFLOAT, TYINT -> {
				parseDcl();
				parseDSs();
			}
			case ID, PRINT -> {
				parseStm();
				parseDSs();
			}
			case EOF -> {
				try {
					match(TokenType.EOF);
				} catch (LexicalException e) {
					throw new SyntacticException(e.getMessage());
				}
				return;
			}
			default -> {
				throw new SyntacticException("Il token " + t.getTipo() + " alla riga " + t.getRiga() + " non è un DSs.");
			}
		}
	}
	
	private void parseDcl() throws SyntacticException {
		Token t;
		
		try {
			t = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}
		
		switch(t.getTipo()) {
			case TYFLOAT, TYINT -> {
				parseTy();
				try {
					match(TokenType.ID);
				} catch (LexicalException e) {
					throw new SyntacticException(e.getMessage());
				}
				parseDclP();
			}
			default -> {
				throw new SyntacticException("Il token " + t.getTipo() + " alla riga " + t.getRiga() + " non è un Dcl.");
			}
		}
	}
	
	private void parseTy() {
			
	}
	
	private void parseDclP() throws SyntacticException {
		Token t;
		
		try {
			t = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}
		
		switch(t.getTipo()) {
			case SEMI -> {
				try {
					match(TokenType.SEMI);
				} catch (LexicalException e) {
					throw new SyntacticException(e.getMessage());
				}
				return;
			}
			case OP_ASSIGN -> {
				try {
					match(TokenType.EOF);
				} catch (LexicalException e) {
					throw new SyntacticException(e.getMessage());
				}
				parseExp();
			}
			default -> {
				throw new SyntacticException("Il token " + t.getTipo() + " alla riga " + t.getRiga() + " non è un DclP.");
			}
		}
	}

	private void parseStm() throws SyntacticException {
		Token t;
		
		try {
			t = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}
		
		switch(t.getTipo()) {
			case ID -> {
				try {
					match(TokenType.ID);
				} catch (LexicalException e) {
					throw new SyntacticException(e.getMessage());
				}
				try {
					match(TokenType.OP_ASSIGN);
				} catch (LexicalException e) {
					throw new SyntacticException(e.getMessage());
				}
				parseExp();
			}
			case PRINT -> {
				try {
					match(TokenType.PRINT);
				} catch (LexicalException e) {
					throw new SyntacticException(e.getMessage());
				}
				return;
			}
			default -> {
				throw new SyntacticException("Il token " + t.getTipo() + " alla riga " + t.getRiga() + " non è un Stm.");
			}
		}
	}
	
	private void parseExp() {
		// TODO Auto-generated method stub
		
	}
	
}
