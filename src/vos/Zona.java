package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Zona {
	
	@JsonProperty(value="id")
	private Long id;
	
	@JsonProperty(value="capacidad")
	private int capacidad;
	
	@JsonProperty(value="abierto")
	private boolean abierto;
	
	@JsonProperty(value="accesoNecEsp")
	private boolean accesoNecEsp;
	
	@JsonProperty(value="asadores")
	private boolean asadores;
	
	@JsonProperty(value="extractores")
	private boolean extractores;
	
	@JsonProperty(value="calentadores")
	private boolean calentadores;
	
	@JsonProperty(value="acondicionarse")	
	private boolean acondicionarse;
	
	public Zona(@JsonProperty(value="id") Long pId,@JsonProperty(value="capacidad") int pCap,@JsonProperty(value="abierto") boolean pAb, 
			@JsonProperty(value="accesoNecEsp") boolean pAcc,@JsonProperty(value="asadores") boolean pAsa,@JsonProperty(value="extractores") boolean pExt, 
			@JsonProperty(value="calentadores") boolean pCal, @JsonProperty(value="acondicionarse") boolean pAco)
	{
		super();

		this.id=pId;
		this.capacidad=pCap;
		this.abierto=pAb;
		this.accesoNecEsp=pAcc;
		this.asadores=pAsa;
		this.extractores=pExt;
		this.calentadores=pCal;
		this.acondicionarse=pAco;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public boolean isAccesoNecEsp() {
		return accesoNecEsp;
	}

	public void setAccesoNecEsp(boolean accesoNecEsp) {
		this.accesoNecEsp = accesoNecEsp;
	}

	public boolean isAsadores() {
		return asadores;
	}

	public void setAsadores(boolean asadores) {
		this.asadores = asadores;
	}

	public boolean isExtractores() {
		return extractores;
	}

	public void setExtractores(boolean extractores) {
		this.extractores = extractores;
	}

	public boolean isCalentadores() {
		return calentadores;
	}

	public void setCalentadores(boolean calentadores) {
		this.calentadores = calentadores;
	}

	public boolean isAcondicionarse() {
		return acondicionarse;
	}

	public void setAcondicionarse(boolean acondicionarse) {
		this.acondicionarse = acondicionarse;
	}
	
	
	
	public boolean isAbierto() {
		return abierto;
	}

	public void setAbierto(boolean abierto) {
		this.abierto = abierto;
	}

	public int isAcondicionarseCHAR() {
		if(!acondicionarse)
			return 0;
		else if(acondicionarse)
			return 1;
		else
			return 0;
	}
	
	public int isCalentadoresCHAR() {
		if(!calentadores)
			return 0;
		else if(calentadores)
			return 1;
		else
			return 0;
	}
	
	public int isAsadoresCHAR() {
		if(!asadores)
			return 0;
		else if(asadores)
			return 1;
		else
			return 0;
	}
	
	public int isAccesoNecEspCHAR() {
		if(!accesoNecEsp)
			return 0;
		else if(accesoNecEsp)
			return 1;
		else
			return 0;
	}
	
	public int isExtractoresCHAR() {
		if(!extractores)
			return 0;
		else if(extractores)
			return 1;
		else
			return 0;
	}
	public int isAbiertoCHAR() {
		if(!abierto)
			return 0;
		else if(abierto)
			return 1;
		else
			return 0;
	}
	
	

}
