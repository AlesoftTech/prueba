package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Preferencia {
	

	@JsonProperty(value="idCliente")	
	private String idCliente;
	
	@JsonProperty(value="tipo")
	private String tipo;
	
	public Preferencia(@JsonProperty(value="idCliente") String ideCliente, @JsonProperty(value="tipo") String tp)
	{
		super();
		this.idCliente = ideCliente;
		this.tipo = tp;	
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idC) {
		this.idCliente = idC;
	}

	public String getTipo() {
		return tipo;
	}

	public void setCategoria(String pTipo) {
		this.tipo = pTipo;
	}
	
	


}