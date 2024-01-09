package symboltable;

import java.util.HashMap;

import ast.LangType;

public class SymbolTable {
	
	private static HashMap<String, Attributes> symbolTable;
	
	public static class Attributes {
		
		private LangType tipo;
		
		
		
	}

	public static void init() {
		symbolTable = new HashMap<>();
	}

	public static boolean enter(String id, Attributes entry) {
// ......
	}

	public static Attributes lookup(String id) {
//......
	}

	public static String toStr() {
// ......
	}

	public static int size() {
// ......
	}
	
}