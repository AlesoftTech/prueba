package rest;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

public class RotondAndesRFC8 {
	/**
	 * Atributo que usa la anotacion @Context para tener el ServletContext de la conexion actual.
	 */
	@Context
	private ServletContext context;

	/**
	 * Metodo que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}


	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}
	
	//TODO: agrgar los metodos 
//	 Muestra la información consolidada de los pedidos hechos en RotondAndes. 
//	 Consolida, como mínimo, para cada uno los restaurantes y para cada uno 
//	 de sus productos las ventas totales (en dinero y en cantidad), lo consumidos 
//	 por clientes registrados y por clientes no registrados.
//	 Esta operación es realizada por un usuario restaurante y por el administrador de RotondAndes.
//	 NOTA: Respetando la privacidad de los clientes, cuando un restaurante hace esta consulta obtiene 
//	 la información de sus propias actividades, mientras que el administrador obtiene toda la información. Ver RNF1.
	

}
