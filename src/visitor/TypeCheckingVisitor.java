package visitor;

import ast.*;
import symboltable.SymbolTable;

public class TypeCheckingVisitor implements IVisitor {

	private TypeDescriptor resType; // mantiene il risultato della visita
	private String log = "";
	
	public TypeCheckingVisitor() {
		SymbolTable.inizializza();
	}
	
	public TypeDescriptor getResType() {
		return resType;
	}
	
	@Override
	public void visit(NodeProgram node) {
		for(NodeDecSt decSt : node.getDecSts()) {
			decSt.accept(this);
		}
	}

	@Override
	public void visit(NodeBinOp node) {
		node.getLeft().accept(this);
		TypeDescriptor leftTD = resType;
		node.getRight().accept(this);
		TypeDescriptor rightTD = resType;
		
		if(leftTD.getTipo() == TipoTD.ERROR) 
			resType = leftTD;
		else if(rightTD.getTipo() == TipoTD.ERROR)
			resType = rightTD;
		else if(leftTD.getTipo() != rightTD.getTipo()) {
			if(leftTD.getTipo() == TipoTD.INT)
				node.setLeft(new NodeConvert(node.getLeft()));
			else
				node.setRight(new NodeConvert(node.getRight()));
			resType = new TypeDescriptor(TipoTD.FLOAT);
		}
		else {
			if(leftTD.getTipo() == TipoTD.FLOAT)
				resType = new TypeDescriptor(TipoTD.FLOAT);
			else
				resType = new TypeDescriptor(TipoTD.INT);
		}
	}

	@Override
	public void visit(NodeAssign node) {
		node.getId().accept(this);
		TypeDescriptor idTD = resType;
		node.getExpr().accept(this);
		TypeDescriptor exprTD = resType;
		
		if(idTD.getTipo() == TipoTD.ERROR)
			resType = idTD;
		else if(exprTD.getTipo() == TipoTD.ERROR)
			resType = exprTD;
		else if(!exprTD.compatibile(idTD)) {
			resType = new TypeDescriptor(TipoTD.ERROR, node.getId().getName() + " è di tipo INT, impossibile assegnargli un'espressione di tipo FLOAT.\n");
			log += resType.getMsg();
		}
		else
			resType = new TypeDescriptor(TipoTD.OK);
	}

	@Override
	public void visit(NodeConst node) {
		switch(node.getType()) {
			case FLOAT -> {
				resType = new TypeDescriptor(TipoTD.FLOAT);
			}
			case INT -> {
				resType = new TypeDescriptor(TipoTD.INT);
			}
		}
	}

	@Override
	public void visit(NodeDecl node) {
		TypeDescriptor idTD;
		
		if(SymbolTable.enter(node.getId().getName(), new SymbolTable.Attributes(node.getType(), node.getId().getName()))) {
			switch(node.getType()) {
			case FLOAT -> {
				idTD = new TypeDescriptor(TipoTD.FLOAT);
			}
			case INT -> {
				idTD = new TypeDescriptor(TipoTD.INT);
			}
			default -> {
				idTD = new TypeDescriptor(TipoTD.ERROR);
			}
			}	
		}
		else {
			resType = new TypeDescriptor(TipoTD.ERROR, "Errore dichiarazione: " + node.getId().getName() + " già dichiarato.\n");
			log += resType.getMsg();
			return;
		}
		
		if(node.getInit() == null) {
			resType = new TypeDescriptor(TipoTD.OK);
			return;
		}
		
		node.getInit().accept(this);
		TypeDescriptor initTD = resType;
		
		SymbolTable.lookup(node.getId().getName());
		if(idTD.getTipo() == TipoTD.INT) {
			if(initTD.getTipo() == TipoTD.FLOAT) {
				resType = new TypeDescriptor(TipoTD.ERROR, node.getId().getName() + " è di tipo INT, impossibile assegnargli un'espressione di tipo FLOAT.\n");
				log += resType.getMsg();
			}
			else
				resType = new TypeDescriptor(TipoTD.OK);
		}
		else 
			resType = new TypeDescriptor(TipoTD.OK);
	}

	@Override
	public void visit(NodeDeref node) {
		node.getId().accept(this);
	}

	@Override
	public void visit(NodeId node) {
		if(SymbolTable.lookup(node.getName()) == null) {
			resType = new TypeDescriptor(TipoTD.ERROR, "Errore ID: " + node.getName() + " non dichiarato.\n");
			log += resType.getMsg();
		}
		else {
			switch (SymbolTable.lookup(node.getName()).getTipo()) {
			case INT -> {
				resType = new TypeDescriptor(TipoTD.INT);
			}
			case FLOAT ->{
				resType = new TypeDescriptor(TipoTD.FLOAT);
			}
		}
		}
	}

	@Override
	public void visit(NodePrint node) {
		node.getId().accept(this);
		
		if(resType.getTipo() != TipoTD.ERROR)
			resType = new TypeDescriptor(TipoTD.OK);
	}

	@Override
	public void visit(NodeConvert node) {
		resType = new TypeDescriptor(TipoTD.FLOAT);
	}
	
	public String getLog() {
		return log;
	}
	
}
