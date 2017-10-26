package rest;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("consumo")
public class RotondAndesRFC7 {
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
	
	@GET 
	@Path("{idUsuario}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response darConsumoUsuario(@PathParam("idUsuario") Long id){
		
		//TODO : completar el metodo. 
//		Consulta los productos que ha consumido un cliente registrado de RotondAndes. 
//		Debe discriminar los productos que ha solicitado independientemente, los de 
//		los menús y los solicitados en una mesa. Esta operación es realizada por los 
//		clientes registrados y por el usuario administrador de RotondAndes.
//		NOTA: Respetando la privacidad de los clientes, cuando un cliente registrado
//		hace esta consulta obtiene la información de su propia actividad, mientras 
//		que el administrador obtiene toda la información de cualquiera de los clientes. 
//		Ver RNF1.
		return null;
	}
	
	

}
