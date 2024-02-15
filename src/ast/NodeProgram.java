package ast;

import java.util.ArrayList;

import visitor.IVisitor;

/**
 * Implementa il nodo NodeProgram dell'AST.
 * 
 * @author Linda Monfermoso, 20028464
 * 
 */
public class NodeProgram extends NodeAST {
	
	private final ArrayList<NodeDecSt> decSts;
	
	/**
	 * Costruttore per NodeProgram.
	 * 
	 * @param decSts la lista di dichiarazioni
	 */
	public NodeProgram(ArrayList<NodeDecSt> decSts) {
		this.decSts = decSts;
	}

	/**
	 * Restituisce la lista di dichiarazioni
	 * 
	 * @return la lista di dichiarazioni
	 */
	public ArrayList<NodeDecSt> getDecSts() {
		return decSts;
	}

	@Override
	public String toString() {
		return "[PROGRAM: " + getDecSts() + "]";
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
	
}
