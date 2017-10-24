package rest;


import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import tm.RotondAndesTM;
import vos.ProductoIngrediente;

@Path("{idUsuario}/productosIngredientes")
public class RotondAndesProductoIngredienteServices {

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
	@Path( "{nameP}" )
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getRestaurantesPorProducto(@PathParam( "nameP" ) String nameP ) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<ProductoIngrediente> ProductoIngredientes;
		try {
			ProductoIngredientes = tm.darIngredientesPorProducto(nameP);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(ProductoIngredientes).build();
	}


   
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addProductoIngrediente(@PathParam("idUsuario") String idUsuario,ProductoIngrediente ProductoIngrediente) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addProductoIngrediente(idUsuario,ProductoIngrediente);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(ProductoIngrediente).build();
	}	
   
	
	

	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteProductoIngrediente(@PathParam("idUsuario") String idUsuario,ProductoIngrediente ProductoIngrediente) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.deleteProductoIngrediente(idUsuario,ProductoIngrediente);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(ProductoIngrediente).build();
	}

}
