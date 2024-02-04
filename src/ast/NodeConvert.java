package ast;

import visitor.IVisitor;

public class NodeConvert extends NodeExpr {
	
	private NodeExpr node;
	
	public NodeConvert(NodeExpr node) {
		this.node = node;
	}
	
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
