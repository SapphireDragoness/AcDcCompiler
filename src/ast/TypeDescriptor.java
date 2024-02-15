package ast;

/**
 * Implementa un TypeDescriptor, che memorizza informazioni ottenute durante
 * l'analisi semantica.
 */
public class TypeDescriptor {

	private TipoTD tipo;
	private String msg;
	private int riga;

	/**
	 * Costruttore per TypeDescriptor, utilizzato in caso di errore.
	 * 
	 * @param tipo il tipo del TypeDescriptor
	 * @param msg  il messaggio d'errore
	 * @param riga la riga corrispondente al TypeDescriptor in esame
	 */
	public TypeDescriptor(TipoTD tipo, String msg, int riga) {
		this.tipo = tipo;
		this.msg = msg;
		this.riga = riga;
	}

	/**
	 * Costruttore per TypeDescriptor.
	 * 
	 * @param tipo il tipo del TypeDescriptor
	 */
	public TypeDescriptor(TipoTD tipo) {
		this.tipo = tipo;
	}

	/**
	 * Restituisce il tipo assegnato al TypeDescriptor.
	 * 
	 * @return il tipo
	 */
	public TipoTD getTipo() {
		return tipo;
	}

	/**
	 * Restituisce il messaggio assegnato al TypeDescriptor.
	 * 
	 * @return il messaggio
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * Restituisce la riga assegnata al TypeDescriptor.
	 * 
	 * @return la riga
	 */
	public int getRiga() {
		return riga;
	}

	/**
	 * Controlla se due TypeDescriptor sono compatibili tra loro, confrontandone il
	 * tipo.
	 * 
	 * @param tD il TypeDescriptor da confrontare con this
	 * @return true se il TypeDescriptor è compatibile, false altrimenti
	 */
	public boolean compatibile(TypeDescriptor tD) {
		if (this.getTipo() == TipoTD.FLOAT && tD.getTipo() == TipoTD.INT) {
			return false;
		}
		return true;
	}

}
