package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Usuario {
	
	@JsonProperty(value="nombre")
	public String nombre;
	
	@JsonProperty(value="identificacion")
	public String identificacion;
	
	@JsonProperty(value="correo")
	public String correo;
	
	@JsonProperty(value="rol")
	public String rol;
	
	public Usuario(@JsonProperty(value="nombre") String nombre, @JsonProperty(value="identificacion") String identificacion, @JsonProperty(value="correo") String correo, @JsonProperty(value="rol") String rol)
	{
		super();

		this.nombre=nombre;
		this.identificacion=identificacion;
		this.correo=correo;
		this.rol=rol;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String name) {
		this.nombre = name;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
	
	

}
