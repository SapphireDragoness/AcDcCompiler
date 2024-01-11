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
		return "NodeBinOp [op: " + getOp() + ", left: " + getLeft() + ", right: " + getRight() + "]";
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
	
}
