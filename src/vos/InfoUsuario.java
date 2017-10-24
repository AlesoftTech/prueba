package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class InfoUsuario 
{
	@JsonProperty(value="usuario")
	private Usuario usuario;
	
	@JsonProperty(value="preferencias")
	private List<Preferencia> preferencias;
	
	@JsonProperty(value="pedidos")
	private List<Pedido> pedidos;
	
	public InfoUsuario(@JsonProperty(value="usuario") Usuario usuario, @JsonProperty(value="preferencias") List<Preferencia> preferencias, @JsonProperty(value="pedidos") List<Pedido> pedidos )
	{
		this.usuario=usuario;
		this.preferencias=preferencias;
		this.pedidos=pedidos;		
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Preferencia> getPreferencias() {
		return preferencias;
	}

	public void setPreferencias(List<Preferencia> preferencias) {
		this.preferencias = preferencias;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
	
	

}
