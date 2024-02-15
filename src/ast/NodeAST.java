package ast;

import visitor.IVisitor;

/**
 * Classe astratta che rappresenta un nodo dell'AST.
 * 
 * @author Linda Monfermoso, 20028464
 */
public abstract class NodeAST {
	
	/**
	 * Implementa il pattern visitor per il nodo.
	 * 
	 * @param visitor il visitor
	 */
	public abstract void accept(IVisitor visitor);
	
}
