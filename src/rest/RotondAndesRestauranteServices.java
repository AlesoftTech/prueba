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
import vos.Restaurante;

@Path("{idUsuario}/restaurantes")
public class RotondAndesRestauranteServices {

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
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getRestaurantes() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Restaurante> Restaurantes;
		try {
			Restaurantes = tm.darRestaurantes();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Restaurantes).build();
	}

	@GET
	@Path( "{name}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getRestaurante( @PathParam( "name" ) String name )
	{
		RotondAndesTM tm = new RotondAndesTM( getPath( ) );
		try
		{
			Restaurante v = tm.buscarRestaurantePorName( name );
			return Response.status( 200 ).entity( v ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	
//	@GET
//	@Path("{nameRestaurante}/productos")
//	public Class<RotondAndesRestauranteProductoServices> getRestProductoServices(@PathParam("nameRestaurante") String nom) throws Exception
//	{
//		if(getRestaurante(nom)==null)
//			throw new Exception("El restaurante "+nom+" no existe.");
//		else
//			return RotondAndesRestauranteProductoServices.class;
//	}

   
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addRestaurante(@PathParam("idUsuario") String idUsuario, Restaurante Restaurante) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addRestaurante(idUsuario,Restaurante);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Restaurante).build();
	}
	
   
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateRestaurante(@PathParam("idUsuario") String idUsuario, Restaurante Restaurante) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.updateRestaurante(Restaurante);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Restaurante).build();
	}
	

	@DELETE
	@Path( "{name}" )
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteRestaurante(@PathParam("idUsuario") String idUsuario, @PathParam( "name" ) String name) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.deleteRestaurante(idUsuario,name);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).build();
	}


	

}
