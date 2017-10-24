package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class RestauranteProducto {
	
	@JsonProperty(value="nomProducto")	
	private String nomProducto;
	
	@JsonProperty(value="nomRestaurante")
	private String nomRestaurante;
	
	@JsonProperty(value="costoProd")
	private double costoProd;
	
	@JsonProperty(value="precio")
	private double precio;
	
	@JsonProperty(value="unidadesDisponibles")
	private int unidadesDisponibles;
	
	
	public RestauranteProducto(@JsonProperty(value="nomProducto") String id, @JsonProperty(value="nomRestaurante") String nom,
			@JsonProperty(value="costoProd") double costo, @JsonProperty(value="precio") double pre, @JsonProperty(value="unidadesDisponibles") int un)
	{
		super();
		this.nomProducto=id;
		this.nomRestaurante=nom;	
		this.costoProd=costo;
		this.precio=pre;
		this.unidadesDisponibles=un;
	}


	public String getNomProducto() {
		return nomProducto;
	}


	public void setNomProducto(String idProducto) {
		this.nomProducto = idProducto;
	}


	public String getNomRestaurante() {
		return nomRestaurante;
	}


	public void setNomRestaurante(String nomRestaurante) {
		this.nomRestaurante = nomRestaurante;
	}


	public double getCostoProd() {
		return costoProd;
	}


	public void setCostoProd(double costoProd) {
		this.costoProd = costoProd;
	}


	public double getPrecio() {
		return precio;
	}


	public void setPrecio(double precio) {
		this.precio = precio;
	}


	public int getUnidadesDisponibles() {
		return unidadesDisponibles;
	}


	public void setUnidadesDisponibles(int unidadesDisponibles) {
		this.unidadesDisponibles = unidadesDisponibles;
	}
	
	

}
