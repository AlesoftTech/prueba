package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Producto
{
	
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="descripcion")
	private String descripcion;
	
	@JsonProperty(value="descTraducida")
	private String descTraducida;
	
	@JsonProperty(value="tiempoPrep")
	private int tiempoPrep;
	
	@JsonProperty(value="tipo")
	private String tipo;
	
	@JsonProperty(value="categoria")
	private String categoria;
	
	


	public Producto(@JsonProperty(value="nombre") String pName, @JsonProperty(value="descripcion") String pDesc, 
			@JsonProperty(value="descTraducida") String pDescT, @JsonProperty(value="tiempoPrep") int pTiempo, 
			@JsonProperty(value="tipo") String pTipo, @JsonProperty(value="categoria") String pCat)
	{
		super();
		this.nombre=pName;
		this.descripcion=pDesc;
		this.descTraducida=pDescT;
		this.tiempoPrep=pTiempo;
		this.tipo=pTipo;
		this.categoria=pCat;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String name) {
		this.nombre = name;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescTraducida() {
		return descTraducida;
	}

	public void setDescTraducida(String descTraducida) {
		this.descTraducida = descTraducida;
	}

	public int getTiempoPrep() {
		return tiempoPrep;
	}

	public void setTiempoPrep(int tiempoPrep) {
		this.tiempoPrep = tiempoPrep;
	}

	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
		}	

}
