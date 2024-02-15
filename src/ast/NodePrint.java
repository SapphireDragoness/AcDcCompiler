package ast;

import visitor.IVisitor;

/**
 * Implementa il nodo NodePrint dell'AST.
 * 
 * @author Linda Monfermoso, 20028464
 * 
 */
public class NodePrint extends NodeStm {

	private NodeId id;

	/**
	 * Costruttore per NodePrint.
	 * 
	 * @param id NodeId assegnato al nodo
	 */
	public NodePrint(NodeId id) {
		this.id = id;
	}

	/**
	 * Restituiscce il NodeId assegnato a un NodePrint.
	 * 
	 * @return NodeId assegnato al nodo
	 */
	public NodeId getId() {
		return id;
	}

	@Override
	public String toString() {
		return "[PRINT: " + getId() + "]";
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

}
