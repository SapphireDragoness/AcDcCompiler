package ast;

import visitor.IVisitor;

public abstract class NodeAST {
	
	private TypeDescriptor resType;
	
	public abstract void accept(IVisitor visitor);

	public TypeDescriptor getResType() {
		return resType;
	}
	
}
