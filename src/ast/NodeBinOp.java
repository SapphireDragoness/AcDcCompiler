package ast;

import visitor.IVisitor;

/**
 * Implementa il nodo NodeBinOp dell'AST.
 * 
 * @author Linda Monfermoso, 20028464
 */
public class NodeBinOp extends NodeExpr {

	private NodeExpr left;
	private LangOper op;
	private NodeExpr right;

	/**
	 * Costruttore per NodeBinOp.
	 * 
	 * @param left  l'espressione a sinistra
	 * @param op    l'operatore
	 * @param right l'espressione a destra
	 */
	public NodeBinOp(NodeExpr left, LangOper op, NodeExpr right) {
		this.left = left;
		this.op = op;
		this.right = right;
	}

	/**
	 * Restituisce l'operatore assegnato a un NodeBinOp.
	 * 
	 * @return il tipo di operatore
	 */
	public LangOper getOp() {
		return op;
	}

	/**
	 * Restituisce l'espressione a sinistra assegnata a un NodeBinOp.
	 * 
	 * @return l'espressione a sinistra dell'operatore
	 */
	public NodeExpr getLeft() {
		return left;
	}

	/**
	 * Restituisce l'espressione a destra assegnata a un NodeBinOp.
	 * 
	 * @return l'espressione a destra dell'operatore
	 */
	public NodeExpr getRight() {
		return right;
	}

	/**
	 * Imposta l'espressione a sinistra di un'operatore in seguito a una
	 * conversione.
	 * 
	 * @return il tipo di operatore
	 */
	public void setLeft(NodeExpr left) {
		this.left = left;
	}

	/**
	 * Imposta l'espressione a destra dell'operatore in seguito a una conversione.
	 * 
	 * @return il tipo di operatore
	 */
	public void setRight(NodeExpr right) {
		this.right = right;
	}

	@Override
	public String toString() {
		return "[BINOP: " + getLeft() + " " + getOp() + " " + getRight() + "]";
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

}
