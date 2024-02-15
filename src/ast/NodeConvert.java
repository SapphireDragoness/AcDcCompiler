package ast;

import visitor.IVisitor;

/**
 * Implementa il nodo NodeConvert dell'AST.
 * 
 * @author Linda Monfermoso, 20028464
 * 
 */
public class NodeConvert extends NodeExpr {

	private NodeExpr node;

	/**
	 * Restituisce l'espressione assegnata a un NodeConvert.
	 * 
	 * @param node il nodo sul quale effettuare la conversione di tipo
	 */
	public NodeConvert(NodeExpr node) {
		this.node = node;
	}

	/**
	 * Restituisce l'espressione assegnata a un NodeExpr.
	 * 
	 * @return il nodo
	 */
	public NodeExpr getNodeExpr() {
		return node;
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		return "[CONVERT: " + getNodeExpr() + "]";
	}

}
