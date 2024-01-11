package ast;

import visitor.IVisitor;

public abstract class NodeAST {
	
	public abstract void accept(IVisitor visitor);
	
}
