package symboltable;

import java.util.HashMap;

import ast.LangType;

/**
 * Implementa la Sybol Table, che contiene i nomi utilizzati dal programma.
 * 
 * @author Linda Monfermoso, 20028464
 */
public class SymbolTable {
	
	/**
	 * Implementa gli attributi che andranno inseriti nella Symbol Table.
	 */
	public static class Attributes {
		
		private LangType tipo;
		private String nome;
		private char registro;
		
		/**
		 * Costruttore dell'attributo.
		 * 
		 * @param tipo il tipo dell'attributo
		 * @param nome il nome dell'attributo
		 */
		public Attributes(LangType tipo, String nome) {
			this.tipo = tipo;
			this.nome = nome;
		}

		/**
		 * Restituisce il tipo dell'attributo.
		 * 
		 * @return il tipo dell'attributo
		 */
		public LangType getTipo() {
			return tipo;
		}
		
		/**
		 * Restituisce il nome dell'attributo.
		 * 
		 * @return il nome dell'attributo
		 */
		public String getNome() {
			return nome;
		}

		/**
		 * Restituisce il registro associato all'attributo.
		 * 
		 * @return il registro
		 */
		public char getRegistro() {
			return registro;
		}

		/**
		 * Imposta il registro dell'attributo.
		 * 
		 * @param registro il registro da impostare
		 */
		public void setRegistro(char registro) {
			this.registro = registro;
		}	
		
	}
	
	private static HashMap<String, Attributes> symbolTable;
	
	/**
	 * Inizializza l'hashmap della Symbol Table.
	 */
	public static void inizializza() {
		symbolTable = new HashMap<>();
	}

	/**
	 * Inserisce un attributo nella Symbol Table.
	 * 
	 * @param id l'id dell'attributo da inserire
	 * @param entry l'attributo da inserire
	 * @return true se l'attributo è stato inserito correttamente, false altrimenti
	 */
	public static boolean enter(String id, Attributes entry) {
		if(symbolTable.containsKey(id))
			return false;
		symbolTable.put(id, entry);
		return true;
	}

	/**
	 * Restituisce un attributo.
	 * 
	 * @param id l'id dell'attributo da cercare
	 * @return l'attributo cercato
	 */
	public static Attributes lookup(String id) {
		return symbolTable.get(id);
	}

	/**
	 * Restituisce una rappresentazione testuale della Symbol Table.
	 * 
	 * @return rappresentazione testuale della Symbol Table
	 */
	public static String toStr() {
		String str = "";
		
		for(var e : symbolTable.entrySet()) {
			str += "ID: " + e.getKey() + ", Tipo: " + e.getValue().getTipo() + "\n";
		}
		return str;
	}
	
}