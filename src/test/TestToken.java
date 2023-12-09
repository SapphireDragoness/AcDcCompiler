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
	
	@Test
	void testErroriNumbers() throws FileNotFoundException, LexicalException {
		scanner = new Scanner("src/test/data/testScanner/erroriNumbers.txt");
		
		Assertions.assertThrows(LexicalException.class, () -> scanner.nextToken());
		assertEquals("TYINT, riga: 1", scanner.nextToken().toString());
		Assertions.assertThrows(LexicalException.class, () -> scanner.nextToken());
		Assertions.assertThrows(LexicalException.class, () -> scanner.nextToken());
	}
	
	@Test
	void testEOF() throws FileNotFoundException, LexicalException {
		scanner = new Scanner("src/test/data/testScanner/testEOF.txt");
		
		assertEquals(TokenType.EOF, scanner.nextToken().getTipo());
	}
	
	@Test
	void testFLOAT() throws FileNotFoundException, LexicalException {
		scanner = new Scanner("src/test/data/testScanner/testFLOAT.txt");
		
		Assertions.assertThrows(LexicalException.class, () -> scanner.nextToken());
		Assertions.assertThrows(LexicalException.class, () -> scanner.nextToken());
		Assertions.assertThrows(LexicalException.class, () -> scanner.nextToken());
		
		Token t = scanner.nextToken();
		
		assertEquals("89.99999", t.getVal());
		assertEquals(TokenType.FLOAT, t.getTipo());
		assertEquals(TokenType.EOF, scanner.nextToken().getTipo());
	}
	
	@Test
	void testGenerale() throws FileNotFoundException, LexicalException {
		scanner = new Scanner("src/test/data/testScanner/testGenerale.txt");
		
		assertEquals("TYINT, riga: 1", scanner.nextToken().toString());
		assertEquals("ID, riga: 1", scanner.nextToken().toString());
		assertEquals("SEMI, riga: 1", scanner.nextToken().toString());
		
		//assertEquals("ID, riga: 2", scanner.nextToken().toString());
	}
	
	@Test
	void testId() throws FileNotFoundException, LexicalException {
		scanner = new Scanner("src/test/data/testScanner/testId.txt");
		
		scanner.nextToken();
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
		
		scanner.nextToken();
	}
	
	@Test
	void testOperators() throws FileNotFoundException, LexicalException {
		scanner = new Scanner("src/test/data/testScanner/testOperators.txt");
		
		scanner.nextToken();
	}

}
