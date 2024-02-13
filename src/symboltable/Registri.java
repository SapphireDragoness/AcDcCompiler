package symboltable;

import java.util.ArrayList;

import eccezioni.CodeGeneratorException;

public class Registri {

	static ArrayList<Character> caratteri;
	
	public static void inizializza() {
		caratteri = new ArrayList<Character>();
		for (char c = 'a'; c <= 'z'; c++) {
			caratteri.add(c);
		}
	}
	
	public static char newRegister() throws CodeGeneratorException {
		if(caratteri.isEmpty())
			throw new CodeGeneratorException("Numero massimo di registri superato.");
		return caratteri.remove(0);
	}
	
}
