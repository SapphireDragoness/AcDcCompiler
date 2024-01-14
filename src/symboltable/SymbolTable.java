package symboltable;

import java.util.HashMap;

import ast.LangType;

public class SymbolTable {
	
	private static HashMap<String, Attributes> symbolTable;
	
	public static class Attributes {
		
		private LangType tipo;
		
		public Attributes(LangType tipo) {
			this.setTipo(tipo);
		}

		public LangType getTipo() {
			return tipo;
		}

		public void setTipo(LangType tipo) {
			this.tipo = tipo;
		}
		
	}

	public static void init() {
		symbolTable = new HashMap<>();
	}

	public static boolean enter(String id, Attributes entry) {
		if(symbolTable.get(id) != null) {
			return false;
		}
		symbolTable.put(id, entry);
		return true;
	}

	public static Attributes lookup(String id) {
		return symbolTable.get(id);
	}

	public static String toStr() {
		StringBuilder str = new StringBuilder();
		
		for(var e : symbolTable.entrySet()) {
			str.append("Chiave").append(e.getKey()).append("\t").append("Valore").append(e.getValue()).append("\n");
		}
		return str.toString();
	}

	public static int size() {
		return symbolTable.size();
	}
	
}