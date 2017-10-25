package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class EquivalenciaProducto {
	
	@JsonProperty(value="nomEquivalencia")
	private String nomEquivalencia;
	
	@JsonProperty(value="nomProducto")
	private String nomProducto;
	
	@JsonProperty(value="nomRestaurante")
	private String nomRestaurante;
	
	public EquivalenciaProducto(@JsonProperty(value="nomEquivalencia") String nomE, @JsonProperty(value="nomProducto") String nomP, @JsonProperty(value="nomRestaurante") String nomR)
	{
		this.nomEquivalencia=nomE;
		this.nomProducto=nomP;
		this.nomRestaurante=nomR;
	}

	public String getnomEquivalencia() {
		return nomEquivalencia;
	}

	public void setnomEquivalencia(String nom) {
		this.nomEquivalencia = nom;
	}

	public String getNomProducto() {
		return nomProducto;
	}

	public void setNomProducto(String nomProducto) {
		this.nomProducto = nomProducto;
	}

	public String getNomRestaurante() {
		return nomRestaurante;
	}

	public void setNomRestaurante(String nomRestaurante) {
		this.nomRestaurante = nomRestaurante;
	}
	
	

}
