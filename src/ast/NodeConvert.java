package ast;

import visitor.IVisitor;

public class NodeConvert extends NodeExpr {
	
	private NodeExpr nodeExpr;
	
	public NodeConvert() {
		
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
	
}
