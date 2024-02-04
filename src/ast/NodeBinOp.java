package ast;

import visitor.IVisitor;

public class NodeBinOp extends NodeExpr {

	private NodeExpr left;
	private LangOper op;
	private NodeExpr right;
	
	public NodeBinOp(NodeExpr left, LangOper op, NodeExpr right) {
		this.left = left;
		this.op = op;
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

	public void setLeft(NodeExpr left) {
		this.left = left;
	}

	public void setRight(NodeExpr right) {
		this.right = right;
	}

	@Override
	public String toString() {
		return "[BINOP: " + getLeft() + " " + getOp() + " " + getRight() + "]";
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
	
}
