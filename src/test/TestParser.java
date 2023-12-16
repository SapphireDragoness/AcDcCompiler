package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import parser.Parser;
import parser.SyntacticException;
import scanner.LexicalException;
import scanner.Scanner;

class TestParser {
	
	Parser parser;
	Scanner scanner;
	SyntacticException e;

//	@Test
//	void testParserCorretto1() throws FileNotFoundException, SyntacticException {
//		scanner = new Scanner("src/test/data/testParser/testParserCorretto1.txt");
//		parser = new Parser(scanner);
//		
//		Assertions.assertDoesNotThrow(() ->
//			parser.parse()
//		);	
//	}
//	
//	@Test
//	void testParserCorretto2() throws FileNotFoundException, SyntacticException {
//		scanner = new Scanner("src/test/data/testParser/testParserCorretto2.txt");
//		parser = new Parser(scanner);
//		
//		Assertions.assertDoesNotThrow(() ->
//			parser.parse()
//		);
//	}
	
	@Test
	void testParserEcc0() throws FileNotFoundException, SyntacticException {
		scanner = new Scanner("src/test/data/testParser/testParserEcc_0.txt");
		parser = new Parser(scanner);
		
		e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
		assertEquals("Aspettavo OP_ASSIGN alla riga 1 ma è SEMI.", e.getMessage());
	}
	
	@Test
	void testParserEcc1() throws FileNotFoundException, SyntacticException {
		scanner = new Scanner("src/test/data/testParser/testParserEcc_1.txt");
		parser = new Parser(scanner);
		
		e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
		assertEquals("Il token TIMES alla riga 2 non è un Tr, dovrebbe essere ID, FLOAT o INT.", e.getMessage());
	}
	
	@Test
	void testParserEcc2() throws FileNotFoundException, SyntacticException {
		scanner = new Scanner("src/test/data/testParser/testParserEcc_2.txt");
		parser = new Parser(scanner);
		
		
		e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
		assertEquals("Il token INT alla riga 3 non è un DSs, dovrebbe essere TYFLOAT, TYINT, ID, PRINT o EOF.", e.getMessage());
	}
	
	@Test
	void testParserEcc3() throws FileNotFoundException, SyntacticException {
		scanner = new Scanner("src/test/data/testParser/testParserEcc_3.txt");
		parser = new Parser(scanner);
		
		e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
		assertEquals("Aspettavo OP_ASSIGN alla riga 2 ma è PLUS.", e.getMessage());
	}
	
	@Test
	void testParserEcc4() throws FileNotFoundException, SyntacticException {
		scanner = new Scanner("src/test/data/testParser/testParserEcc_4.txt");
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
		scanner = new Scanner("src/test/data/testParser/testParserEcc_6.txt");
		parser = new Parser(scanner);
		
		e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
		assertEquals("Aspettavo ID alla riga 4 ma è TYFLOAT.", e.getMessage());
	}
	
	@Test
	void testParserEcc7() throws FileNotFoundException, SyntacticException {
		scanner = new Scanner("src/test/data/testParser/testParserEcc_7.txt");
		parser = new Parser(scanner);
		
		e = Assertions.assertThrows(SyntacticException.class, () -> parser.parse());
		assertEquals("Aspettavo ID alla riga 2 ma è OP_ASSIGN.", e.getMessage());
	}
	
	@Test
	void testSoloDichPrint1() throws FileNotFoundException, SyntacticException {
		scanner = new Scanner("src/test/data/testParser/testSoloDichPrint1.txt");
		parser = new Parser(scanner);
		
		Assertions.assertDoesNotThrow(() -> 
			parser.parse()
		);
	}
	
}
