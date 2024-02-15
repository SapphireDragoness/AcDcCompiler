package visitor;

import ast.*;

/**
 * Interfaccia per il pattern visitor.
 * 
 * @author Linda Monfermoso, 20028464
 */
public interface IVisitor {

	/**
	 * Visita il nodo NodeProgram.
	 * 
	 * @param node il NodeProgram da visitare
	 */
	
	public abstract void visit(NodeProgram node);
	
	/**
	 * Visita il nodo NodeAssign.
	 *
	 * @param node il NodeAssign da visitare
	 */
	public abstract void visit(NodeAssign node);
	
	/**
	 * Visita il nodo NodeBinOp.
	 * 
	 * @param node il NodeBinOp da visitare
	 */
	public abstract void visit(NodeBinOp node);
	
	/**
	 * Visita il nodo NodeConst.
	 * 
	 * @param node il NodeConst da visitare
	 */
	public abstract void visit(NodeConst node);
	
	/**
	 * Visita il nodo NodeDecl.
	 * 
	 * @param node il NodeDecl da visitare
	 */
	public abstract void visit(NodeDecl node);
	
	/**
	 * Visita il nodo NodeDeref.
	 * 
	 * @param node il NodeDeref da visitare
	 */
	public abstract void visit(NodeDeref node);
	
	/**
	 * Visita il nodo NodeId.
	 * 
	 * @param node il NodeId da visitare
	 */
	public abstract void visit(NodeId node);
	
	/**
	 * Visita il nodo NodePrint.
	 * 
	 * @param node il NodePrint da visitare
	 */
	public abstract void visit(NodePrint node);
	
	/**
	 * Visita il nodo NodeConvert.
	 * 
	 * @param node il NodeConvert da visitare
	 */
	public abstract void visit(NodeConvert node);
	
}
