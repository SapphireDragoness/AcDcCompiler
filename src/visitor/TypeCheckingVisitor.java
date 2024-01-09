package visitor;

import ast.NodeBinOp;

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
	// I metodi visit per gli altri nodi concreti.......
}
