package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class RFC1 {

	
	@JsonProperty(value="restauranteProducto")
	private List<RestauranteProducto> restauranteProducto;
	
	@JsonProperty(value="productos")
	private List<Producto> productos;
	

	
	public RFC1 (@JsonProperty(value="restauranteProducto") List<RestauranteProducto> resta, @JsonProperty(value="productos") List<Producto> p)
	{
		this.restauranteProducto = resta;
		this.productos = p;
}

	public List<RestauranteProducto> getRestauranteProducto() {
		return restauranteProducto;
	}

	public void setRestauranteProducto(List<RestauranteProducto> restauranteProducto) {
		this.restauranteProducto = restauranteProducto;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	
	
	
}