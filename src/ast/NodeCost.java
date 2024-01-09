package ast;

import visitor.IVisitor;

public class NodeCost extends NodeExpr {

	private String value;
	private LangType type;
	
	public NodeCost(String value, LangType type) {
		this.value = value;
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public LangType getType() {
		return type;
	}

	@Override
	public String toString() {
		return "NodeCost [getValue()=" + getValue() + ", getType()=" + getType() + "]";
	}


	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
	
}
