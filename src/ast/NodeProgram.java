package ast;

import java.util.ArrayList;

import visitor.IVisitor;

public class NodeProgram extends NodeAST {
	
	private final ArrayList<NodeDecSt> decSts;
	
	public NodeProgram(ArrayList<NodeDecSt> decSts) {
		this.decSts = decSts;
	}

	public ArrayList<NodeDecSt> getDecSts() {
		return decSts;
	}

	@Override
	public String toString() {
		return "NodeProgram [decSts: " + getDecSts() + "]";
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
	
}
