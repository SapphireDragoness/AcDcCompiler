package visitor;

import ast.NodeAssign;
import ast.NodeBinOp;
import ast.NodeConvert;
import ast.NodeCost;
import ast.NodeDecl;
import ast.NodeDeref;
import ast.NodeId;
import ast.NodePrint;
import ast.NodeProgram;

public class CodeGeneratorVisitor implements IVisitor {
	
	private String codiceDc; // mantiene il codice della visita

	public void visit(NodeBinOp node) {
		node.getLeft().accept(this);
		String leftCodice = codiceDc;
		node.getRight().accept(this);
		String rightCodice = codiceDc;
		if ( ......... ) //controlli opportuni su leftTD e rightTD
		codiceDc = leftCodice + rightCodice + ... //
	}

	@Override
	public void visit(NodeProgram node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(NodeAssign node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(NodeCost node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(NodeDecl node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(NodeDeref node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(NodeId node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(NodePrint node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(NodeConvert node) {
		// TODO Auto-generated method stub
		
	}

}