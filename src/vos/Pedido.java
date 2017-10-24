package vos;

import java.sql.Date;

import org.codehaus.jackson.annotate.JsonProperty;

import java.text.SimpleDateFormat;

public class Pedido {
	
	@JsonProperty(value="idPedido")
	private Long idPedido;
	
	@JsonProperty(value="fecha")
	private String fecha;
	
	@JsonProperty(value="idZona")
	private Long idZona;
	
	@JsonProperty(value="precioTotal")
	private double precioTotal;
	
	@JsonProperty(value="idUsuario")
	private String idUsuario;
	
	
	public Pedido(@JsonProperty(value="idPedido") Long idP, @JsonProperty(value="fecha") Date f, @JsonProperty(value="idZona") Long idZ, 
			@JsonProperty(value="precioTotal") double pre, @JsonProperty(value="idUsuario") String idUsuario)
	{
		this.idPedido=idP;
		
		SimpleDateFormat ft =new SimpleDateFormat ("yyyy/MM/dd");
		this.fecha=ft.format(f);
		
				
		this.idZona=idZ;
		
		this.precioTotal=pre;
		
		if(idUsuario==null||idUsuario.isEmpty())
			this.idUsuario=null;
		else
			this.idUsuario=idUsuario;
	}
	

	public Long getIdPedido() {
		return idPedido;
	}


	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}



	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}


	public Long getIdZona() {
		return idZona;
	}

	public void setIdZona(Long idZona) {
		this.idZona = idZona;
	}



	public double getPrecioTotal() {
		return precioTotal;
	}



	public void setPrecioTotal(double precioTotal) {
		this.precioTotal = precioTotal;
	}


	public String getIdUsuario() {
		return idUsuario;
	}


	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	
	
	

}
