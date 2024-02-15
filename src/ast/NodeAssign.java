package ast;

import visitor.IVisitor;

/**
 * Implementa il nodo NodeAssign dell'AST.
 * 
 * @author Linda Monfermoso, 20028464
 * 
 */
public class NodeAssign extends NodeStm {

	private NodeId id;
	private NodeExpr expr;

	/**
	 * Costruttore per NodeAssign
	 * 
	 * @param id   il nome dell'id
	 * @param expr l'espressione assegnata all'id
	 */
	public NodeAssign(NodeId id, NodeExpr expr) {
		this.id = id;
		this.expr = expr;
	}

	/**
	 * Restituisce l'id assegnato a un NodeId.
	 * 
	 * @return NodeId assegnato al nodo
	 */
	public NodeId getId() {
		return id;
	}

	/**
	 * Restituisce l'espressione assegnata a un NodeId.
	 * 
	 * @return NodeExpr assegnato al nodo
	 */
	public NodeExpr getExpr() {
		return expr;
	}

	@Override
	public String toString() {
		return "[ASSIGN: " + getId() + "->" + getExpr() + "]";
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

}
