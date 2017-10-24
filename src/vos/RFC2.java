package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class RFC2 {
	
	
	@JsonProperty(value="zona")
	private Zona zona;
	
	@JsonProperty(value="pedido")
	private List<Pedido> pedido;
	
	public  RFC2 (@JsonProperty(value="zona") Zona z, @JsonProperty(value="pedido") List<Pedido> p)
	{
		this.zona=z;
		this.pedido=p;
	}


	public Zona getZona() {
		return zona;
	}


	public void setZona(Zona zona) {
		this.zona = zona;
	}


	public List<Pedido> getPedido() {
		return pedido;
	}


	public void setPedido(List<Pedido> pedidos) {
		this.pedido = pedidos;
	}
	
	
	

}