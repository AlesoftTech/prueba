package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Restaurante
{
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="representante")
	private String representante;
	
	@JsonProperty(value="tipoComida")
	private String tipoComida;
	
	@JsonProperty(value="paginaWeb")
	private String paginaWeb;
	
	public Restaurante(@JsonProperty(value="nombre") String pNombre, @JsonProperty(value="representante") String pRep,
			@JsonProperty(value="tipoComida") String pTipo, @JsonProperty(value="paginaWeb") String pPag)
	{
		super();

		this.nombre=pNombre;
		this.representante=pRep;
		this.tipoComida=pTipo;
		this.paginaWeb=pPag;
		
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRepresentante() {
		return representante;
	}

	public void setRepresentante(String representante) {
		this.representante = representante;
	}

	public String getTipoComida() {
		return tipoComida;
	}

	public void setTipoComida(String tipoComida) {
		this.tipoComida = tipoComida;
	}

	public String getPaginaWeb() {
		return paginaWeb;
	}

	public void setPaginaWeb(String paginaWeb) {
		this.paginaWeb = paginaWeb;
	}
	
	
	
	

}
