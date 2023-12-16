package parser;

import scanner.LexicalException;
import scanner.Scanner;
import token.Token;
import token.TokenType;

/**
 * Questa classe implementa un parser che parsifica un file secondo la
 * grammatica ac.
 * 
 * @author Linda Monfermoso, 20028464
 */
public class Parser {

	private Scanner scanner;

	public Parser(Scanner scanner) {
		this.scanner = scanner;
	}

	public void parse() throws SyntacticException {
		this.parsePrg();
	}

	private Token match(TokenType tipo) throws LexicalException, SyntacticException {
		Token t = scanner.peekToken();

		if (tipo.equals(t.getTipo())) {
			return scanner.nextToken();
		} else {
			throw new SyntacticException(
					"Aspettavo " + tipo.toString() + " alla riga " + t.getRiga() + " ma è " + t.getTipo() + ".");
		}
	}

	private void parsePrg() throws SyntacticException {
		Token t = null;
		try {
			t = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

		switch (t.getTipo()) {
		// Prg -> DSs $
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
			throw new SyntacticException(
					"Il token " + t.getTipo() + " alla riga " + t.getRiga() + " non è l'inizio del programma.");
		}
		}
	}

	private void parseDSs() throws SyntacticException {
		Token t;

		try {
			t = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

		switch (t.getTipo()) {
		// DSs -> Dcl DSs
		case TYFLOAT, TYINT -> {
			parseDcl();
			parseDSs();
		}
		// DSs -> Stm DSs
		case ID, PRINT -> {
			parseStm();
			parseDSs();
		}
		// DSs -> ϵ
		case EOF -> {
			return;
		}
		default -> {
			throw new SyntacticException("Il token " + t.getTipo() + " alla riga " + t.getRiga()
					+ " non è un DSs, dovrebbe essere TYFLOAT, TYINT, ID, PRINT o EOF.");
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

		switch (t.getTipo()) {
		// Dcl -> Ty id DclP
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
			throw new SyntacticException("Il token " + t.getTipo() + " alla riga " + t.getRiga()
					+ " non è un Dcl, dovrebbe essere TYFLOAT o TYINT.");
		}
		}
	}

	private void parseTy() throws SyntacticException {
		Token t;

		try {
			t = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

		switch (t.getTipo()) {
		// Ty -> float
		case TYFLOAT -> {
			try {
				match(TokenType.TYFLOAT);
			} catch (LexicalException e) {
				throw new SyntacticException(e.getMessage());
			}
			return;
		}
		// Ty -> int
		case TYINT -> {
			try {
				match(TokenType.TYINT);
			} catch (LexicalException e) {
				throw new SyntacticException(e.getMessage());
			}
			return;
		}
		default -> {
			throw new SyntacticException("Il token " + t.getTipo() + " alla riga " + t.getRiga()
					+ " non è un Stm, dovrebbe essere TYFLOAT o TYINT.");
		}
		}
	}

	private void parseDclP() throws SyntacticException {
		Token t;

		try {
			t = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

		switch (t.getTipo()) {
		// DclP -> ;
		case SEMI -> {
			try {
				match(TokenType.SEMI);
			} catch (LexicalException e) {
				throw new SyntacticException(e.getMessage());
			}
			return;
		}
		// DclP -> opAssign Exp ;
		case OP_ASSIGN -> {
			try {
				match(TokenType.OP_ASSIGN);
			} catch (LexicalException e) {
				throw new SyntacticException(e.getMessage());
			}
			parseExp();
			try {
				match(TokenType.SEMI);
			} catch (LexicalException e) {
				throw new SyntacticException(e.getMessage());
			}
		}
		default -> {
			throw new SyntacticException("Il token " + t.getTipo() + " alla riga " + t.getRiga()
					+ " non è un DclP, dovrebbe essere SEMI o OP_ASSIGN.");
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

		switch (t.getTipo()) {
		// Stm -> id opAssign Exp ;
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
			try {
				match(TokenType.SEMI);
			} catch (LexicalException e) {
				throw new SyntacticException(e.getMessage());
			}
		}
		// Stm -> print id ;
		case PRINT -> {
			try {
				match(TokenType.PRINT);
			} catch (LexicalException e) {
				throw new SyntacticException(e.getMessage());
			}
			try {
				match(TokenType.ID);
			} catch (LexicalException e) {
				throw new SyntacticException(e.getMessage());
			}
			try {
				match(TokenType.SEMI);
			} catch (LexicalException e) {
				throw new SyntacticException(e.getMessage());
			}
			return;
		}
		default -> {
			throw new SyntacticException("Il token " + t.getTipo() + " alla riga " + t.getRiga()
					+ " non è un Stm, dovrebbe essere ID o PRINT.");
		}
		}
	}

	private void parseExp() throws SyntacticException {
		Token t;

		try {
			t = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

		switch (t.getTipo()) {
		// Exp -> Tr ExpP
		case ID, FLOAT, INT -> {
			parseTr();
			parseExpP();
		}
		default -> {
			throw new SyntacticException("Il token " + t.getTipo() + " alla riga " + t.getRiga()
					+ " non è un Exp, dovrebbe essere ID, FLOAT o INT.");
		}
		}
	}

	private void parseExpP() throws SyntacticException {
		Token t;

		try {
			t = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

		switch (t.getTipo()) {
		// Exp -> + Tr ExpP
		case PLUS -> {
			try {
				match(TokenType.PLUS);
			} catch (LexicalException e) {
				throw new SyntacticException(e.getMessage());
			}
			parseTr();
			parseExpP();
		}
		// Exp -> - Tr ExpP
		case MINUS -> {
			try {
				match(TokenType.MINUS);
			} catch (LexicalException e) {
				throw new SyntacticException(e.getMessage());
			}
			parseTr();
			parseExpP();
		}
		// Exp -> ϵ
		case SEMI -> {
			return;
		}
		default -> {
			throw new SyntacticException("Il token " + t.getTipo() + " alla riga " + t.getRiga()
					+ " non è un ExpP, dovrebbe essere PLUS, MINUS o SEMI.");
		}
		}
	}

	private void parseTr() throws SyntacticException {
		Token t;

		try {
			t = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

		switch (t.getTipo()) {
		// Tr -> Val TrP
		case ID, FLOAT, INT -> {
			parseVal();
			parseTrP();
		}
		default -> {
			throw new SyntacticException("Il token " + t.getTipo() + " alla riga " + t.getRiga()
					+ " non è un Tr, dovrebbe essere ID, FLOAT o INT.");
		}
		}
	}

	private void parseTrP() throws SyntacticException {
		Token t;

		try {
			t = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

		switch (t.getTipo()) {
		// TrP -> * Val TrP
		case TIMES -> {
			try {
				match(TokenType.TIMES);
			} catch (LexicalException e) {
				throw new SyntacticException(e.getMessage());
			}
			parseVal();
			parseTrP();
		}
		// TrP -> / Val TrP
		case DIVIDE -> {
			try {
				match(TokenType.DIVIDE);
			} catch (LexicalException e) {
				throw new SyntacticException(e.getMessage());
			}
			parseVal();
			parseTrP();
		}
		// TrP -> ϵ
		case MINUS, PLUS, SEMI -> {
			return;
		}
		default -> {
			throw new SyntacticException("Il token " + t.getTipo() + " alla riga " + t.getRiga()
					+ " non è un TrP, dovrebbe essere TIMES, DIVIDE, PLUS, MINUS o SEMI.");
		}
		}
	}

	private void parseVal() throws SyntacticException {
		Token t;

		try {
			t = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

		switch (t.getTipo()) {
		// Val -> intVal
		case INT -> {
			try {
				match(TokenType.INT);
			} catch (LexicalException e) {
				throw new SyntacticException(e.getMessage());
			}
		}
		// Val -> floatVal
		case FLOAT -> {
			try {
				match(TokenType.FLOAT);
			} catch (LexicalException e) {
				throw new SyntacticException(e.getMessage());
			}
		}
		// Val -> id
		case ID -> {
			try {
				match(TokenType.ID);
			} catch (LexicalException e) {
				throw new SyntacticException(e.getMessage());
			}
		}
		default -> {
			throw new SyntacticException("Il token " + t.getTipo() + " alla riga " + t.getRiga()
					+ " non è un Val, dovrebbe essere INT, FLOAT o ID.");
		}
		}
	}

}
