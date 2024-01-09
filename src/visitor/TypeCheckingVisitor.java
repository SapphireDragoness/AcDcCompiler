package visitor;

import ast.*;

public class TypeCheckingVisitor implements IVisitor {

	private TypeDescriptor resType; // mantiene il risultato della visita

	public void visit(NodeBinOp node) {
		node.getLeft().accept(this);
		TypeDescriptor leftTD = resType;
		node.getRight().accept(this);
		TypeDescriptor rightTD = resType;
		
		if ( ......... ) //controlli opportuni su leftTD e rightTD
		......................
		resType = ..... // assegna il TypeDescriptor appropriato
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
