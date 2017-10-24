package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ProductoMenu {


	@JsonProperty(value="nomMenu")	
	private String nomMenu;

	@JsonProperty(value="nomProducto")
	private String nomProducto;

	public ProductoMenu(@JsonProperty(value="nomMenu") String nomMenu, @JsonProperty(value="nomProducto") String nomProducto)
	{
		super();
		this.nomMenu = nomMenu;
		this.nomProducto = nomProducto;	

	}

	public String getNomMenu() {
		return nomMenu;
	}

	public void setNomMenu(String nombre) {
		this.nomMenu = nombre;
	}

	public String getNomProducto() {
		return nomProducto;
	}

	public void setNomProducto(String nombre) {
		this.nomProducto = nombre;
	}	


}
