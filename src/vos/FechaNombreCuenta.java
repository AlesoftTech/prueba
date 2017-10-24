package vos;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class FechaNombreCuenta {
	
	@JsonProperty(value="fecha")
	private String fecha;
	
	@JsonProperty(value="nom")
	private String nom;
	
	@JsonProperty(value="cuenta")
	private int cuenta;
	
	public FechaNombreCuenta(@JsonProperty(value="fecha") Date f,@JsonProperty(value="nom") String n, @JsonProperty(value="cuenta") int c)
	{
		SimpleDateFormat ft =new SimpleDateFormat ("yyyy/MM/dd");
		this.fecha=ft.format(f);
		this.nom=n;
		this.cuenta=c;
		
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getCuenta() {
		return cuenta;
	}

	public void setCuenta(int cuenta) {
		this.cuenta = cuenta;
	}
	
	

}
