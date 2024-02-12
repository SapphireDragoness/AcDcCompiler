package ast;

public class TypeDescriptor {

	private TipoTD tipo;
	private String msg;
	private int riga;
	
	public TypeDescriptor(TipoTD tipo, String msg, int riga) {
		this.tipo = tipo;
		this.msg = msg;
		this.riga = riga;
	}
	
	public TypeDescriptor(TipoTD tipo) {
		this.tipo = tipo;
	}
	
	public TipoTD getTipo() {
		return tipo;
	}

	public String getMsg() {
		return msg;
	}
	
	public int getRiga() {
		return riga;
	}

	public boolean compatibile(TypeDescriptor tD) {
		if(this.getTipo() == TipoTD.FLOAT && tD.getTipo() == TipoTD.INT) {
			return false;
		}
		return true;
	}
	
}
