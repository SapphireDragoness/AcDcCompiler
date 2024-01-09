package ast;

import visitor.IVisitor;

public class NodeBinOp extends NodeExpr {

	private LangOper op;
	private NodeExpr left;
	private NodeExpr right;
	
	public NodeBinOp(LangOper op, NodeExpr left, NodeExpr right) {
		this.op = op;
		this.left = left;
		this.right = right;
	}
	
	public TypeDescriptor calcResType() {
		TypeDescriptor leftTD=left.calcResType; // descrittore di tipo della espressione sinistra
		TypeDescriptor rightTD=right.calcResType; // descrittore di tipo della espressione destra
		if ( ......... ) //controlli opportuni su leftTD e rightTD
		..........
		return;
	}

	public LangOper getOp() {
		return op;
	}

	public NodeExpr getLeft() {
		return left;
	}

	public NodeExpr getRight() {
		return right;
	}
	
	@Override
	public String toString() {
		return getOp().toString() + getLeft().toString() + getRight().toString();
		
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
