package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import eccezioni.LexicalException;
import scanner.Scanner;
import token.Token;
import token.TokenType;

class TestScanner {
	
	final String path = "src/test/data/testScanner/";

	Scanner scanner;
	LexicalException e;

	@Test
	void testErroriNumbers() throws FileNotFoundException, LexicalException {
		scanner = new Scanner("src/test/data/testScanner/erroriNumbers.txt");

		e = Assertions.assertThrows(LexicalException.class, () -> scanner.nextToken());
		assertEquals("Errore lessicale a riga 1: sequenza di caratteri '00' non riconosciuta", e.getMessage());
		e = Assertions.assertThrows(LexicalException.class, () -> scanner.nextToken());
		assertEquals("Errore lessicale a riga 2: sequenza di caratteri '123a' non riconosciuta", e.getMessage());
		e = Assertions.assertThrows(LexicalException.class, () -> scanner.nextToken());
		assertEquals("Errore lessicale a riga 3: sequenza di caratteri '12.a' non riconosciuta", e.getMessage());
	}
	
	@Test
	void testErroriId() throws FileNotFoundException, LexicalException {
		scanner = new Scanner(path + "erroriId.txt");

		e = Assertions.assertThrows(LexicalException.class, () -> scanner.nextToken());
		assertEquals("Errore lessicale a riga 1: sequenza di caratteri 'number1' non riconosciuta", e.getMessage());
		e = Assertions.assertThrows(LexicalException.class, () -> scanner.nextToken());
		assertEquals("Errore lessicale a riga 2: sequenza di caratteri '34number' non riconosciuta", e.getMessage());
		e = Assertions.assertThrows(LexicalException.class, () -> scanner.nextToken());
		assertEquals("Errore lessicale a riga 3: sequenza di caratteri 'float56' non riconosciuta", e.getMessage());
	}
	
	@Test
	void testErroriOp() throws FileNotFoundException, LexicalException {
		scanner = new Scanner(path + "erroriOp.txt");

		e = Assertions.assertThrows(LexicalException.class, () -> scanner.nextToken());
		assertEquals("Errore lessicale a riga 1: sequenza di caratteri '++' non riconosciuta", e.getMessage());
		e = Assertions.assertThrows(LexicalException.class, () -> scanner.nextToken());
		assertEquals("Errore lessicale a riga 2: sequenza di caratteri '==' non riconosciuta", e.getMessage());
		e = Assertions.assertThrows(LexicalException.class, () -> scanner.nextToken());
		assertEquals("Errore lessicale a riga 2: sequenza di caratteri ';;' non riconosciuta", e.getMessage());
		e = Assertions.assertThrows(LexicalException.class, () -> scanner.nextToken());
		assertEquals("Errore lessicale a riga 3: sequenza di caratteri '**' non riconosciuta", e.getMessage());
		e = Assertions.assertThrows(LexicalException.class, () -> scanner.nextToken());
		assertEquals("Errore lessicale a riga 3: sequenza di caratteri '//' non riconosciuta", e.getMessage());
		e = Assertions.assertThrows(LexicalException.class, () -> scanner.nextToken());
		assertEquals("Errore lessicale a riga 4: sequenza di caratteri '/+' non riconosciuta", e.getMessage());
		e = Assertions.assertThrows(LexicalException.class, () -> scanner.nextToken());
		assertEquals("Errore lessicale a riga 5: sequenza di caratteri '=*' non riconosciuta", e.getMessage());
	}

	@Test
	void testEOF() throws FileNotFoundException, LexicalException {
		scanner = new Scanner(path + "testEOF.txt");

		assertEquals(TokenType.EOF, scanner.nextToken().getTipo());
	}

