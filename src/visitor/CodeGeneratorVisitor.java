package visitor;

import ast.NodeBinOp;

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
	// I metodi visit per gli altri nodi concreti.......

}