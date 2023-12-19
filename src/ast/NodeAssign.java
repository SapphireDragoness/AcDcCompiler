package ast;

public class NodeAssign extends NodeStm {
	
	private NodeId id;
	private NodeExpr expr;
	
	public NodeAssign(NodeId id, NodeExpr expr) {
		this.id = id;
		this.expr = expr;
	}

	public NodeId getId() {
		return id;
	}

	public NodeExpr getExpr() {
		return expr;
	}

	@Override
	public String toString() {
		return "NodeAssign [getId()=" + getId() + ", getExpr()=" + getExpr() + "]";
	}
	
}
