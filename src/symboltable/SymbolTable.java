package symboltable;

import java.util.HashMap;

import ast.LangType;

public class SymbolTable {
	
	private static HashMap<String, Attributes> symbolTable = new HashMap<String, Attributes>();
	
	public static class Attributes {
		
		private LangType tipo;
		private char registro;

		public LangType getTipo() {
			return tipo;
		}

		public char getRegistro() {
			return registro;
		}

		public void setTipo(LangType tipo) {
			this.tipo = tipo;
		}

		public void setRegistro(char registro) {
			this.registro = registro;
		}	
		
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
		String str = "";
		
		for(var e : symbolTable.entrySet()) {
			str += "Chiave" + e.getKey() + "\t" + "Valore" + e.getValue() + "\n";
		}
		return str;
	}

	public static int size() {
		return symbolTable.size();
	}
	
}