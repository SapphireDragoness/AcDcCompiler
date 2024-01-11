package ast;

import visitor.IVisitor;

public class NodeDeref extends NodeExpr {

	private NodeId id;
	
	public NodeDeref(NodeId id) {
		this.id = id;
	}

	public NodeId getId() {
		return id;
	}

	@Override
	public String toString() {
		return "NodeDeref [id: " + getId() + "]";
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
	
}
