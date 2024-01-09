package ast;

import visitor.IVisitor;

public class NodeConvert extends NodeExpr {
	
	private NodeExpr nodeExpr;
	
	public NodeConvert() {
		
	}

	@Override
	public TypeDescriptor calcResType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String calcCodice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
	
}
