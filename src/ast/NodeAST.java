package ast;

import visitor.IVisitor;

public abstract class NodeAST {
	
	public abstract TypeDescriptor calcResType();
	public abstract String calcCodice();
	public abstract void accept(IVisitor visitor);
	
}
