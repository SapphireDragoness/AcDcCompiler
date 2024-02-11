package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import eccezioni.SyntacticException;
import parser.Parser;
import scanner.Scanner;

class TestParser {

	String path = "src/test/data/testParser/";
	Parser parser;
	Scanner scanner;
	SyntacticException e;

	@Test
	void testParserCorretto1() throws FileNotFoundException, SyntacticException {
		scanner = new Scanner(path + "testParserCorretto1.txt");
		parser = new Parser(scanner);

		Assertions.assertDoesNotThrow(() -> parser.parse());
	}

	@Test
	void testParserCorretto2() throws FileNotFoundException, SyntacticException {
		scanner = new Scanner(path + "testParserCorretto2.txt");
		parser = new Parser(scanner);

		Assertions.assertDoesNotThrow(() -> parser.parse());
	}

	@Test
	void testParserCommenti() throws FileNotFoundException, SyntacticException {
		scanner = new Scanner(path + "testParserCommenti.txt");
		parser = new Parser(scanner);

		/* ignora correttamente i commenti */
		Assertions.assertDoesNotThrow(() -> parser.parse());
	}
	
	@Test
	void testParserEcc0() throws FileNotFoundException, SyntacticException {
		scanner = new Scanner(path + "testParserEcc_0.txt");
		parser = new Parser(scanner);

		e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
		assertEquals("Errore sintattico a riga 1: atteso OP_ASSIGN, ma è SEMI", e.getMessage());
	}

	@Test
	void testParserEcc1() throws FileNotFoundException, SyntacticException {
		scanner = new Scanner(path + "testParserEcc_1.txt");
		parser = new Parser(scanner);

		e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
		assertEquals("Errore sintattico a riga 2: atteso ID, FLOAT o INT, ma è TIMES", e.getMessage());
	}

	@Test
	void testParserEcc2() throws FileNotFoundException, SyntacticException {
		scanner = new Scanner(path + "testParserEcc_2.txt");
		parser = new Parser(scanner);

		e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
		assertEquals("Errore sintattico a riga 3: atteso TYFLOAT, TYINT, ID, PRINT o EOF, ma è INT",
				e.getMessage());
	}

	@Test
	void testParserEcc3() throws FileNotFoundException, SyntacticException {
		scanner = new Scanner(path + "testParserEcc_3.txt");
		parser = new Parser(scanner);

		e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
		assertEquals("Errore sintattico a riga 2: atteso OP_ASSIGN, ma è PLUS", e.getMessage());
	}

	@Test
	void testParserEcc4() throws FileNotFoundException, SyntacticException {
		scanner = new Scanner(path + "testParserEcc_4.txt");
		parser = new Parser(scanner);

		e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
		assertEquals("Errore sintattico a riga 2: atteso ID, ma è INT", e.getMessage());
	}

	@Test
	void testParserEcc5() throws FileNotFoundException, SyntacticException {
		scanner = new Scanner("src/test/data/testParser/testParserEcc_5.txt");
		parser = new Parser(scanner);

		e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
		assertEquals("Errore sintattico a riga 3: atteso ID, ma è INT", e.getMessage());
	}

	@Test
	void testParserEcc6() throws FileNotFoundException, SyntacticException {
		scanner = new Scanner(path + "testParserEcc_6.txt");
		parser = new Parser(scanner);

		e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
		assertEquals("Errore sintattico a riga 4: atteso ID, ma è TYFLOAT", e.getMessage());
	}

	@Test
	void testParserEcc7() throws FileNotFoundException, SyntacticException {
		scanner = new Scanner(path + "testParserEcc_7.txt");
		parser = new Parser(scanner);

		e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
		assertEquals("Errore sintattico a riga 2: atteso ID, ma è OP_ASSIGN", e.getMessage());
	}
	
	@Test
	void testParserEcc8() throws FileNotFoundException, SyntacticException {
		scanner = new Scanner(path + "testParserEcc_8.txt");
		parser = new Parser(scanner);

		e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
		assertEquals("Errore sintattico a riga 3: atteso ID, ma è INT", e.getMessage());
	}
	
	@Test
	void testParserEcc9() throws FileNotFoundException, SyntacticException {
		scanner = new Scanner(path + "testParserEcc_9.txt");
		parser = new Parser(scanner);

		e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
		assertEquals("Errore sintattico a riga 1: atteso ID, ma è SEMI", e.getMessage());
		e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
		assertEquals("Errore sintattico a riga 2: atteso ID, ma è TYFLOAT", e.getMessage());
		e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
		assertEquals("Errore sintattico a riga 2: atteso ID, ma è SEMI", e.getMessage());
		e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
		assertEquals("Errore sintattico a riga 2: atteso TYFLOAT, TYINT, ID, PRINT o EOF (PANIC MODE: cercherò un ';'), ma è SEMI", e.getMessage());
		Assertions.assertDoesNotThrow(() -> parser.parse());
	}

	@Test
	void testSoloDichPrint1() throws FileNotFoundException, SyntacticException {
		scanner = new Scanner(path + "testSoloDichPrint1.txt");
		parser = new Parser(scanner);

		Assertions.assertDoesNotThrow(() -> parser.parse());
	}

	@Test
	void testPrint1() throws FileNotFoundException, SyntacticException {
		scanner = new Scanner(path + "testPrint_1.txt");
		parser = new Parser(scanner);

		Assertions.assertDoesNotThrow(() -> parser.parse());
	}
	
	@Test
	void testPrint2() throws FileNotFoundException, SyntacticException {
		scanner = new Scanner(path + "testPrint_2.txt");
		parser = new Parser(scanner);

		Assertions.assertDoesNotThrow(() -> parser.parse());
	}

}
