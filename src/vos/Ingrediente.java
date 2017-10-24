package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Ingrediente {
	
	@JsonProperty(value="nombre")
	public String nombre;
	
	@JsonProperty(value="descripcion")
	public String descripcion;
	
	@JsonProperty(value="descTraducida")
	public String descTraducida;
	
	public Ingrediente(@JsonProperty(value="nombre") String pName, @JsonProperty(value="descripcion") String pDesc, @JsonProperty(value="descTraducida") String pDescT)
	{
		super();

		this.nombre=pName;
		this.descripcion=pDesc;
		this.descTraducida=pDescT;
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
	
	

}