	@Test
	void testFLOAT() throws FileNotFoundException, LexicalException {
		scanner = new Scanner("src/test/data/testScanner/testFLOAT.txt");
		Token t = scanner.nextToken();

		assertEquals("098.8095", t.getVal());
		assertEquals(TokenType.FLOAT, t.getTipo());
		t = scanner.nextToken();
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
		scanner = new Scanner(path + "testGenerale.txt");

		assertEquals("TYINT, riga: 1", scanner.nextToken().toString());
		assertEquals("ID, riga: 1, temp", scanner.nextToken().toString());
		assertEquals("SEMI, riga: 1", scanner.nextToken().toString());

		assertEquals("ID, riga: 2, temp", scanner.nextToken().toString());
		assertEquals("OP_ASSIGN, riga: 2, +=", scanner.nextToken().toString());
		assertEquals("FLOAT, riga: 2, 5.", scanner.nextToken().toString());
		assertEquals("SEMI, riga: 2", scanner.nextToken().toString());

		assertEquals("TYFLOAT, riga: 4", scanner.nextToken().toString());
		assertEquals("ID, riga: 4, b", scanner.nextToken().toString());
		assertEquals("SEMI, riga: 4", scanner.nextToken().toString());

		assertEquals("ID, riga: 5, b", scanner.nextToken().toString());
		assertEquals("OP_ASSIGN, riga: 5, =", scanner.nextToken().toString());
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
	void testGeneraleNoSpazi() throws FileNotFoundException, LexicalException {
		scanner = new Scanner(path + "testGeneraleNoSpazi.txt");

		/* tokenizza correttamente id seguiti immediatamente da operatori (es. temp+=5) */
		assertEquals("TYINT, riga: 1", scanner.nextToken().toString());
		assertEquals("ID, riga: 1, temp", scanner.nextToken().toString());
		assertEquals("SEMI, riga: 1", scanner.nextToken().toString());

		assertEquals("ID, riga: 2, temp", scanner.nextToken().toString());
		assertEquals("OP_ASSIGN, riga: 2, +=", scanner.nextToken().toString());
		assertEquals("FLOAT, riga: 2, 5.", scanner.nextToken().toString());
		assertEquals("SEMI, riga: 2", scanner.nextToken().toString());

		assertEquals("TYFLOAT, riga: 4", scanner.nextToken().toString());
		assertEquals("ID, riga: 4, b", scanner.nextToken().toString());
		assertEquals("SEMI, riga: 4", scanner.nextToken().toString());

		assertEquals("ID, riga: 5, b", scanner.nextToken().toString());
		assertEquals("OP_ASSIGN, riga: 5, =", scanner.nextToken().toString());
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
		scanner = new Scanner(path + "testId.txt");

		assertEquals(TokenType.ID, scanner.nextToken().getTipo());
		assertEquals(TokenType.ID, scanner.nextToken().getTipo());
		assertEquals(TokenType.ID, scanner.nextToken().getTipo());
		assertEquals(TokenType.ID, scanner.nextToken().getTipo());
	}

	@Test
	void testIdKeyWords() throws FileNotFoundException, LexicalException {
		scanner = new Scanner(path + "testIdKeyWords.txt");

		assertEquals(TokenType.TYINT, scanner.nextToken().getTipo());
		assertEquals(TokenType.ID, scanner.nextToken().getTipo());
		assertEquals(TokenType.TYFLOAT, scanner.nextToken().getTipo());
		assertEquals(TokenType.PRINT, scanner.nextToken().getTipo());
		assertEquals(TokenType.ID, scanner.nextToken().getTipo());
		assertEquals(TokenType.ID, scanner.nextToken().getTipo());
		assertEquals(TokenType.TYINT, scanner.nextToken().getTipo());
		assertEquals(TokenType.ID, scanner.nextToken().getTipo());
	}

	@Test
	void testINT() throws FileNotFoundException, LexicalException {
		scanner = new Scanner(path + "testINT.txt");

		assertEquals("698", scanner.nextToken().getVal());
		assertEquals("560099", scanner.nextToken().getVal());
		assertEquals("1234", scanner.nextToken().getVal());
		assertEquals(TokenType.EOF, scanner.nextToken().getTipo());
	}

	@Test
	void testKeywords() throws FileNotFoundException, LexicalException {
		scanner = new Scanner(path + "testKeywords.txt");

		assertEquals(TokenType.PRINT, scanner.nextToken().getTipo());
		assertEquals(TokenType.TYFLOAT, scanner.nextToken().getTipo());
		assertEquals(TokenType.TYINT, scanner.nextToken().getTipo());
	}

	@Test
	void testOperators() throws FileNotFoundException, LexicalException {
		scanner = new Scanner(path + "testOperators.txt");

		assertEquals("PLUS, riga: 1", scanner.nextToken().toString());
		assertEquals("OP_ASSIGN, riga: 1, /=", scanner.nextToken().toString());
		
		assertEquals("MINUS, riga: 2", scanner.nextToken().toString());
		assertEquals("TIMES, riga: 2", scanner.nextToken().toString());
		
		assertEquals("DIVIDE, riga: 3", scanner.nextToken().toString());
		
		assertEquals("OP_ASSIGN, riga: 5, +=", scanner.nextToken().toString());
		
		assertEquals("OP_ASSIGN, riga: 6, =", scanner.nextToken().toString());
		assertEquals("OP_ASSIGN, riga: 6, -=", scanner.nextToken().toString());
		
		assertEquals("MINUS, riga: 8", scanner.nextToken().toString());
		assertEquals("OP_ASSIGN, riga: 8, =", scanner.nextToken().toString());
		assertEquals("OP_ASSIGN, riga: 8, *=", scanner.nextToken().toString());
		
		assertEquals("SEMI, riga: 10", scanner.nextToken().toString());
		assertEquals("EOF, riga: 10", scanner.nextToken().toString());
	}

	@Test
	void testPeekToken() throws LexicalException, FileNotFoundException {
		scanner = new Scanner(path + "testGenerale.txt");

		assertEquals(scanner.peekToken().getTipo(), TokenType.TYINT);
		assertEquals(scanner.nextToken().getTipo(), TokenType.TYINT);
		assertEquals(scanner.peekToken().getTipo(), TokenType.ID);
		assertEquals(scanner.peekToken().getTipo(), TokenType.ID);

		Token t = scanner.nextToken();

		assertEquals(t.getTipo(), TokenType.ID);
		assertEquals(t.getRiga(), 1);
		assertEquals(t.getVal(), "temp");
	}
	
	@Test
	void testCommenti() throws FileNotFoundException, LexicalException {
		scanner = new Scanner(path + "testCommenti.txt");

		/* ignora correttamente i commenti */
		assertEquals("TYINT, riga: 2", scanner.nextToken().toString());
		assertEquals("ID, riga: 2, temp", scanner.nextToken().toString());
		assertEquals("SEMI, riga: 2", scanner.nextToken().toString());

		assertEquals("ID, riga: 3, temp", scanner.nextToken().toString());
		assertEquals("OP_ASSIGN, riga: 3, +=", scanner.nextToken().toString());
		assertEquals("FLOAT, riga: 3, 5.", scanner.nextToken().toString());
		assertEquals("SEMI, riga: 3", scanner.nextToken().toString());

		assertEquals("TYFLOAT, riga: 6", scanner.nextToken().toString());
		assertEquals("ID, riga: 6, b", scanner.nextToken().toString());
		assertEquals("SEMI, riga: 6", scanner.nextToken().toString());

		assertEquals("ID, riga: 7, b", scanner.nextToken().toString());
		assertEquals("OP_ASSIGN, riga: 7, =", scanner.nextToken().toString());
		assertEquals("ID, riga: 7, temp", scanner.nextToken().toString());
		assertEquals("PLUS, riga: 7", scanner.nextToken().toString());
		assertEquals("FLOAT, riga: 7, 3.2", scanner.nextToken().toString());
		assertEquals("SEMI, riga: 7", scanner.nextToken().toString());

		assertEquals("PRINT, riga: 8", scanner.nextToken().toString());
		assertEquals("ID, riga: 8, b", scanner.nextToken().toString());
		assertEquals("SEMI, riga: 8", scanner.nextToken().toString());

		assertEquals("EOF, riga: 12", scanner.nextToken().toString());
	}

}
