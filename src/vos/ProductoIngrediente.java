package vos;

import org.codehaus.jackson.annotate.JsonProperty;


public class ProductoIngrediente {

		
		@JsonProperty(value="nomProducto")	
		private String nomProducto;
		
		@JsonProperty(value="nomIngrediente")
		private String nomIngrediente;
		
		public ProductoIngrediente(@JsonProperty(value="nomProducto") String nombreProducto, @JsonProperty(value="nomIngrediente") String nomIng)
		{
			super();
			this.nomProducto = nombreProducto;
			this.nomIngrediente = nomIng;	
			
		}

		public String getNomProducto() {
			return nomProducto;
		}


		public void setNomProducto(String nombre) {
			this.nomProducto = nombre;
		}


		public String getNomIngrediente() {
			return nomIngrediente;
		}


		public void setNomIngrediente(String nombre) {
			this.nomIngrediente = nombre;
		}

	}
