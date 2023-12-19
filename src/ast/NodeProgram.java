package ast;

import java.util.ArrayList;

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
		return decSts.toString();
	}
	
}
