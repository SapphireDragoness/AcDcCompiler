package parser;

import java.util.ArrayList;

import ast.*;
import eccezioni.LexicalException;
import eccezioni.SyntacticException;
import scanner.Scanner;
import token.Token;
import token.TokenType;

/**
 * Implementa un parser che parsifica un file secondo la grammatica ac.
 * 
 * @author Linda Monfermoso, 20028464
 */
public class Parser {

	private Scanner scanner;

	/**
	 * Costruttore della classe Parser.
	 * 
	 * @param scanner lo scanner da utilizzare
	 */
	public Parser(Scanner scanner) {
		this.scanner = scanner;
	}

	/**
	 * Inizia il parsing.
	 * 
	 * @return NodeProgram dell'AST
	 * @throws SyntacticException se viene riscontrato un errore sintattico
	 */
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
			throw new SyntacticException(t.getRiga(), tipo.toString(), t.getTipo());
		}
	}

	/**
	 * Implementa la regola: Prg -> DSs $
	 * 
	 * @return NodeProgram dell'AST
	 * @throws SyntacticException se viene riscontrato un errore sintattico
	 */
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
		/* il compilatore entra in panic mode, cerca un ';' e continua il parsing */
		default -> {
			match(TokenType.SEMI);
			parse();
			throw new SyntacticException(t.getRiga(), "TYFLOAT, TYINT, ID, PRINT o EOF (PANIC MODE: cercherò un ';')",
					t.getTipo());
		}
		}
	}

	/**
	 * Implementa le regole: DSs -> Dcl DSs DSs -> Stm DSs DSs -> ϵ
	 * 
	 * @return lista di decSts
	 * @throws SyntacticException se viene riscontrato un errore sintattico
	 */
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
			throw new SyntacticException(t.getRiga(), "TYFLOAT, TYINT, ID, PRINT o EOF", t.getTipo());
		}
		}
	}

	/**
	 * Implementa la regola: Dcl -> Ty id DclP
	 * 
	 * @return NodeDecl dell'AST
	 * @throws SyntacticException se viene riscontrato un errore sintattico
	 */
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
			throw new SyntacticException(t.getRiga(), "TYFLOAT o TYINT", t.getTipo());
		}
		}
	}

	/**
	 * Implementa le regole: Ty -> float Ty -> int
	 * 
	 * @return il tipo del terminale
	 * @throws SyntacticException se viene riscontrato un errore sintattico
	 */
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
			throw new SyntacticException(t.getRiga(), "TYFLOAT o TYINT", t.getTipo());
		}
		}
	}

	/**
	 * Implementa le regole: DclP -> ; DclP -> opAssign Exp ;
	 * 
	 * @return NodeExpr dell'AST
	 * @throws SyntacticException se viene riscontrato un errore sintattico
	 */
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
			throw new SyntacticException(t.getRiga(), "SEMI o OP_ASSIGN", t.getTipo());
		}
		}
	}

	/**
	 * Implementa le regole: Stm -> id opAssign Exp ; Stm -> print id ; Stm -> print
	 * id ;
	 * 
	 * @return NodeStm dell'AST
	 * @throws SyntacticException se viene riscontrato un errore sintattico
	 */
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
			switch (op.getVal()) {
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
			throw new SyntacticException(t.getRiga(), "ID o PRINT", t.getTipo());
		}
		}
	}

	/**
	 * Implementa le regole: Exp -> Tr ExpP
	 * 
	 * @return NodeExpr dell'AST
	 * @throws SyntacticException se viene riscontrato un errore sintattico
	 */
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
			throw new SyntacticException(t.getRiga(), "ID, FLOAT o INT", t.getTipo());
		}
		}
	}

	/**
	 * Implementa le regole: Exp -> + Tr ExpP Exp -> - Tr ExpP Exp -> ϵ
	 * 
	 * @return NodeExpr dell'AST
	 * @throws SyntacticException se viene riscontrato un errore sintattico
	 */
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
			throw new SyntacticException(t.getRiga(), "PLUS, MINUS o SEMI", t.getTipo());
		}
		}
	}

	/**
	 * Implementa le regole: Tr -> Val TrP
	 * 
	 * @return NodeExpr dell'AST
	 * @throws SyntacticException se viene riscontrato un errore sintattico
	 */
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
			throw new SyntacticException(t.getRiga(), "ID, FLOAT o INT", t.getTipo());
		}
		}
	}

	/**
	 * Implementa le regole: TrP -> * Val TrP TrP -> / Val TrP TrP -> ϵ
	 * 
	 * @return NodeExpr dell'AST
	 * @throws SyntacticException se viene riscontrato un errore sintattico
	 */
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
			throw new SyntacticException(t.getRiga(), "TIMES, DIVIDE, MINUS, PLUS o SEMI", t.getTipo());
		}
		}
	}

	/**
	 * Implementa le regole: Val -> intVal Val -> floatVal Val -> id
	 * 
	 * @return NodeExpr dell'AST
	 * @throws SyntacticException se viene riscontrato un errore sintattico
	 */
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
			throw new SyntacticException(t.getRiga(), "INT, FLOAT o ID", t.getTipo());
		}
		}
	}

}
