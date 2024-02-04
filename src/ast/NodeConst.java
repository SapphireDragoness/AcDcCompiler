package ast;

import visitor.IVisitor;

public class NodeConst extends NodeExpr {

	private String value;
	private LangType type;
	
	public NodeConst(String value, LangType type) {
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
		return "[CONST: " + getValue() + ", " + getType() + "]";
	}


	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
	
}
