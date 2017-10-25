package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class EquivalenciaIngrediente {
	
	@JsonProperty(value="nomEquivalencia")
	private String nomEquivalencia;
	
	@JsonProperty(value="nomIngrediente")
	private String nomIngrediente;
	
	@JsonProperty(value="nomRestaurante")
	private String nomRestaurante;
	
	public EquivalenciaIngrediente(@JsonProperty(value="nomEquivalencia") String nomE, @JsonProperty(value="nomIngrediente") String nomP, @JsonProperty(value="nomRestaurante") String nomR)
	{
		this.nomEquivalencia=nomE;
		this.nomIngrediente=nomP;
		this.nomRestaurante=nomR;
	}

	public String getnomEquivalencia() {
		return nomEquivalencia;
	}

	public void setnomEquivalencia(String nom) {
		this.nomEquivalencia = nom;
	}

	public String getNomIngrediente() {
		return nomIngrediente;
	}

	public void setNomIngrediente(String nomIngrediente) {
		this.nomIngrediente = nomIngrediente;
	}

	public String getNomRestaurante() {
		return nomRestaurante;
	}

	public void setNomRestaurante(String nomRestaurante) {
		this.nomRestaurante = nomRestaurante;
	}
	
	

}
