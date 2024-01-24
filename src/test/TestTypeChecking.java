package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import ast.NodeProgram;
import parser.Parser;
import parser.SyntacticException;
import scanner.Scanner;
import visitor.TypeCheckingVisitor;

class TestTypeChecking {
	 
	final String path = "src/test/data/testTypeChecking/";

//	@Test
//	void testDicRipetute() throws FileNotFoundException, SyntacticException {
//		NodeProgram nP = new Parser(new Scanner(path + "1_dicRipetute.txt")).parse();
//		var tcVisit = new TypeCheckingVisitor();
//		nP.accept(tcVisit);
//		tcVisit.getResType();
//		
//		
//	}
	
	@Test
	void testCorretto1() throws FileNotFoundException, SyntacticException {
		NodeProgram nP = new Parser(new Scanner(path + "5_corretto.txt")).parse();
		var tcVisit = new TypeCheckingVisitor();
		nP.accept(tcVisit);
		tcVisit.getResType();
		
		assertEquals(tcVisit.getLog(), "");
	}

}
