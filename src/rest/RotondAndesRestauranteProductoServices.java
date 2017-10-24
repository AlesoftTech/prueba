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
import vos.RestauranteProducto;

@Path("{idUsuario}/restaurantesProducto")
public class RotondAndesRestauranteProductoServices {
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
	@Path( "{nameRestaurante}" )
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getRestaurantesPorProducto(@PathParam( "nameRestaurante" ) String nameRestaurante ) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<RestauranteProducto> RestauranteProductos;
		try {
			RestauranteProductos = tm.darProductosPorRestaurante(nameRestaurante);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(RestauranteProductos).build();
	}

//	@GET
//	@Path( "{name}" )
//	@Produces( { MediaType.APPLICATION_JSON } )
//	public Response getRestauranteProducto( @PathParam( "name" ) String name )
//	{
//		RotondAndesTM tm = new RotondAndesTM(getPath());
//		List<RestauranteProducto> RestauranteProductos;
//		try {
//			RestauranteProductos = tm.darRestaurantesPorProducto(name);
//		} catch (Exception e) {
//			return Response.status(500).entity(doErrorMessage(e)).build();
//		}
//		return Response.status(200).entity(RestauranteProductos).build();
//	}

   
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addRestauranteProducto(@PathParam("idUsuario") String idUsuario,RestauranteProducto RestauranteProducto) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addRestauranteProducto(idUsuario,RestauranteProducto);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(RestauranteProducto).build();
	}	
   
	
	

	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteRestauranteProducto(@PathParam("idUsuario") String idUsuario,RestauranteProducto RestauranteProducto) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.deleteRestauranteProducto(idUsuario,RestauranteProducto);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(RestauranteProducto).build();
	}



}
