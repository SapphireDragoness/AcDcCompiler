package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import scanner.LexicalException;
import scanner.Scanner;
import token.Token;
import token.TokenType;

class TestToken {
	
	Scanner scanner;
	
//	@Test
//	void testErroriNumbers() throws FileNotFoundException, LexicalException {
//		scanner = new Scanner("src/test/data/testScanner/erroriNumbers.txt");
//		
//		Assertions.assertThrows(LexicalException.class, () -> scanner.nextToken());
//		Assertions.assertThrows(LexicalException.class, () -> scanner.nextToken());
//		Assertions.assertThrows(LexicalException.class, () -> scanner.nextToken());
//	}
	
	@Test
	void testEOF() throws FileNotFoundException, LexicalException {
		scanner = new Scanner("src/test/data/testScanner/testEOF.txt");
		
		assertEquals(TokenType.EOF, scanner.nextToken().getTipo());
	}
	
	@Test
	void testFLOAT() throws FileNotFoundException, LexicalException {
		scanner = new Scanner("src/test/data/testScanner/testFLOAT.txt");
		
		Assertions.assertThrows(LexicalException.class, () -> scanner.nextToken());
		
		Token t = scanner.nextToken();
		// un float senza cifre decimali è considerato comunque come FLOAT
		assertEquals("0.", t.getVal());
		assertEquals(TokenType.FLOAT, t.getTipo());
		t = scanner.nextToken();
		assertEquals("98.", t.getVal());
		assertEquals(TokenType.FLOAT, t.getTipo());
		t = scanner.nextToken();
		assertEquals("89.99999", t.getVal());
		assertEquals(TokenType.FLOAT, t.getTipo());
		assertEquals(TokenType.EOF, scanner.nextToken().getTipo());
	}
	
	@Test
	void testGenerale() throws FileNotFoundException, LexicalException {
		scanner = new Scanner("src/test/data/testScanner/testGenerale.txt");
		
		assertEquals("TYINT, riga: 1", scanner.nextToken().toString());
		assertEquals("ID, riga: 1, temp", scanner.nextToken().toString());
		assertEquals("SEMI, riga: 1", scanner.nextToken().toString());
		
		assertEquals("ID, riga: 2, temp", scanner.nextToken().toString());
		assertEquals("PLUS, riga: 2", scanner.nextToken().toString());
		assertEquals("OP_ASSIGN, riga: 2", scanner.nextToken().toString());
		assertEquals("FLOAT, riga: 2, 5.", scanner.nextToken().toString());
		assertEquals("SEMI, riga: 2", scanner.nextToken().toString());
		
		assertEquals("TYFLOAT, riga: 4", scanner.nextToken().toString());
		assertEquals("ID, riga: 4, b", scanner.nextToken().toString());
		assertEquals("SEMI, riga: 4", scanner.nextToken().toString());
		
		assertEquals("ID, riga: 5, b", scanner.nextToken().toString());
		assertEquals("OP_ASSIGN, riga: 5", scanner.nextToken().toString());
		assertEquals("ID, riga: 5, temp", scanner.nextToken().toString());
		assertEquals("PLUS, riga: 5", scanner.nextToken().toString());
		assertEquals("FLOAT, riga: 5, 3.2", scanner.nextToken().toString());
		assertEquals("SEMI, riga: 5", scanner.nextToken().toString());
		
		assertEquals("PRINT, riga: 6", scanner.nextToken().toString());
		assertEquals("ID, riga: 6, b", scanner.nextToken().toString());
		assertEquals("SEMI, riga: 6", scanner.nextToken().toString());
		
		assertEquals("EOF, riga: 7", scanner.nextToken().toString());
	}
	
	@Test
	void testId() throws FileNotFoundException, LexicalException {
		scanner = new Scanner("src/test/data/testScanner/testId.txt");
		
		assertEquals(TokenType.ID, scanner.nextToken().getTipo());
		assertEquals(TokenType.ID, scanner.nextToken().getTipo());
		assertEquals(TokenType.ID, scanner.nextToken().getTipo());
		assertEquals(TokenType.ID, scanner.nextToken().getTipo());
	}
	
	@Test
	void testIdKeyWords() throws FileNotFoundException, LexicalException {
		scanner = new Scanner("src/test/data/testScanner/testIdKeyWords.txt");
		
		scanner.nextToken();
	}
	
	@Test
	void testINT() throws FileNotFoundException, LexicalException {
		scanner = new Scanner("src/test/data/testScanner/testINT.txt");
		
		assertEquals("698", scanner.nextToken().getVal());
		assertEquals("560099", scanner.nextToken().getVal());
		assertEquals("1234", scanner.nextToken().getVal());
		assertEquals(TokenType.EOF, scanner.nextToken().getTipo());
	}
	
	@Test
	void testKeywords() throws FileNotFoundException, LexicalException {
		scanner = new Scanner("src/test/data/testScanner/testKeywords.txt");
		
		assertEquals(TokenType.PRINT, scanner.nextToken().getTipo());
		assertEquals(TokenType.TYFLOAT, scanner.nextToken().getTipo());
		assertEquals(TokenType.TYINT, scanner.nextToken().getTipo());
	}
	
	@Test
	void testOperators() throws FileNotFoundException, LexicalException {
		scanner = new Scanner("src/test/data/testScanner/testOperators.txt");
		
		scanner.nextToken();
	}
	
	@Test
	void peekToken () throws LexicalException, FileNotFoundException {
		scanner = new Scanner("src/test/data/testScanner/testGenerale.txt");
		
		assertEquals(scanner.peekToken().getTipo(), TokenType.TYINT );
		assertEquals(scanner.nextToken().getTipo(), TokenType.TYINT );
		assertEquals(scanner.peekToken().getTipo(), TokenType.ID );
		assertEquals(scanner.peekToken().getTipo(), TokenType.ID );
		
		Token t = scanner.nextToken();
		
		assertEquals(t.getTipo(), TokenType.ID);
		assertEquals(t.getRiga(), 1);
		assertEquals(t.getVal(), "temp");
	}

}
