package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ProductoPedido {
	
	@JsonProperty(value="idPedido")
	private Long idPedido;
	
	@JsonProperty(value="nomProducto")
	private String nomProducto;
	
	@JsonProperty(value="nomRestaurante")
	private String nomRestaurante;
	
	public ProductoPedido(@JsonProperty(value="idPedido") Long id, @JsonProperty(value="nomProducto") String nomP, @JsonProperty(value="nomRestaurante") String nomR)
	{
		this.idPedido=id;
		this.nomProducto=nomP;
		this.nomRestaurante=nomR;
	}

	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
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
