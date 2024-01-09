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
		return getId().toString();
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
