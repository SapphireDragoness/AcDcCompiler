package parser;

import java.util.ArrayList;

import ast.LangType;
import ast.NodeDecSt;
import ast.NodeDecl;
import ast.NodeExpr;
import ast.NodeId;
import ast.NodePrint;
import ast.NodeProgram;
import ast.NodeStm;
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

	public NodeProgram parse() throws SyntacticException {
		return this.parsePrg();
	}

	private Token match(TokenType tipo) throws SyntacticException {
		Token t;
		try {
			t = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

		if (tipo.equals(t.getTipo())) {
			try {
				return scanner.nextToken();
			} catch (LexicalException e) {
				throw new SyntacticException(e.getMessage());
			}
		} else {
			throw new SyntacticException(
					"Aspettavo " + tipo.toString() + " alla riga " + t.getRiga() + " ma è " + t.getTipo() + ".");
		}
	}

	private NodeProgram parsePrg() throws SyntacticException {
		Token t = null;
		
		try {
			t = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

		switch (t.getTipo()) {
		// Prg -> DSs $
		case TYFLOAT, TYINT, ID, PRINT, EOF -> {
			ArrayList<NodeDecSt> prg = parseDSs();
			match(TokenType.EOF);
			return new NodeProgram(prg);
		}
		default -> {
			throw new SyntacticException(
					"Il token " + t.getTipo() + " alla riga " + t.getRiga() + " non è l'inizio del programma.");
		}
		}
	}

	private ArrayList<NodeDecSt> parseDSs() throws SyntacticException {
		Token t;

		try {
			t = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

		switch (t.getTipo()) {
		// DSs -> Dcl DSs
		case TYFLOAT, TYINT -> {
			NodeDecl decl = parseDcl();
			ArrayList<NodeDecSt> decSts = parseDSs();
			return decSts;
		}
		// DSs -> Stm DSs
		case ID, PRINT -> {
			NodeStm stm = parseStm();
			ArrayList<NodeDecSt> decSts = parseDSs();
			return decSts;
		}
		// DSs -> ϵ
		case EOF -> {
			return new ArrayList<NodeDecSt>();
		}
		default -> {
			throw new SyntacticException("Il token " + t.getTipo() + " alla riga " + t.getRiga()
					+ " non è un DSs, dovrebbe essere TYFLOAT, TYINT, ID, PRINT o EOF.");
		}
		}
	}

	private NodeDecl parseDcl() throws SyntacticException {
		Token t;

		try {
			t = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

		switch (t.getTipo()) {
		// Dcl -> Ty id DclP
		case TYFLOAT, TYINT -> {
			LangType type = parseTy();
			String id = match(TokenType.ID).getVal();
			NodeExpr init = parseDclP();
			return new NodeDecl(new NodeId(id), type, init);
		}
		default -> {
			throw new SyntacticException("Il token " + t.getTipo() + " alla riga " + t.getRiga()
					+ " non è un Dcl, dovrebbe essere TYFLOAT o TYINT.");
		}
		}
	}

	private LangType parseTy() throws SyntacticException {
		Token t;

		try {
			t = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

		switch (t.getTipo()) {
		// Ty -> float
		case TYFLOAT -> {
			match(TokenType.TYFLOAT);
			return LangType.FLOAT;
		}
		// Ty -> int
		case TYINT -> {
			match(TokenType.TYINT);
			return LangType.INT;
		}
		default -> {
			throw new SyntacticException("Il token " + t.getTipo() + " alla riga " + t.getRiga()
					+ " non è un Stm, dovrebbe essere TYFLOAT o TYINT.");
		}
		}
	}

	private NodeExpr parseDclP() throws SyntacticException {
		Token t;

		try {
			t = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

		switch (t.getTipo()) {
		// DclP -> ;
		case SEMI -> {
			match(TokenType.SEMI);
			return null;
		}
		// DclP -> opAssign Exp ;
		case OP_ASSIGN -> {
			match(TokenType.OP_ASSIGN);
			parseExp();
			match(TokenType.SEMI);
			return null;
		}
		default -> {
			throw new SyntacticException("Il token " + t.getTipo() + " alla riga " + t.getRiga()
					+ " non è un DclP, dovrebbe essere SEMI o OP_ASSIGN.");
		}
		}
	}

	private NodeStm parseStm() throws SyntacticException {
		Token t;

		try {
			t = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

		switch (t.getTipo()) {
		// Stm -> id opAssign Exp ;
		case ID -> {
			match(TokenType.ID);
			match(TokenType.OP_ASSIGN);
			parseExp();	
			match(TokenType.SEMI);
			return null;
		}
		// Stm -> print id ;
		case PRINT -> {
			match(TokenType.PRINT);
			String id = match(TokenType.ID).getVal();
			match(TokenType.SEMI);
			return new NodePrint(new NodeId(id));
		}
		default -> {
			throw new SyntacticException("Il token " + t.getTipo() + " alla riga " + t.getRiga()
					+ " non è un Stm, dovrebbe essere ID o PRINT.");
		}
		}
	}

	private NodeExpr parseExp() throws SyntacticException {
		Token t;

		try {
			t = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

		switch (t.getTipo()) {
		// Exp -> Tr ExpP
		case ID, FLOAT, INT -> {
			NodeExpr left = parseTr();
			NodeExpr exp = parseExpP(left);
			return null;
		}
		default -> {
			throw new SyntacticException("Il token " + t.getTipo() + " alla riga " + t.getRiga()
					+ " non è un Exp, dovrebbe essere ID, FLOAT o INT.");
		}
		}
	}

	private NodeExpr parseExpP(NodeExpr left) throws SyntacticException {
		Token t;

		try {
			t = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

		switch (t.getTipo()) {
		// Exp -> + Tr ExpP
		case PLUS -> {
			match(TokenType.PLUS);
			NodeExpr exp1 = parseTr();
			NodeExpr exp2 = parseExpP(exp1);
			return null;
		}
		// Exp -> - Tr ExpP
		case MINUS -> {
			match(TokenType.MINUS);
			NodeExpr exp1 = parseTr();
			NodeExpr exp2 = parseExpP(exp1);
			return null;
		}
		// Exp -> ϵ
		case SEMI -> {
			return null;
		}
		default -> {
			throw new SyntacticException("Il token " + t.getTipo() + " alla riga " + t.getRiga()
					+ " non è un ExpP, dovrebbe essere PLUS, MINUS o SEMI.");
		}
		}
	}

	private NodeExpr parseTr() throws SyntacticException {
		Token t;

		try {
			t = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

		switch (t.getTipo()) {
		// Tr -> Val TrP
		case ID, FLOAT, INT -> {
			NodeExpr left = parseVal();
			NodeExpr exp = parseTrP(left);
			return null;
		}
		default -> {
			throw new SyntacticException("Il token " + t.getTipo() + " alla riga " + t.getRiga()
					+ " non è un Tr, dovrebbe essere ID, FLOAT o INT.");
		}
		}
	}

	private NodeExpr parseTrP(NodeExpr left) throws SyntacticException {
		Token t;

		try {
			t = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

		switch (t.getTipo()) {
		// TrP -> * Val TrP
		case TIMES -> {
			match(TokenType.TIMES);
			NodeExpr exp1 = parseVal();
			NodeExpr exp2 = parseTrP(exp1);
			return null;
		}
		// TrP -> / Val TrP
		case DIVIDE -> {
			match(TokenType.DIVIDE);
			NodeExpr exp1 = parseVal();
			NodeExpr exp2 = parseTrP(exp1);
			return null;
		}
		// TrP -> ϵ
		case MINUS, PLUS, SEMI -> {
			return left;
		}
		default -> {
			throw new SyntacticException("Il token " + t.getTipo() + " alla riga " + t.getRiga()
					+ " non è un TrP, dovrebbe essere TIMES, DIVIDE, PLUS, MINUS o SEMI.");
		}
		}
	}

	private NodeExpr parseVal() throws SyntacticException {
		Token t;

		try {
			t = scanner.peekToken();
		} catch (LexicalException e) {
			throw new SyntacticException(e.getMessage());
		}

		switch (t.getTipo()) {
		// Val -> intVal
		case INT -> {
			match(TokenType.INT);
			return null;
		}
		// Val -> floatVal
		case FLOAT -> {
			match(TokenType.FLOAT);
			return null;
		}
		// Val -> id
		case ID -> {
			match(TokenType.ID);
			return null;
		}
		default -> {
			throw new SyntacticException("Il token " + t.getTipo() + " alla riga " + t.getRiga()
					+ " non è un Val, dovrebbe essere INT, FLOAT o ID.");
		}
		}
	}

}
