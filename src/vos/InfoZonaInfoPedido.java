package vos;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

public class InfoZonaInfoPedido {
	
	
	@JsonProperty(value="zona")
	private Zona zona;
	
	@JsonProperty(value="pedido")
	private Pedido pedido;
	
	@JsonProperty(value="productosDelPedido")
	private ArrayList<ProductoPedido> productosDelPedido;
	
	
	public  InfoZonaInfoPedido (@JsonProperty(value="zona") Zona z, @JsonProperty(value="pedido") Pedido p, @JsonProperty(value="productosDelPedido") ArrayList<ProductoPedido> pP)
	{
		this.zona=z;
		this.pedido=p;
		this.productosDelPedido=pP;
	}


	public Zona getZona() {
		return zona;
	}


	public void setZona(Zona zona) {
		this.zona = zona;
	}


	public Pedido getPedido() {
		return pedido;
	}


	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}


	public ArrayList<ProductoPedido> getProductosDelPedido() {
		return productosDelPedido;
	}


	public void setProductosDelPedido(ArrayList<ProductoPedido> productosDelPedido) {
		this.productosDelPedido = productosDelPedido;
	}
	
	
	
	

}