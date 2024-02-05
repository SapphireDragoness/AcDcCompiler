package symboltable;

import java.util.HashMap;

import ast.LangType;

public class SymbolTable {
	
	public static class Attributes {
		
		private LangType tipo;
		private String nome;
		private char registro;
		
		public Attributes(LangType tipo, String nome) {
			this.tipo = tipo;
			this.nome = nome;
		}

		public LangType getTipo() {
			return tipo;
		}
		
		public String getNome() {
			return nome;
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
	
	private static HashMap<String, Attributes> symbolTable;
	
	public static void inizializza() {
		symbolTable = new HashMap<>();
	}

	public static boolean enter(String id, Attributes entry) {
		if(symbolTable.containsKey(id))
			return false;
		symbolTable.put(id, entry);
		return true;
	}

	public static Attributes lookup(String id) {
		return symbolTable.get(id);
	}

	public static String toStr() {
		String str = "";
		
		for(var e : symbolTable.entrySet()) {
			str += "Chiave: " + e.getKey() + ", Valore: " + e.getValue().getTipo() + "\n";
		}
		return str;
	}

	public static int size() {
		return symbolTable.size();
	}
	
}