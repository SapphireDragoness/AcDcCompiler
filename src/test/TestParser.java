package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ast.NodeProgram;
import parser.Parser;
import parser.SyntacticException;
import scanner.Scanner;

class TestParser {

	String path = "src/test/data/testParser/";
	Parser parser;
	Scanner scanner;
	SyntacticException e;
	
	@Test
	void testParserCommenti() throws FileNotFoundException, SyntacticException {
		scanner = new Scanner(path + "testParserCommenti.txt");
		parser = new Parser(scanner);
		NodeProgram nodePrg = parser.parse();

		/* ignora correttamente i commenti */
		assertEquals(
				"NodeProgram [decSts: [NodeAssign [id: NodeId [name: b], expr: NodeBinOp [op: PLUS, left: NodeDeref [id: NodeId [name: a]], right: NodeCost [value: 5, type: INT]]], NodeAssign [id: NodeId [name: a], expr: NodeBinOp [op: PLUS, left: NodeCost [value: 5, type: INT], right: NodeCost [value: 3, type: INT]]], NodeDecl [id: NodeId [name: floati], type: INT, init: null], NodeDecl [id: NodeId [name: numberfloat], type: FLOAT, init: null], NodePrint [id: NodeId [name: stampa]]]]",
				nodePrg.toString());
	}

	@Test
	void testParserCorretto1() throws FileNotFoundException, SyntacticException {
		scanner = new Scanner(path + "testParserCorretto1.txt");
		parser = new Parser(scanner);
		NodeProgram nodePrg = parser.parse();

		assertEquals(
				"NodeProgram [decSts: [NodeAssign [id: NodeId [name: b], expr: NodeBinOp [op: PLUS, left: NodeDeref [id: NodeId [name: a]], right: NodeCost [value: 5, type: INT]]], NodeAssign [id: NodeId [name: a], expr: NodeBinOp [op: PLUS, left: NodeCost [value: 5, type: INT], right: NodeCost [value: 3, type: INT]]], NodeDecl [id: NodeId [name: floati], type: INT, init: null], NodeDecl [id: NodeId [name: numberfloat], type: FLOAT, init: null], NodePrint [id: NodeId [name: stampa]]]]",
				nodePrg.toString());
	}

	@Test
	void testParserCorretto2() throws FileNotFoundException, SyntacticException {
		scanner = new Scanner(path + "testParserCorretto2.txt");
		parser = new Parser(scanner);

		Assertions.assertDoesNotThrow(() -> parser.parse());
	}

	@Test
	void testParserEcc0() throws FileNotFoundException, SyntacticException {
		scanner = new Scanner(path + "testParserEcc_0.txt");
		parser = new Parser(scanner);

		e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
		assertEquals("Aspettavo OP_ASSIGN alla riga 1 ma è SEMI.", e.getMessage());
	}

	@Test
	void testParserEcc1() throws FileNotFoundException, SyntacticException {
		scanner = new Scanner(path + "testParserEcc_1.txt");
		parser = new Parser(scanner);

		e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
		assertEquals("Il token TIMES alla riga 2 non è un Tr, dovrebbe essere ID, FLOAT o INT.", e.getMessage());
	}

	@Test
	void testParserEcc2() throws FileNotFoundException, SyntacticException {
		scanner = new Scanner(path + "testParserEcc_2.txt");
		parser = new Parser(scanner);

		e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
		assertEquals("Il token INT alla riga 3 non è un DSs, dovrebbe essere TYFLOAT, TYINT, ID, PRINT o EOF.",
				e.getMessage());
	}

	@Test
	void testParserEcc3() throws FileNotFoundException, SyntacticException {
		scanner = new Scanner(path + "testParserEcc_3.txt");
		parser = new Parser(scanner);

		e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
		assertEquals("Aspettavo OP_ASSIGN alla riga 2 ma è PLUS.", e.getMessage());
	}

	@Test
	void testParserEcc4() throws FileNotFoundException, SyntacticException {
		scanner = new Scanner(path + "testParserEcc_4.txt");
		parser = new Parser(scanner);

		e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
		assertEquals("Aspettavo ID alla riga 2 ma è INT.", e.getMessage());
	}

	@Test
	void testParserEcc5() throws FileNotFoundException, SyntacticException {
		scanner = new Scanner("src/test/data/testParser/testParserEcc_5.txt");
		parser = new Parser(scanner);

		e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
		assertEquals("Aspettavo ID alla riga 3 ma è INT.", e.getMessage());
	}

	@Test
	void testParserEcc6() throws FileNotFoundException, SyntacticException {
		scanner = new Scanner(path + "testParserEcc_6.txt");
		parser = new Parser(scanner);

		e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
		assertEquals("Aspettavo ID alla riga 4 ma è TYFLOAT.", e.getMessage());
	}

	@Test
	void testParserEcc7() throws FileNotFoundException, SyntacticException {
		scanner = new Scanner(path + "testParserEcc_7.txt");
		parser = new Parser(scanner);

		e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
		assertEquals("Aspettavo ID alla riga 2 ma è OP_ASSIGN.", e.getMessage());
	}

	@Test
	void testSoloDichPrint1() throws FileNotFoundException, SyntacticException {
		scanner = new Scanner(path + "testSoloDichPrint1.txt");
		parser = new Parser(scanner);

		Assertions.assertDoesNotThrow(() -> parser.parse());
	}

	@Test
	void testPrint() throws FileNotFoundException, SyntacticException {
		scanner = new Scanner(path + "testPrint.txt");
		parser = new Parser(scanner);

		Assertions.assertDoesNotThrow(() -> parser.parse());
	}

}
