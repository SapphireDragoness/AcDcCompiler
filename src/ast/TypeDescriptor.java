package ast;

public class TypeDescriptor {

	private TipoTD tipo;
	private String msg;
	
	public TypeDescriptor(TipoTD tipo, String msg) {
		this.tipo = tipo;
		this.msg = msg;
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

	public boolean compatibile(TypeDescriptor tD) {
		if(this.getTipo() == TipoTD.FLOAT && tD.getTipo() == TipoTD.INT) {
			return false;
		}
		return true;
	}
	
}
