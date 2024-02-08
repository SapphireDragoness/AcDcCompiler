package parser;

import java.util.ArrayList;

import ast.*;
import eccezioni.LexicalException;
import eccezioni.SyntacticException;
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
			decSts.add(0, decl);
			return decSts;
		}
		// DSs -> Stm DSs
		case ID, PRINT -> {
			NodeStm stm = parseStm();
			ArrayList<NodeDecSt> decSts = parseDSs();
			decSts.add(0, stm);
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
			LangType ty = parseTy();
			NodeId id = new NodeId(match(TokenType.ID).getVal());
			NodeExpr dclP = parseDclP();
			return new NodeDecl(id, ty, dclP);
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
			NodeExpr exp = parseExp();
			match(TokenType.SEMI);
			return exp;
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
			NodeId id = new NodeId(match(TokenType.ID).getVal());
			Token op = match(TokenType.OP_ASSIGN);
			NodeExpr exp = parseExp();	
			switch (op.getVal()){
	            case "+=" -> exp = new NodeBinOp(new NodeDeref(id), LangOper.PLUS, exp);
	            case "-=" -> exp = new NodeBinOp(new NodeDeref(id), LangOper.MINUS, exp);
	            case "*=" -> exp = new NodeBinOp(new NodeDeref(id), LangOper.TIMES, exp);
	            case "/=" -> exp = new NodeBinOp(new NodeDeref(id), LangOper.DIV, exp);
			}	
			match(TokenType.SEMI);
			return new NodeAssign(id, exp);
		}
		// Stm -> print id ;
		case PRINT -> {
			match(TokenType.PRINT);
			NodeId id = new NodeId(match(TokenType.ID).getVal());
			match(TokenType.SEMI);
			return new NodePrint(id);
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
			NodeExpr tr = parseTr();
			NodeExpr expP = parseExpP(tr);
			return expP;
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
			NodeExpr tr = parseTr();
			NodeExpr expP = parseExpP(tr);
			return new NodeBinOp(left, LangOper.PLUS, expP);
		}
		// Exp -> - Tr ExpP
		case MINUS -> {
			match(TokenType.MINUS);
			NodeExpr tr = parseTr();
			NodeExpr expP = parseExpP(tr);
			return new NodeBinOp(left, LangOper.MINUS, expP);
		}
		// Exp -> ϵ
		case SEMI -> {
			return left;
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
			NodeExpr val = parseVal();
			NodeExpr trP = parseTrP(val);
			return trP;
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
			NodeExpr val = parseVal();
			NodeExpr trP = parseTrP(val);
			return new NodeBinOp(left, LangOper.TIMES, trP);
		}
		// TrP -> / Val TrP
		case DIVIDE -> {
			match(TokenType.DIVIDE);
			NodeExpr val = parseVal();
			NodeExpr trP = parseTrP(val);
			return new NodeBinOp(left, LangOper.DIV, trP);
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
			String intVal = match(TokenType.INT).getVal();
			return new NodeConst(intVal, LangType.INT);
		}
		// Val -> floatVal
		case FLOAT -> {
			String floatVal = match(TokenType.FLOAT).getVal();
			return new NodeConst(floatVal, LangType.FLOAT);
		}
		// Val -> id
		case ID -> {
			String id = match(TokenType.ID).getVal();
			return new NodeDeref(new NodeId(id));
		}
		default -> {
			throw new SyntacticException("Il token " + t.getTipo() + " alla riga " + t.getRiga()
					+ " non è un Val, dovrebbe essere INT, FLOAT o ID.");
		}
		}
	}

}
