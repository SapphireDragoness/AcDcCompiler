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

	public void setTipo(TipoTD tipo) {
		this.tipo = tipo;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean compatibile(TypeDescriptor tD) {
		if(this.getTipo() == TipoTD.ERROR || tD.getTipo() == TipoTD.ERROR || (this.getTipo() == TipoTD.INT && tD.getTipo() == TipoTD.FLOAT)) {
			return false;
		}
		return true;
	}
	
}
