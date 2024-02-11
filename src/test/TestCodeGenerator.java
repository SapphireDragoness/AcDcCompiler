package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import ast.NodeProgram;
import eccezioni.SyntacticException;
import parser.Parser;
import scanner.Scanner;
import visitor.CodeGeneratorVisitor;
import visitor.TypeCheckingVisitor;

class TestCodeGenerator {
	
	final String path = "src/test/data/testCodeGenerator/";

	@Test
	void testGenerale1() throws FileNotFoundException, SyntacticException {
		NodeProgram nP = new Parser(new Scanner(path + "1_generale.txt")).parse();
		var tcVisit = new TypeCheckingVisitor();
		nP.accept(tcVisit);
		var cgVisit = new CodeGeneratorVisitor();
		nP.accept(cgVisit);
		
		assertEquals(cgVisit.getLog(), "");
		assertEquals(cgVisit.getCodiceGenerato(), "10 sa 0.5 sb 5 k la lb * sb 0 k lb p P");
	}
	
	@Test
	void testGenerale2() throws FileNotFoundException, SyntacticException {
		NodeProgram nP = new Parser(new Scanner(path + "2_generale.txt")).parse();
		var tcVisit = new TypeCheckingVisitor();
		nP.accept(tcVisit);
		var cgVisit = new CodeGeneratorVisitor();
		nP.accept(cgVisit);
		
		assertEquals(cgVisit.getLog(), "");
		assertEquals(cgVisit.getCodiceGenerato(), "4.6 sa la 1.9 + sb lb p P");
	}
	
	@Test
	void testGenerale3() throws FileNotFoundException, SyntacticException {
		NodeProgram nP = new Parser(new Scanner(path + "3_generale.txt")).parse();
		var tcVisit = new TypeCheckingVisitor();
		nP.accept(tcVisit);
		var cgVisit = new CodeGeneratorVisitor();
		nP.accept(cgVisit);
		
		assertEquals(cgVisit.getLog(), "");
		assertEquals(cgVisit.getCodiceGenerato(), "3.5 5 k 1 + sa 0 k  10 3 / sb la lb - sa la p P");
	}
	
	@Test
	void testAssign1() throws FileNotFoundException, SyntacticException {
		NodeProgram nP = new Parser(new Scanner(path + "1_assign.txt")).parse();
		var tcVisit = new TypeCheckingVisitor();
		nP.accept(tcVisit);
		var cgVisit = new CodeGeneratorVisitor();
		nP.accept(cgVisit);
		
		assertEquals(cgVisit.getLog(), "");
		assertEquals(cgVisit.getCodiceGenerato(), "1.0 5 k 6 / sa 0 k la p P");
	}
	
	@Test
	void testAssign2() throws FileNotFoundException, SyntacticException {
		NodeProgram nP = new Parser(new Scanner(path + "2_assign.txt")).parse();
		var tcVisit = new TypeCheckingVisitor();
		nP.accept(tcVisit);
		var cgVisit = new CodeGeneratorVisitor();
		nP.accept(cgVisit);
		
		
		assertEquals(cgVisit.getLog(), "");
		assertEquals(cgVisit.getCodiceGenerato(), "1 6 / sa la p P");
	}
	
	@Test
	void testConversione() throws FileNotFoundException, SyntacticException {
		NodeProgram nP = new Parser(new Scanner(path + "1_conversione.txt")).parse();
		var tcVisit = new TypeCheckingVisitor();
		nP.accept(tcVisit);
		var cgVisit = new CodeGeneratorVisitor();
		nP.accept(cgVisit);
		
		
		assertEquals(cgVisit.getLog(), "");
		assertEquals(cgVisit.getCodiceGenerato(), "5.3 sa la 34 + sa la p P");
	}
	
	@Test
	void testRegistriFiniti() throws FileNotFoundException, SyntacticException {
		NodeProgram nP = new Parser(new Scanner(path + "registriFiniti.txt")).parse();
		var tcVisit = new TypeCheckingVisitor();
		nP.accept(tcVisit);
		var cgVisit = new CodeGeneratorVisitor();
		nP.accept(cgVisit);
		
		/* il log contiene errore e non viene generato codice */
		assertEquals(cgVisit.getLog(), "Numero massimo di registri superato.");
		assertEquals(cgVisit.getCodiceGenerato(), "");
	}

}
